package pcd.ass01.simtrafficview;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficexamples.RoadSimStatistics;
import pcd.ass01.simtrafficexamples.RoadSimView;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

/**
 * Class to handle the view of the simulation
 */
public class View extends JFrame {
    private final JButton startButton;
    private final JButton stopButton;
    private final JFormattedTextField nStepField;
    private final JFormattedTextField seedField;
    private final ExecutionFlag threadFlag;
    private final JComboBox<SimulationType> selectedSimulationComboBox;
    private final JCheckBox runWithGuiCheckBox;
    private RoadSimView view;

    public View(String windowTitle) {
        super(windowTitle);
        threadFlag = new ExecutionFlag(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);

        nStepField = new JFormattedTextField(formatter);
        nStepField.setValue(1000);
        seedField = new JFormattedTextField(formatter);
        seedField.setValue(4321);
        runWithGuiCheckBox = new JCheckBox("Start simulation with GUI");
        selectedSimulationComboBox = new JComboBox<>(SimulationType.values());

        add(createPanel());
        pack();
        setVisible(true);
    }

    /**
     * Create the panel with the settings for the simulation
     *
     * @return the panel with the settings
     */
    private JPanel createPanel() {
        runWithGuiCheckBox.setSelected(true);
        JPanel settingsPage = new JPanel(new GridBagLayout());
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        JLabel stepLabel = new JLabel("Step:");
        nStepField.setPreferredSize(new Dimension(70, 20));
        JLabel seedLabel = new JLabel("Seed:");
        seedField.setPreferredSize(new Dimension(70, 20));

        settingsPage.add(startButton, new GridBagConstraintsExtended(1, 0, new Insets(5, 15, 10, 5)));
        settingsPage.add(stopButton, new GridBagConstraintsExtended(2, 0));
        settingsPage.add(stepLabel, new GridBagConstraintsExtended(1, 1, GridBagConstraints.EAST, 1));
        settingsPage.add(nStepField, new GridBagConstraintsExtended(2, 1, GridBagConstraints.WEST, new Insets(5, 5, 10, 20)));
        settingsPage.add(seedLabel, new GridBagConstraintsExtended(1, 2, GridBagConstraints.EAST, 1));
        settingsPage.add(seedField, new GridBagConstraintsExtended(2, 2, GridBagConstraints.WEST, new Insets(5, 5, 10, 20)));
        settingsPage.add(runWithGuiCheckBox, new GridBagConstraintsExtended(1, 3, GridBagConstraints.CENTER, 2));
        settingsPage.add(selectedSimulationComboBox, new GridBagConstraintsExtended(0, 4, GridBagConstraints.CENTER, 99));


        startButton.addActionListener(e -> {
            if (runWithGuiCheckBox.isSelected()) startSimulationWithGui();
            else startSimulationWithoutGui();
        });
        stopButton.addActionListener(e -> stopSimulation());

        return settingsPage;
    }

    /**
     * Start the simulation without the GUI, results will be shown in a dialog
     */
    private void startSimulationWithoutGui() {
        threadFlag.set(true);
        switchButtonState();

        new Thread(() -> {
            SimulationType selectedOption = (SimulationType) selectedSimulationComboBox.getSelectedItem();
            AbstractSimulation simulation = selectedOption.createSimulation(threadFlag, false, (Integer) seedField.getValue());
            if (simulation != null) {
                simulation.setup();
                simulation.run((Integer) nStepField.getValue());
                long simDuration = simulation.getSimulationDuration();
                while (simDuration < 0) {
                    try {
                        Thread.sleep(10);
                        simDuration = simulation.getSimulationDuration();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                showDialog(selectedOption.toString(),
                        "Completed in " + simDuration
                        + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");
                stopSimulation();
            } else {
                // Handle case where simulation is not found
                System.out.println("Selected simulation not found.");
            }
        }).start();

    }

    /**
     * Start the simulation with the GUI
     */
    private void startSimulationWithGui() {
        threadFlag.set(true);
        switchButtonState();

        new Thread(() -> {
            SimulationType selectedOption = (SimulationType) selectedSimulationComboBox.getSelectedItem();
            AbstractSimulation simulation = selectedOption.createSimulation(threadFlag, true, (Integer) seedField.getValue());
            if (simulation != null) {
                simulation.setup();
                view = new RoadSimView();
                view.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                view.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowOpened(e);
                        stopSimulation();
                    }
                });
                view.setLocationRelativeTo(null);
                view.display();

                simulation.addSimulationListener(new RoadSimStatistics());
                simulation.addSimulationListener(view);

                simulation.run((Integer) nStepField.getValue());
            } else {
                // Handle case where simulation is not found
                System.out.println("Selected simulation not found.");
            }
        }).start();
    }

    /**
     * Show a dialog with the given title and message
     *
     * @param title   the title of the dialog
     * @param message the message shown inside the dialog
     */
    private void showDialog(String title, String message) {
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setTitle(title);
        JLabel label = new JLabel(message);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialog.add(label);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    /**
     * Switch the state of the start and stop buttons
     */
    private void switchButtonState() {
        startButton.setEnabled(!startButton.isEnabled());
        stopButton.setEnabled(!stopButton.isEnabled());
    }

    /**
     * Stop the simulation
     */
    private void stopSimulation() {
        threadFlag.set(false);
        if (view != null) {
            view.dispose();
        }
        switchButtonState();
    }

}
