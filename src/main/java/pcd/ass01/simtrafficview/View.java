package pcd.ass01.simtrafficview;

import pcd.ass01.simengineconc.ConcurrentAbstractSimulation;
import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficexamples.RoadSimStatistics;
import pcd.ass01.simtrafficexamples.RoadSimView;
import pcd.ass01.simtrafficexamples.TrafficSimulationWithCrossRoads;
import pcd.ass01.simtrafficexamplesconc.ConcurrentTrafficSimulationWithCrossRoads;

import javax.swing.*;
import java.lang.reflect.Array;

public class View extends JFrame {

    private JButton startButton;
    private JButton stopButton;
    private JPanel settingsPage;
    private JTextField nStepField;
    private RoadSimView roadSimView;
    private Thread simulationThread;
    private ExecutionFlag threadFlag;
    private RoadSimView view;
    private JComboBox<AbstractSimulation> selectedSimulation;


    public View() {
        super("SimTraffic");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 100);

        AbstractSimulation[] choices = { new TrafficSimulationWithCrossRoads(threadFlag),
                new ConcurrentTrafficSimulationWithCrossRoads(threadFlag)};


        selectedSimulation = new JComboBox<>(choices);

        settingsPage = new JPanel();
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        nStepField = new JTextField("10000");
        nStepField.setColumns(5);
        settingsPage.add(startButton);
        settingsPage.add(stopButton);
        settingsPage.add(nStepField);
        settingsPage.add(selectedSimulation);

        stopButton.setEnabled(false);
        threadFlag = new ExecutionFlag(true);
        startButton.addActionListener(e -> {

            threadFlag.set(true);
            startButton.setEnabled(false);
            stopButton.setEnabled(true);


            simulationThread = new Thread(() -> {
                TrafficSimulationWithCrossRoads simulation = new TrafficSimulationWithCrossRoads(threadFlag);
                simulation.setup();

                RoadSimStatistics stat = new RoadSimStatistics();
                view = new RoadSimView();
                view.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                view.setLocationRelativeTo(null);
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
            threadFlag.set(false);
            view.dispose();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        });


        add(settingsPage);
        setVisible(true);
    }
}

