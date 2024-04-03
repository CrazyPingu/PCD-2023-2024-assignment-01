package pcd.ass01.simtrafficview;

import pcd.ass01.simtrafficexamples.RoadSimStatistics;
import pcd.ass01.simtrafficexamples.RoadSimView;
import pcd.ass01.simtrafficexamples.TrafficSimulationWithCrossRoads;

import javax.swing.*;

public class View extends JFrame {

    private JButton startButton;
    private JButton stopButton;
    private JPanel settingsPage;
    private JTextField nStepField;
    private RoadSimView roadSimView;
    private Thread simulationThread;
    private TrafficSimulationWithCrossRoads simulation;
    private boolean threadFlag;


    public View() {
        super("SimTraffic");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 100);
        settingsPage = new JPanel();
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        nStepField = new JTextField("10000");
        nStepField.setColumns(5);
        settingsPage.add(startButton);
        settingsPage.add(stopButton);
        settingsPage.add(nStepField);

        stopButton.setEnabled(false);
        startButton.addActionListener(e -> {

            threadFlag = true;
            startButton.setEnabled(false);
            stopButton.setEnabled(true);


            simulationThread = new Thread(() -> {
                simulation = new TrafficSimulationWithCrossRoads(threadFlag);
                simulation.setup();

                RoadSimStatistics stat = new RoadSimStatistics();
                RoadSimView view = new RoadSimView();
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.display();

                simulation.addSimulationListener(stat);
                simulation.addSimulationListener(view);

                simulation.run(Integer.parseInt(nStepField.getText()));

            });

            simulationThread.start();


//            roadSimView = new RoadSimView();
//            roadSimView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//            roadSimView.display();
        });

        stopButton.addActionListener(e -> {
            threadFlag = false;
//            simulationThread.interrupt();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        });


        add(settingsPage);
        setVisible(true);
    }


}
