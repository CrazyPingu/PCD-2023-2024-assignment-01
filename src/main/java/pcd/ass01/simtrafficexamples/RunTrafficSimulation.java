package pcd.ass01.simtrafficexamples;

import pcd.ass01.simtrafficbase.RoadsEnv;
import pcd.ass01.utils.RoadEnvAnalyzer;

/**
 * 
 * Main class to create and run a simulation
 * 
 */
public class RunTrafficSimulation {

	public static void main(String[] args) {		

		// var simulation = new TrafficSimulationSingleRoadTwoCars();
		// TrafficSimulationSingleRoadSeveralCars simulation = new TrafficSimulationSingleRoadSeveralCars();
		// var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
		TrafficSimulationWithCrossRoads simulation = new TrafficSimulationWithCrossRoads();
		simulation.setup();
		
//		RoadSimStatistics stat = new RoadSimStatistics();
//		RoadSimView view = new RoadSimView();
//		view.display();
//
//		simulation.addSimulationListener(stat);
//		simulation.addSimulationListener(view);

		int nSteps = 10000;

		log("Running the simulation: " + ((RoadsEnv) simulation.getEnvironment()).getAgentInfo().size() + " cars, for " + nSteps + " steps ...");

		simulation.run(nSteps);

		long d = simulation.getSimulationDuration();
		log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");

//		RoadEnvAnalyzer.logEnv((RoadsEnv) simulation.getEnvironment());
		RoadEnvAnalyzer.saveEnvToFile((RoadsEnv) simulation.getEnvironment(), "seq-cross-roads.txt");
	}

	private static void log(String msg) {
		System.out.println("[ SIMULATION ] " + msg);
	}

}
