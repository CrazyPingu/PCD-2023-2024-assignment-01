package pcd.ass01.simtrafficexamplesconc;

import pcd.ass01.simtrafficbase.RoadsEnv;
import pcd.ass01.utils.RoadEnvAnalyzer;

/**
 * 
 * Main class to create and run a simulation
 * 
 */
public class RunConcurrentTrafficSimulation {

	public static void main(String[] args) {

		// ConcurrentTrafficSimulationSingleRoadSeveralCars simulation = new ConcurrentTrafficSimulationSingleRoadSeveralCars();
//		 ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars simulation = new ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(3);
		// var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
		ConcurrentTrafficSimulationWithCrossRoads simulation = new ConcurrentTrafficSimulationWithCrossRoads();
		simulation.setup();
		
//		RoadSimStatistics stat = new RoadSimStatistics();
//		RoadSimView view = new RoadSimView();
//		view.display();
		
//		simulation.addSimulationListener(stat);
//		simulation.addSimulationListener(view);

		int nSteps = 100;

		log("Running the simulation: " + ((RoadsEnv) simulation.getEnvironment()).getAgentInfo().size() + " cars, for " + nSteps + " steps ...");

		simulation.run(nSteps);

		long d = simulation.getSimulationDuration();
		log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");

		RoadEnvAnalyzer.logEnv((RoadsEnv) simulation.getEnvironment());
	}

	private static void log(String msg) {
		System.out.println("[ SIMULATION ] " + msg);
	}

}
