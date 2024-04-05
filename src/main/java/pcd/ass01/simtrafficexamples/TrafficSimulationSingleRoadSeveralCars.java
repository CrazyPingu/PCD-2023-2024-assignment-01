package pcd.ass01.simtrafficexamples;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficbase.*;
import pcd.ass01.simtrafficview.ExecutionFlag;

/**
 * Traffic Simulation about a number of cars
 * moving on a single road, no traffic lights
 */
public class TrafficSimulationSingleRoadSeveralCars extends AbstractSimulation {

    private boolean guiEnabled = true;

    public TrafficSimulationSingleRoadSeveralCars() {
        super(new ExecutionFlag(true));
    }

    public TrafficSimulationSingleRoadSeveralCars(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
        super(threadFlag, seed);
        this.guiEnabled = guiEnabled;
    }

    public void setup() {

        this.setupTimings(0, 1);

        RoadsEnv env = new RoadsEnv();
        this.setupEnvironment(env);

        Road road = env.createRoad(new P2d(0, 300), new P2d(1500, 300));

        int nCars = 30;

        for (int i = 0; i < nCars; i++) {

            String carId = "car-" + i;
            // double initialPos = i*30;
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
        }

        if (guiEnabled)
            this.syncWithTime(25);
    }
}
	