package pcd.ass01.simtrafficview;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficexamples.RoadSimStatistics;
import pcd.ass01.simtrafficexamples.RoadSimView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View extends JFrame {

    private final JButton startButton;
    private final JButton stopButton;
    private final JTextField nStepField;
    private final ExecutionFlag threadFlag;
    private RoadSimView view;
    private final JComboBox<SimulationType> selectedSimulationComboBox;

    public View() {
        super("SimTraffic");
        threadFlag = new ExecutionFlag(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 200);

        JPanel settingsPage = new JPanel();


        startButton = new JButton("Start");
        settingsPage.add(startButton);

        stopButton = new JButton("Stop");
        settingsPage.add(stopButton);

        nStepField = new JTextField("10000");
        nStepField.setColumns(5);

        settingsPage.add(nStepField);
        selectedSimulationComboBox = new JComboBox<>(SimulationType.values());
        settingsPage.add(selectedSimulationComboBox);

        stopButton.setEnabled(false);
        startButton.addActionListener(e -> startSimulation());
        stopButton.addActionListener(e -> stopSimulation());

        add(settingsPage);
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
