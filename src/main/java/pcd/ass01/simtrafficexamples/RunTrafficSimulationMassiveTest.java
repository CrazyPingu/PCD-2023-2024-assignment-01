package pcd.ass01.simtrafficexamples;

import pcd.ass01.simtrafficbase.RoadsEnv;
import pcd.ass01.utils.RoadEnvAnalyzer;

public class RunTrafficSimulationMassiveTest {

	public static void main(String[] args) {		

		int numCars = 50;
		int nSteps = 100;
		
		TrafficSimulationSingleRoadMassiveNumberOfCars simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars);
		simulation.setup();
		
		log("Running the simulation: " + numCars + " cars, for " + nSteps + " steps ...");
		
		simulation.run(nSteps);

		long d = simulation.getSimulationDuration();
		log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");

//		RoadEnvAnalyzer.logEnv((RoadsEnv) simulation.getEnvironment());
		RoadEnvAnalyzer.saveEnvToFile((RoadsEnv) simulation.getEnvironment(), "seq-single-road-massive.txt");
	}
	
	private static void log(String msg) {
		System.out.println("[ SIMULATION ] " + msg);
	}
}
