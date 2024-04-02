package pcd.ass01.simtrafficexamplesconc_improved;

import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineconc_improved.StepMonitorImpl;
import pcd.ass01.simengineseq_improved.AbstractSimulation;
import pcd.ass01.simtrafficbase.P2d;
import pcd.ass01.simtrafficbase_improved.CarAgent;
import pcd.ass01.simtrafficbase_improved.Road;
import pcd.ass01.simtrafficbase_improved.RoadsEnv;
import pcd.ass01.simtrafficconc_improved.CarAgentBasic;
import pcd.ass01.simtrafficconc_improved.ConcurrentRoadsEnv;
import pcd.ass01.utils.RandomGenerator;
import pcd.ass01.utils.RandomGeneratorImpl;

public class ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars extends AbstractSimulation {

    private int numCars;

    public ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(int numCars) {
        super();
        this.numCars = numCars;
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
        }

    }
}
	