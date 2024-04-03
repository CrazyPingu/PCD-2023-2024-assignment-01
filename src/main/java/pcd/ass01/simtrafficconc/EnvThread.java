package pcd.ass01.simtrafficconc;

import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simtrafficview.ExecutionFlag;

public class EnvThread extends Thread {

    private final AbstractEnvironment env;
    private final int dt;
    private final int numberOfSteps;
    private int steps = 0;
    private ExecutionFlag threadFlag;

    public EnvThread(AbstractEnvironment env, int dt, int numberOfSteps, ExecutionFlag threadFlag) {
        this.threadFlag = threadFlag;
        this.env = env;
        this.dt = dt;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public void run() {
        while (steps < numberOfSteps && threadFlag.get()) {
            env.step(dt);
            steps++;
        }
    }

}
