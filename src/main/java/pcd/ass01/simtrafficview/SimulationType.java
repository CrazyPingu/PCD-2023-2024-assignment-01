package pcd.ass01.simtrafficview;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadWithTrafficLightTwoCars;
import pcd.ass01.simtrafficexamples.TrafficSimulationWithCrossRoads;
import pcd.ass01.simtrafficexamplesconc.ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars;
import pcd.ass01.simtrafficexamplesconc.ConcurrentTrafficSimulationSingleRoadSeveralCars;
import pcd.ass01.simtrafficexamplesconc.ConcurrentTrafficSimulationWithCrossRoads;

/**
 * Enum to represent the type of simulation
 */
public enum SimulationType {

    TRAFFIC_SIMULATION_WITH_LIGHTS("Traffic Simulation Single Road With Traffic Light (Two Cars)") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled) {
            return new TrafficSimulationSingleRoadWithTrafficLightTwoCars(threadFlag, guiEnabled);
        }
    },
    TRAFFIC_SIMULATION_WITH_CROSSROADS("Traffic Simulation With Crossroads") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled) {
            return new TrafficSimulationWithCrossRoads(threadFlag, guiEnabled);
        }
    },
    CONCURRENT_SIMULATION_WITH_CROSSROADS("Concurrent Traffic Simulation With Crossroads") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled) {
            return new ConcurrentTrafficSimulationWithCrossRoads(threadFlag, guiEnabled);
        }
    },
    CONCURRENT_SIMULATION_SINGLE_ROAD_SEVERAL_CARS("Concurrent Traffic Simulation Single Road Several Cars") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled) {
            return new ConcurrentTrafficSimulationSingleRoadSeveralCars(threadFlag, guiEnabled);
        }
    },
    CONCURRENT_SIMULATION_SINGLE_ROAD_MASSIVE_NUMBER_OF_CARS("Concurrent Traffic Simulation Single Road Massive Number Of Cars") {
        public AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled) {
            return new ConcurrentTrafficSimulationSingleRoadMassiveNumberOfCars(5000, threadFlag, guiEnabled);
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
     * @return the simulation
     */
    public abstract AbstractSimulation createSimulation(ExecutionFlag threadFlag, boolean guiEnabled);

    @Override
    public String toString() {
        return displayName;
    }

}
