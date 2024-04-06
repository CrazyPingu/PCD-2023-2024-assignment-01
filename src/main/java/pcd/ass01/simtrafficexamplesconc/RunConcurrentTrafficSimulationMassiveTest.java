package pcd.ass01.simtrafficexamplesconc;

import gov.nasa.jpf.vm.Verify;
import pcd.ass01.simtrafficbase.RoadsEnv;
import pcd.ass01.utils.RoadEnvAnalyzer;

public class RunConcurrentTrafficSimulationMassiveTest {

    public static void main(String[] args) {

        int numCars = 5000;
        int nSteps = 100;

        ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars simulation = new ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(numCars);
        simulation.setup();

        log("Running the simulation: " + numCars + " cars, for " + nSteps + " steps ...");

        Verify.beginAtomic();

        simulation.run(nSteps);

        while (simulation.getSimulationDuration() < 0) {
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
