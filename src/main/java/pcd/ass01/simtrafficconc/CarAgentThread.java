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
        System.out.println("Car " + car.getId() + " created");
    }

    @Override
    public void run() {
        while (steps < numberOfSteps) {
            System.out.println("  " + car.getId() + " step start " + steps);
            car.step(dt);
            System.out.println("  " + car.getId() + " step finish " + steps);
            steps++;
        }
    }
}
