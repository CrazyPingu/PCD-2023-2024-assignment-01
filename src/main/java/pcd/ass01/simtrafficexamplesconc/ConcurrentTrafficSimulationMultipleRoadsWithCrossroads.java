package pcd.ass01.simtrafficexamplesconc;

import pcd.ass01.simengineconc.ConcurrentAbstractSimulation;
import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineconc.StepMonitorImpl;
import pcd.ass01.simtrafficbase.*;
import pcd.ass01.simtrafficconc.CarAgentExtended;
import pcd.ass01.simtrafficconc.ConcurrentRoadsEnv;
import pcd.ass01.simtrafficview.ExecutionFlag;

/**
 * This class represents a simulation of a traffic system with multiple roads (4) and crossroads (4).
 * The simulation is concurrent and it is based on the {@link pcd.ass01.simtrafficconc} package.
 */
public class ConcurrentTrafficSimulationMultipleRoadsWithCrossroads extends ConcurrentAbstractSimulation {

    private boolean guiEnabled = false;

    public ConcurrentTrafficSimulationMultipleRoadsWithCrossroads(ExecutionFlag threadFlag, boolean guiEnabled) {
        super(threadFlag);
        this.guiEnabled = guiEnabled;
    }

    public ConcurrentTrafficSimulationMultipleRoadsWithCrossroads(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
        super(threadFlag, seed);
        this.guiEnabled = guiEnabled;
    }

    public ConcurrentTrafficSimulationMultipleRoadsWithCrossroads() {
        this(new ExecutionFlag(true), false);
    }

    public void setup() {

        StepMonitor monitor = new StepMonitorImpl(8);

        this.setupTimings(0, 1);

        RoadsEnv env = new ConcurrentRoadsEnv(monitor);
        this.setupEnvironment(env);

//      Top left crossroad
        TrafficLight tl1 = env.createTrafficLight(new P2d(540, 200), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
        TrafficLight tl2 = env.createTrafficLight(new P2d(550, 190), TrafficLight.TrafficLightState.RED, 75, 25, 100);

//      Top right crossroad
        TrafficLight tl3 = env.createTrafficLight(new P2d(940, 200), TrafficLight.TrafficLightState.RED, 75, 25, 100);
        TrafficLight tl4 = env.createTrafficLight(new P2d(950, 190), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);

//      Bottom left crossroad
        TrafficLight tl5 = env.createTrafficLight(new P2d(540, 400), TrafficLight.TrafficLightState.RED, 75, 25, 100);
        TrafficLight tl6 = env.createTrafficLight(new P2d(550, 390), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);

//       Bottom right crossroad
        TrafficLight tl7 = env.createTrafficLight(new P2d(940, 400), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
        TrafficLight tl8 = env.createTrafficLight(new P2d(950, 390), TrafficLight.TrafficLightState.RED, 75, 25, 100);

//      Top Horizontal road
        Road r1 = env.createRoad(new P2d(0, 200), new P2d(1500, 200));
        r1.addTrafficLight(tl1, 540);
        r1.addTrafficLight(tl3, 940);

//      Bottom Horizontal road
        Road r2 = env.createRoad(new P2d(0, 400), new P2d(1500, 400));
        r2.addTrafficLight(tl5, 540);
        r2.addTrafficLight(tl7, 940);

//      First Vertical road
        Road r3 = env.createRoad(new P2d(550, 0), new P2d(550, 600));
        r3.addTrafficLight(tl2, 190);
        r3.addTrafficLight(tl6, 390);

//      Second Vertical road
        Road r4 = env.createRoad(new P2d(950, 0), new P2d(950, 600));
        r4.addTrafficLight(tl4, 190);
        r4.addTrafficLight(tl8, 390);

        Double acc = gen.nextDouble(0.05, 0.2);
        Double dec = gen.nextDouble(0.4, 0.7);

        CarAgent car1 = new CarAgentExtended("car-1", env, r1, 0, acc, dec, gen.nextDouble(5.5, 6.5), monitor);
        CarAgent car2 = new CarAgentExtended("car-2", env, r1, 100, acc, dec, gen.nextDouble(4.5, 5.5), monitor);
        CarAgent car7 = new CarAgentExtended("car-7", env, r2, 0, acc, gen.nextDouble(0.07, 0.15), gen.nextDouble(3.5, 4.5), monitor);
        CarAgent car8 = new CarAgentExtended("car-8", env, r2, 100, acc, gen.nextDouble(0.07, 0.15), gen.nextDouble(3.5, 4.5), monitor);
        CarAgent car3 = new CarAgentExtended("car-3", env, r3, 0, acc, gen.nextDouble(0.1, 0.25), gen.nextDouble(4.5, 5.5), monitor);
        CarAgent car4 = new CarAgentExtended("car-4", env, r3, 100, acc, gen.nextDouble(0.07, 0.15), gen.nextDouble(3.5, 4.5), monitor);
        CarAgent car5 =  new CarAgentExtended("car-5", env, r4, 0, acc, gen.nextDouble(0.07, 0.15), gen.nextDouble(3.5, 4.5), monitor);
        CarAgent car6 = new CarAgentExtended("car-6", env, r4, 100, acc, gen.nextDouble(0.07, 0.15), gen.nextDouble(3.5, 4.5), monitor);

        this.addAgent(car1);
        this.addAgent(car2);
        this.addAgent(car3);
        this.addAgent(car4);
        this.addAgent(car5);
        this.addAgent(car6);
        this.addAgent(car7);
        this.addAgent(car8);


        if (guiEnabled)
            this.syncWithTime(25);
    }

}
