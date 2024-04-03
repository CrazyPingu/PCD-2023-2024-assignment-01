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
import pcd.ass01.utils.RandomGenerator;
import pcd.ass01.utils.RandomGeneratorImpl;

import java.util.Optional;

/**
 * 
 * Traffic Simulation about a number of cars 
 * moving on a single road, no traffic lights
 * 
 */
public class ConcurrentTrafficSimulationSingleRoadSeveralCars extends ConcurrentAbstractSimulation {

	public ConcurrentTrafficSimulationSingleRoadSeveralCars() {
		super(new ExecutionFlag(true));
	}
	
	public void setup() {

		RandomGenerator gen = new RandomGeneratorImpl(1234);

		int nCars = 5;

		StepMonitor monitor = new StepMonitorImpl(nCars);

		this.setupTimings(0, 1);

		RoadsEnv env = new ConcurrentRoadsEnv(monitor);
		this.setupEnvironment(env);
		
		Road road = env.createRoad(new P2d(0,300), new P2d(1500,300));

		for (int i = 0; i < nCars; i++) {
			
			String carId = "car-" + i;
			// double initialPos = i*30;
			double initialPos = i*10;
			
			double carAcceleration = 1; //  + gen.nextDouble()/2;
			double carDeceleration = 0.3; //  + gen.nextDouble()/2;
			double carMaxSpeed = 7; // 4 + gen.nextDouble();

//			if (i == 3) {
//				carAcceleration = 0.2;
//				carMaxSpeed = 8;
//			}

			CarAgent car = new CarAgentBasic(carId, env,
									road,
									initialPos, 
									carAcceleration, 
									carDeceleration,
									carMaxSpeed,
									monitor);
			this.addAgent(car);
		}
		
		this.syncWithTime(5);
	}	
}
	