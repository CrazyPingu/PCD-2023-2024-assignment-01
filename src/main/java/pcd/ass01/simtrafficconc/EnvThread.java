package pcd.ass01.simtrafficconc;

import pcd.ass01.simengineseq.AbstractEnvironment;

public class EnvThread extends Thread {

    private final AbstractEnvironment env;
    private final int dt;
    private final int numberOfSteps;
    private int steps = 0;

    public EnvThread(AbstractEnvironment env, int dt, int numberOfSteps) {
        this.env = env;
        this.dt = dt;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public void run() {
        while (steps < numberOfSteps) {
            env.step(dt);
            steps++;
        }
    }

}