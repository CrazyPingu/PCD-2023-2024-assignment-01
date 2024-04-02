package pcd.ass01.simtrafficexamplesconc;

import pcd.ass01.simtrafficexamples.RoadSimStatistics;
import pcd.ass01.simtrafficexamples.RoadSimView;
import pcd.ass01.simtrafficexamples.TrafficSimulationWithCrossRoads;

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
		
		RoadSimStatistics stat = new RoadSimStatistics();
		RoadSimView view = new RoadSimView();
		view.display();
		
		simulation.addSimulationListener(stat);
		simulation.addSimulationListener(view);		
		simulation.run(10000);
	}
}
