package pcd.ass01.simtrafficconc;

import pcd.ass01.simengineseq.AbstractAgent;

public class CarAgentThread extends Thread {

    private final AbstractAgent car;
    private final int dt;
    private final int numberOfSteps;
    private int steps = 0;

    public CarAgentThread(AbstractAgent car, int dt, int numberOfSteps) {
        this.car = car;
        this.dt = dt;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public void run() {
        while (steps < numberOfSteps) {
            car.step(dt);
            steps++;
        }
    }
}
