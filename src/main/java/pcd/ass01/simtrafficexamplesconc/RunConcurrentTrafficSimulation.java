package pcd.ass01.simtrafficexamplesconc;

import gov.nasa.jpf.vm.Verify;
import pcd.ass01.simengineconc.ConcurrentAbstractSimulation;
import pcd.ass01.simtrafficbase.RoadsEnv;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadWithTrafficLightTwoCars;
import pcd.ass01.utils.RoadEnvAnalyzer;

/**
 * 
 * Main class to create and run a simulation
 * 
 */
public class RunConcurrentTrafficSimulation {

	public static void main(String[] args) {

//		 ConcurrentTrafficSimulationSingleRoadSeveralCars simulation = new ConcurrentTrafficSimulationSingleRoadSeveralCars();
//		 ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars simulation = new ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(100);
//		ConcurrentTrafficSimulationSingleRoadWithTrafficLightTwoCars simulation = new ConcurrentTrafficSimulationSingleRoadWithTrafficLightTwoCars();
		ConcurrentTrafficSimulationWithCrossRoads simulation = new ConcurrentTrafficSimulationWithCrossRoads();
		simulation.setup();

//		RoadSimStatistics stat = new RoadSimStatistics();
//		RoadSimView view = new RoadSimView();
//		view.display();
//
//		simulation.addSimulationListener(stat);
//		simulation.addSimulationListener(view);

		int nSteps = 10000;

		log("Running the simulation: " + ((RoadsEnv) simulation.getEnvironment()).getAgentInfo().size() + " cars, for " + nSteps + " steps ...");

		Verify.beginAtomic();

		simulation.run(nSteps);

		while (simulation.getSimulationDuration() <= 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Verify.endAtomic();
		long d = simulation.getSimulationDuration();
		log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");

//		RoadEnvAnalyzer.logEnv((RoadsEnv) simulation.getEnvironment());
		RoadEnvAnalyzer.saveEnvToFile((RoadsEnv) simulation.getEnvironment(), "test-conc.txt");
	}

	private static void log(String msg) {
		System.out.println("[ SIMULATION ] " + msg);
	}

}
