package pcd.ass01.simtrafficview;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficexamples.*;

import pcd.ass01.simtrafficexamplesconc.*;

/**
 * Enum to represent the type of simulation
 */
public enum SimulationType {

    TRAFFIC_SIMULATION_WITH_LIGHTS("Traffic Simulation Single Road With Traffic Light (Two Cars)") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new TrafficSimulationSingleRoadWithTrafficLightTwoCars(threadFlag, guiEnabled);
        }
    },
    TRAFFIC_SIMULATION_WITH_CROSSROADS("Traffic Simulation With Crossroads") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new TrafficSimulationWithCrossRoads(threadFlag, guiEnabled);
        }
    },

    TRAFFIC_SIMULATION_SINGLE_ROAD_SEVERAL_CARS("Traffic Simulation Single Road Several Cars") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new TrafficSimulationSingleRoadSeveralCars(threadFlag, guiEnabled, seed);
        }
    },
    TRAFFIC_SIMULATION_SINGLE_ROAD_TWO_CARS("Traffic Simulation Single Road Two Cars") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new TrafficSimulationSingleRoadTwoCars(threadFlag, guiEnabled);
        }
    },
    TRAFFIC_SIMULATION_SINGLE_ROAD_MASSIVE_NUMBER_OF_CARS("Traffic Simulation Single Road Massive Number Of Cars") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new TrafficSimulationSingleRoadMassiveNumberOfCars(5000, threadFlag, guiEnabled, seed);
        }
    },
    TRAFFIC_SIMULATION_MULTIPLE_CROSSROADS_MASSIVE_NUMBER_OF_CARS("Traffic Simulation Multiple Crossroads Massive Number Of Cars") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new TrafficSimulationMultipleCrossroadsMassiveNumberOfCars(threadFlag, guiEnabled, seed);
        }
    },
    CONCURRENT_SIMULATION_WITH_CROSSROADS("Concurrent Traffic Simulation With Crossroads") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new ConcurrentTrafficSimulationWithCrossRoads(threadFlag, guiEnabled, seed);
        }
    },
    CONCURRENT_SIMULATION_SINGLE_ROAD_SEVERAL_CARS("Concurrent Traffic Simulation Single Road Several Cars") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new ConcurrentTrafficSimulationSingleRoadSeveralCars(threadFlag, guiEnabled, seed);
        }
    },
    CONCURRENT_SIMULATION_SINGLE_ROAD_MASSIVE_NUMBER_OF_CARS("Concurrent Traffic Simulation Single Road Massive Number Of Cars") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(5000, threadFlag, guiEnabled, seed);
        }
    },
    CONCURRENT_SIMULATION_SINGLE_ROAD_WITH_LIGHTS("Concurrent Traffic Simulation Single Road With Traffic Light (Two Cars)") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new ConcurrentTrafficSimulationSingleRoadWithTrafficLightTwoCars(threadFlag, guiEnabled, seed);
        }
    },
    CONCURRENT_TRAFFIC_SIMULATION_MULTIPLE_ROADS_WITH_CROSSROADS("Concurrent Traffic Simulation Multiple Roads With Crossroads") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new ConcurrentTrafficSimulationMultipleRoadsWithCrossroads(threadFlag, guiEnabled, seed);
        }
    },
    CONCURRENT_TRAFFIC_SIMULATION_MULTIPLE_CROSSROADS_MASSIVE_NUMBER_OF_CARS("Concurrent Traffic Simulation Multiple Crossroads Massive Number Of Cars") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
            return new ConcurrentTrafficSimulationMultipleCrossroadsMassiveNumberOfCars(threadFlag, guiEnabled, seed);
        }
    };

    private final String displayName;

    SimulationType(String displayName) {
        this.displayName = displayName;
    }


    /**
     * Create the simulation
     *
     * @param threadFlag the flag to stop the simulation
     * @param guiEnabled if the GUI must be shown or not
     * @param seed the seed for the random generator
     * @return the simulation
     */
    public abstract AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled, int seed);

    @Override
    public String toString() {
        return displayName;
    }

}
