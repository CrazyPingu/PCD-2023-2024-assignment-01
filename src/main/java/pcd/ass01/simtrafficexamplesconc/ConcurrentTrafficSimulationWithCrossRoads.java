package pcd.ass01.simtrafficexamplesconc;

import pcd.ass01.simengineconc.ConcurrentAbstractSimulation;
import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineconc.StepMonitorImpl;

import pcd.ass01.simtrafficbase.*;
import pcd.ass01.simtrafficconc.CarAgentExtended;
import pcd.ass01.simtrafficconc.ConcurrentRoadsEnv;
import pcd.ass01.simtrafficview.ExecutionFlag;
import pcd.ass01.utils.RandomGenerator;
import pcd.ass01.utils.RandomGeneratorImpl;

import java.util.Optional;

public class ConcurrentTrafficSimulationWithCrossRoads extends ConcurrentAbstractSimulation {

	public ConcurrentTrafficSimulationWithCrossRoads() {
		super(new ExecutionFlag(true));
	}
	
	public void setup() {

		RandomGenerator gen = new RandomGeneratorImpl(1234);

		StepMonitor monitor = new StepMonitorImpl(4);

		this.setupTimings(0, 1);
		
		RoadsEnv env = new ConcurrentRoadsEnv(monitor);
		this.setupEnvironment(env);
				
		TrafficLight tl1 = env.createTrafficLight(new P2d(740,300), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
		
		Road r1 = env.createRoad(new P2d(0,300), new P2d(1500,300));
		r1.addTrafficLight(tl1, 740);

//		Double acc = gen.nextDouble(0.05, 0.2);
//		Double dec = gen.nextDouble(0.2, 0.4);
//		CarAgent car1 = new CarAgentExtended("car-1", env, r1, 0, acc, dec, gen.nextDouble(5.5, 6.5), monitor);
//		this.addAgent(car1);
//		CarAgent car2 = new CarAgentExtended("car-2", env, r1, 100, acc, dec, gen.nextDouble(4.5, 5.5), monitor);
//		this.addAgent(car2);
		CarAgent car1 = new CarAgentExtended("car-1", env, r1, 0, 0.1, 0.3, 6, monitor);
		this.addAgent(car1);
		CarAgent car2 = new CarAgentExtended("car-2", env, r1, 100, 0.1, 0.3, 5, monitor);
		this.addAgent(car2);
		
		TrafficLight tl2 = env.createTrafficLight(new P2d(750,290),  TrafficLight.TrafficLightState.RED, 75, 25, 100);

		Road r2 = env.createRoad(new P2d(750,0), new P2d(750,600));
		r2.addTrafficLight(tl2, 290);

//		CarAgent car3 = new CarAgentExtended("car-3", env, r2, 0, acc, gen.nextDouble(0.1, 0.25), gen.nextDouble(4.5, 5.5), monitor);
//		this.addAgent(car3);
//		CarAgent car4 = new CarAgentExtended("car-4", env, r2, 100, acc, gen.nextDouble(0.07, 0.15), gen.nextDouble(3.5, 4.5), monitor);
//		this.addAgent(car4);
		CarAgent car3 = new CarAgentExtended("car-3", env, r2, 0, 0.1, 0.2, 5, monitor);
		this.addAgent(car3);
		CarAgent car4 = new CarAgentExtended("car-4", env, r2, 100, 0.1, 0.1, 4, monitor);
		this.addAgent(car4);

		
		this.syncWithTime(25);
	}	
	
}
