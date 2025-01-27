package pcd.ass01.simtrafficexamples;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficbase.*;
import pcd.ass01.simtrafficview.ExecutionFlag;

public class TrafficSimulationSingleRoadMassiveNumberOfCars extends AbstractSimulation {

    private int numCars;
    private boolean guiEnabled;

    public TrafficSimulationSingleRoadMassiveNumberOfCars(int numCars) {
        super(new ExecutionFlag(true));
        this.numCars = numCars;
    }

    public TrafficSimulationSingleRoadMassiveNumberOfCars(int numCars, ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
        super(threadFlag, seed);
        this.numCars = numCars;
        this.guiEnabled = guiEnabled;
    }

    public void setup() {
        this.setupTimings(0, 1);

        RoadsEnv env = new RoadsEnv();
        this.setupEnvironment(env);

        Road road = env.createRoad(new P2d(0, 300), new P2d(15000, 300));

        for (int i = 0; i < numCars; i++) {

            String carId = "car-" + i;
            double initialPos = i * 10;
            double carAcceleration = 1 + gen.nextDouble() / 2;
            double carDeceleration = 0.3 + gen.nextDouble() / 2;
            double carMaxSpeed = 4 + gen.nextDouble();

            CarAgent car = new CarAgentBasic(carId, env,
                    road,
                    initialPos,
                    carAcceleration,
                    carDeceleration,
                    carMaxSpeed);
            this.addAgent(car);

            /* no sync with wall-time */
            if (guiEnabled)
                this.syncWithTime(25);
        }

    }
}
	