package pcd.ass01.simtrafficexamplesconc;

import pcd.ass01.simengineconc.ConcurrentAbstractSimulation;
import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineconc.StepMonitorImpl;
import pcd.ass01.simtrafficbase.*;
import pcd.ass01.simtrafficconc.CarAgentExtended;
import pcd.ass01.simtrafficconc.ConcurrentRoadsEnv;
import pcd.ass01.simtrafficview.ExecutionFlag;


/**
 * Traffic Simulation about 2 cars moving on a single road, with one semaphore
 */
public class ConcurrentTrafficSimulationSingleRoadWithTrafficLightTwoCars extends ConcurrentAbstractSimulation {

    private boolean guiEnabled = false;

    public ConcurrentTrafficSimulationSingleRoadWithTrafficLightTwoCars() {
        super(new ExecutionFlag(true));
    }

    public ConcurrentTrafficSimulationSingleRoadWithTrafficLightTwoCars(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
        super(threadFlag, seed);
        this.guiEnabled = guiEnabled;
    }

    public void setup() {

        this.setupTimings(0, 1);
        StepMonitor monitor = new StepMonitorImpl(2);

        RoadsEnv env = new ConcurrentRoadsEnv(monitor);
        this.setupEnvironment(env);

        Road r = env.createRoad(new P2d(0, 300), new P2d(1500, 300));

        TrafficLight tl = env.createTrafficLight(new P2d(740, 300), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
        r.addTrafficLight(tl, 740);

        CarAgent car1 = new CarAgentExtended("car-1", env, r, 0, 0.1, 0.3, 6, monitor);
        this.addAgent(car1);
        CarAgent car2 = new CarAgentExtended("car-2", env, r, 100, 0.1, 0.3, 5, monitor);
        this.addAgent(car2);

        if (guiEnabled)
            this.syncWithTime(25);
    }

}
