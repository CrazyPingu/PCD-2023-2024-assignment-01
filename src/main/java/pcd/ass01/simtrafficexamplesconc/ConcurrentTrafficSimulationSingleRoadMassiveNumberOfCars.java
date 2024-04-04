package pcd.ass01.simtrafficexamplesconc;

import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineconc.ConcurrentAbstractSimulation;
import pcd.ass01.simengineconc.StepMonitorImpl;
import pcd.ass01.simtrafficbase.P2d;
import pcd.ass01.simtrafficbase.CarAgent;
import pcd.ass01.simtrafficbase.Road;
import pcd.ass01.simtrafficbase.RoadsEnv;
import pcd.ass01.simtrafficconc.CarAgentBasic;
import pcd.ass01.simtrafficconc.ConcurrentRoadsEnv;
import pcd.ass01.simtrafficview.ExecutionFlag;
import pcd.ass01.utils.RandomGenerator;
import pcd.ass01.utils.RandomGeneratorImpl;

public class ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars extends ConcurrentAbstractSimulation {

    private int numCars;
    private boolean guiEnabled;

    public ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(int numCars, ExecutionFlag threadFlag, boolean guiEnabled) {
        super(threadFlag);
        this.numCars = numCars;
        this.guiEnabled = guiEnabled;
    }

    public ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(int numCars) {
        this(numCars, new ExecutionFlag(true), false);
    }



    public void setup() {
        this.setupTimings(0, 1);

        RandomGenerator gen = new RandomGeneratorImpl(1234);

        StepMonitor monitor = new StepMonitorImpl(numCars);

        RoadsEnv env = new ConcurrentRoadsEnv(monitor);
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
                    carMaxSpeed,
                    monitor);
            this.addAgent(car);

            /* no sync with wall-time */
            if(guiEnabled)
                this.syncWithTime(25);
        }

    }
}
	