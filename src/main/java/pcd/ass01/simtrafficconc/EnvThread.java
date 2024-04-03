package pcd.ass01.simtrafficconc;

import gov.nasa.jpf.vm.Verify;
import pcd.ass01.simengineseq.AbstractEnvironment;

public class EnvThread extends Thread {

    private final AbstractEnvironment env;
    private final int dt;
    private final int numberOfSteps;
    private int steps = 0;
    private boolean threadFlag;

    public EnvThread(AbstractEnvironment env, int dt, int numberOfSteps, boolean threadFlag) {
        this.threadFlag = threadFlag;
        this.env = env;
        this.dt = dt;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public void run() {
        while (steps < numberOfSteps && threadFlag) {
            env.step(dt);
            steps++;
        }
    }

}
