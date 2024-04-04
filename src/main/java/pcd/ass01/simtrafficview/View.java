package pcd.ass01.simtrafficview;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficexamples.RoadSimStatistics;
import pcd.ass01.simtrafficexamples.RoadSimView;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadWithTrafficLightTwoCars;
import pcd.ass01.simtrafficexamples.TrafficSimulationWithCrossRoads;
import pcd.ass01.simtrafficexamplesconc.ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars;
import pcd.ass01.simtrafficexamplesconc.ConcurrentTrafficSimulationSingleRoadSeveralCars;
import pcd.ass01.simtrafficexamplesconc.ConcurrentTrafficSimulationWithCrossRoads;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class View extends JFrame {

    private final JButton startButton;
    private final JButton stopButton;
    private final JTextField nStepField;
    private Thread simulationThread;
    private final ExecutionFlag threadFlag;
    private RoadSimView view;


    public View() {
        super("SimTraffic");
        threadFlag = new ExecutionFlag(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 200);

        JComboBox<String> selectedSimulation = new JComboBox<>(new String[] {
            "TrafficSimulationSingleRoadWithTrafficLightTwoCars",
            "TrafficSimulationWithCrossRoads",
            "ConcurrentTrafficSimulationWithCrossRoads",
            "ConcurrentTrafficSimulationSingleRoadSeveralCars",
            "ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars"
        });

//        Map<String, AbstractSimulation> simulationsMap = new HashMap<>();
//       simulationsMap.put("TrafficSimulationSingleRoadWithTrafficLightTwoCars", new TrafficSimulationSingleRoadWithTrafficLightTwoCars(threadFlag));
//       simulationsMap.put("TrafficSimulationWithCrossRoads", new TrafficSimulationWithCrossRoads(threadFlag));
//       simulationsMap.put("ConcurrentTrafficSimulationWithCrossRoads", new ConcurrentTrafficSimulationWithCrossRoads(threadFlag));
//       simulationsMap.put("ConcurrentTrafficSimulationSingleRoadSeveralCars", new ConcurrentTrafficSimulationSingleRoadSeveralCars(threadFlag));
//       simulationsMap.put("ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars", new ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(100, threadFlag));

//        JComboBox<String> selectedSimulation = new JComboBox<>(simulationsMap.keySet().toArray(new String[0]));

        JPanel settingsPage = new JPanel();
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        nStepField = new JTextField("10000");
        nStepField.setColumns(5);
        settingsPage.add(startButton);
        settingsPage.add(stopButton);
        settingsPage.add(nStepField);
        settingsPage.add(selectedSimulation);

        stopButton.setEnabled(false);
        startButton.addActionListener(e -> {

            threadFlag.set(true);
            startButton.setEnabled(false);
            stopButton.setEnabled(true);


            simulationThread = new Thread(() -> {
                AbstractSimulation simulation = getSimulation(Objects.requireNonNull(selectedSimulation.getSelectedItem()).toString());
//                TODO ! setup should be set to public to work, better change the line above for best practice from AbstractSimulation to ????
//                simulation.setup();

                view = new RoadSimView();
                // Close the simulation when the window is closed
                view.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                view.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowOpened(e);
                        stopSimulation(startButton, stopButton);
                    }
                });
                view.setLocationRelativeTo(null);
                view.display();

                simulation.addSimulationListener(new RoadSimStatistics());
                simulation.addSimulationListener(view);

                simulation.run(Integer.parseInt(nStepField.getText()));
            });

            simulationThread.start();
        });

        stopButton.addActionListener(e -> {
            stopSimulation(startButton, stopButton);
        });

        add(settingsPage);
        setVisible(true);
    }


    private void stopSimulation(JButton startButton, JButton stopButton) {
        threadFlag.set(false);
        view.dispose();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private AbstractSimulation getSimulation(String simulationName){
        AbstractSimulation simulation = null;
        switch (simulationName) {
            case "TrafficSimulationSingleRoadWithTrafficLightTwoCars":
                simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars(threadFlag);
                break;
            case "TrafficSimulationWithCrossRoads":
                simulation = new TrafficSimulationWithCrossRoads(threadFlag);
                break;
            case "ConcurrentTrafficSimulationWithCrossRoads":
                simulation = new ConcurrentTrafficSimulationWithCrossRoads(threadFlag);
                break;
            case "ConcurrentTrafficSimulationSingleRoadSeveralCars":
                simulation = new ConcurrentTrafficSimulationSingleRoadSeveralCars(threadFlag);
                break;
            case "ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars":
                simulation = new ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(100, threadFlag);
                break;
        }
        return simulation;
    }
}

