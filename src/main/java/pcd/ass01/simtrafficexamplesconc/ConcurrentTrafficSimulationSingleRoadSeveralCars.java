package pcd.ass01.simtrafficexamplesconc;

import pcd.ass01.simengineconc.ConcurrentAbstractSimulation;
import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineconc.StepMonitorImpl;
import pcd.ass01.simtrafficbase.CarAgent;
import pcd.ass01.simtrafficbase.P2d;
import pcd.ass01.simtrafficbase.Road;
import pcd.ass01.simtrafficbase.RoadsEnv;
import pcd.ass01.simtrafficconc.CarAgentBasic;
import pcd.ass01.simtrafficconc.ConcurrentRoadsEnv;
import pcd.ass01.simtrafficview.ExecutionFlag;


/**
 * Traffic Simulation about a number of cars
 * moving on a single road, no traffic lights
 */
public class ConcurrentTrafficSimulationSingleRoadSeveralCars extends ConcurrentAbstractSimulation {

    private boolean guiEnabled = false;

    public ConcurrentTrafficSimulationSingleRoadSeveralCars() {
        this(new ExecutionFlag(true), false);
    }

    public ConcurrentTrafficSimulationSingleRoadSeveralCars(ExecutionFlag threadFlag, boolean guiEnabled) {
        super(threadFlag);
        this.guiEnabled = guiEnabled;
    }

    public ConcurrentTrafficSimulationSingleRoadSeveralCars(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
        super(threadFlag, seed);
        this.guiEnabled = guiEnabled;
    }

    public void setup() {

        int nCars = 5;

        StepMonitor monitor = new StepMonitorImpl(nCars);

        this.setupTimings(0, 1);

        RoadsEnv env = new ConcurrentRoadsEnv(monitor);
        this.setupEnvironment(env);

        Road road = env.createRoad(new P2d(0, 300), new P2d(1500, 300));

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
                    carMaxSpeed,
                    monitor);
            this.addAgent(car);
        }

        if (guiEnabled)
            this.syncWithTime(25);
    }
}
	