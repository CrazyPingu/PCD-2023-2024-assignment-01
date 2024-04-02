package pcd.ass01.simtrafficexamplesconc;

import pcd.ass01.simtrafficbase.RoadsEnv;
import pcd.ass01.utils.RoadEnvAnalyzer;

public class RunConcurrentTrafficSimulationMassiveTest {

	public static void main(String[] args) {		

		int numCars = 5000;
		int nSteps = 100;
		
		ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars simulation = new ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(numCars);
		simulation.setup();
		
		log("Running the simulation: " + numCars + " cars, for " + nSteps + " steps ...");
		
		simulation.run(nSteps);

		long d = simulation.getSimulationDuration();
		log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");

//		RoadEnvAnalyzer.logEnv((RoadsEnv) simulation.getEnvironment());
		RoadEnvAnalyzer.saveEnvToFile((RoadsEnv) simulation.getEnvironment(), "conc-single-road-massive.txt");
	}
	
	private static void log(String msg) {
		System.out.println("[ SIMULATION ] " + msg);
	}
}
