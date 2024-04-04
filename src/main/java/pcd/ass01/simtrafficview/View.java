package pcd.ass01.simtrafficview;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficexamples.RoadSimStatistics;
import pcd.ass01.simtrafficexamples.RoadSimView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View extends JFrame {

    private final JButton startButton;
    private final JButton stopButton;
    private final JTextField nStepField;
    private final ExecutionFlag threadFlag;
    private final JComboBox<SimulationType> selectedSimulationComboBox;
    private final JCheckBox runWithGuiCheckBox;
    private RoadSimView view;

    public View() {
        super();
        threadFlag = new ExecutionFlag(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 200);
        JPanel settingsPage = new JPanel(new GridBagLayout());


        startButton = new JButton("Start");
        settingsPage.add(startButton, new GridBagConstraintsExtended(1, 0, new Insets(5, 15, 10, 5)));


        stopButton = new JButton("Stop");
        settingsPage.add(stopButton, new GridBagConstraintsExtended(2, 0));

        JLabel stepLabel = new JLabel("Step:");
        settingsPage.add(stepLabel, new GridBagConstraintsExtended(1, 1, GridBagConstraints.EAST, 1));

        nStepField = new JTextField("10000");
        nStepField.setPreferredSize(new Dimension(70, 20));
        settingsPage.add(nStepField, new GridBagConstraintsExtended(2, 1, GridBagConstraints.WEST, new Insets(5, 5, 10, 20)));

        runWithGuiCheckBox = new JCheckBox("Start simulation with GUI");
        runWithGuiCheckBox.setSelected(true);
        settingsPage.add(runWithGuiCheckBox, new GridBagConstraintsExtended(1, 2, GridBagConstraints.CENTER, 2));

        selectedSimulationComboBox = new JComboBox<>(SimulationType.values());
        settingsPage.add(selectedSimulationComboBox, new GridBagConstraintsExtended(0, 3, GridBagConstraints.CENTER, 99));

        stopButton.setEnabled(false);
        startButton.addActionListener(e -> startSimulation());
        stopButton.addActionListener(e -> stopSimulation());
        runWithGuiCheckBox.addActionListener(e -> {
            if (runWithGuiCheckBox.isSelected()) {
                settingsPage.add(selectedSimulationComboBox, new GridBagConstraintsExtended(0, 3, GridBagConstraints.CENTER, 99));
            } else {
                settingsPage.remove(selectedSimulationComboBox);
            }
            settingsPage.revalidate();
            settingsPage.repaint();
            pack();
        });

        add(settingsPage);
        pack();
        setVisible(true);
    }


    private void startSimulation() {
        threadFlag.set(true);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);

        Thread simulationThread = new Thread(() -> {
            SimulationType selectedOption = (SimulationType) selectedSimulationComboBox.getSelectedItem();
            AbstractSimulation simulation = selectedOption.createSimulation(threadFlag);
            if (simulation != null) {
                // TODO ! setup should be set to public to work, better change the line above for best
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

                simulation.run(Integer.parseInt(nStepField.getText()));
            } else {
                // Handle case where simulation is not found
                System.out.println("Selected simulation not found.");
            }
        });

        simulationThread.start();
    }

    private void stopSimulation() {
        threadFlag.set(false);
        view.dispose();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

}
