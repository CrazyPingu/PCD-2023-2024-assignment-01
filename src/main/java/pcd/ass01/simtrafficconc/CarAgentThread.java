package pcd.ass01.simtrafficconc;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simtrafficview.ExecutionFlag;

public class CarAgentThread extends Thread {

    private final AbstractAgent car;
    private final int dt;
    private final int numberOfSteps;
    private int steps = 0;
    private final ExecutionFlag threadFlag;

    public CarAgentThread(AbstractAgent car, int dt, int numberOfSteps, ExecutionFlag threadFlag) {
        this.threadFlag = threadFlag;
        this.car = car;
        this.dt = dt;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public void run() {
        while (steps < numberOfSteps && threadFlag.get()) {
            car.step(dt);
            steps++;
        }
    }
}
