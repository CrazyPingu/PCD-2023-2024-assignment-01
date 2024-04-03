package pcd.ass01.simtrafficconc;

import pcd.ass01.simengineseq.AbstractAgent;

public class CarAgentThread extends Thread {

    private final AbstractAgent car;
    private final int dt;
    private final int numberOfSteps;
    private int steps = 0;
    private boolean threadFlag;

    public CarAgentThread(AbstractAgent car, int dt, int numberOfSteps, boolean threadFlag) {
        this.threadFlag = threadFlag;
        this.car = car;
        this.dt = dt;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public void run() {
        while (steps < numberOfSteps && threadFlag) {
            car.step(dt);
            steps++;
        }
    }
}
