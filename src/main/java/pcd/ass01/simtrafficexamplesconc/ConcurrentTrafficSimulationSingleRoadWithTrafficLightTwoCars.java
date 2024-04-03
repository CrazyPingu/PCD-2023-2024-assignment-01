package pcd.ass01.simtrafficexamplesconc;

import pcd.ass01.simengineconc.ConcurrentAbstractSimulation;
import pcd.ass01.simtrafficbase.*;
import pcd.ass01.simtrafficview.ExecutionFlag;
import pcd.ass01.utils.RandomGenerator;
import pcd.ass01.utils.RandomGeneratorImpl;


/**
 * 
 * Traffic Simulation about 2 cars moving on a single road, with one semaphore
 * 
 */
public class ConcurrentTrafficSimulationSingleRoadWithTrafficLightTwoCars extends ConcurrentAbstractSimulation {

	public ConcurrentTrafficSimulationSingleRoadWithTrafficLightTwoCars() {
		super(new ExecutionFlag(true));
	}
	
	public void setup() {

		RandomGenerator gen = new RandomGeneratorImpl(1234);

		this.setupTimings(0, 1);
		
		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);
				
		Road r = env.createRoad(new P2d(0,300), new P2d(1500,300));

		TrafficLight tl = env.createTrafficLight(new P2d(740,300), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
		r.addTrafficLight(tl, 740);
		
		CarAgent car1 = new CarAgentExtended("car-1", env, r, 0, 0.1, 0.3, 6);
		this.addAgent(car1);		
		CarAgent car2 = new CarAgentExtended("car-2", env, r, 100, 0.1, 0.3, 5);
		this.addAgent(car2);

		this.syncWithTime(25);
	}	
	
}
