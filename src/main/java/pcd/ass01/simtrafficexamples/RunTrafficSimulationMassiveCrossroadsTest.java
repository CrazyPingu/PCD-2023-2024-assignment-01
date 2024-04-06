package pcd.ass01.simtrafficexamples;

import pcd.ass01.simtrafficbase.RoadsEnv;
import pcd.ass01.utils.RoadEnvAnalyzer;

public class RunTrafficSimulationMassiveCrossroadsTest {

    public static void main(String[] args) {

        int nSteps = 100;

        TrafficSimulationMultipleCrossroadsMassiveNumberOfCars simulation = new TrafficSimulationMultipleCrossroadsMassiveNumberOfCars();
        simulation.setup();

        log("Running the simulation: " + ((RoadsEnv) simulation.getEnvironment()).getAgentInfo().size() + " cars, for " + nSteps + " steps ...");

        simulation.run(nSteps);

        long d = simulation.getSimulationDuration();
        log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");

//		RoadEnvAnalyzer.logEnv((RoadsEnv) simulation.getEnvironment());
        RoadEnvAnalyzer.saveEnvToFile((RoadsEnv) simulation.getEnvironment(), "test-seq.txt");
    }

    private static void log(String msg) {
        System.out.println("[ SIMULATION ] " + msg);
    }

}
