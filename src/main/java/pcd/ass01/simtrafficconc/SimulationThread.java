package pcd.ass01.simtrafficconc;


import pcd.ass01.simengineconc.Callback;
import pcd.ass01.simengineseq.AbstractSimulation;

public class SimulationThread extends Thread {

    private final Callback simSteps;

    public SimulationThread(Callback simSteps) {
        super();
        this.simSteps = simSteps;
    }

    @Override
    public void run() {
        simSteps.call();
    }

}
