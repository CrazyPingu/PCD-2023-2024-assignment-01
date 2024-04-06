package pcd.ass01.simtrafficconc;


import pcd.ass01.simengineconc.Callback;
import pcd.ass01.simengineseq.AbstractSimulation;

public class SimulationThread extends Thread {

    private final AbstractSimulation simulation;
    private final Callback simSteps;

    public SimulationThread(AbstractSimulation simulation, Callback simSteps) {
        super();
        this.simulation = simulation;
        this.simSteps = simSteps;
    }

    @Override
    public void run() {
        simSteps.call();
    }

}
