package pcd.ass01.simtrafficexamples;

import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineconc.StepMonitorImpl;
import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficbase.*;
import pcd.ass01.simtrafficview.ExecutionFlag;

import java.util.ArrayList;
import java.util.List;

public class TrafficSimulationMultipleCrossroadsMassiveNumberOfCars extends AbstractSimulation {

    private boolean guiEnabled = false;

    public TrafficSimulationMultipleCrossroadsMassiveNumberOfCars(ExecutionFlag threadFlag, boolean guiEnabled) {
        super(threadFlag);
        this.guiEnabled = guiEnabled;
    }

    public TrafficSimulationMultipleCrossroadsMassiveNumberOfCars(ExecutionFlag threadFlag, boolean guiEnabled, int seed) {
        super(threadFlag, seed);
        this.guiEnabled = guiEnabled;
    }

    public TrafficSimulationMultipleCrossroadsMassiveNumberOfCars() {
        this(new ExecutionFlag(true), false);
    }

    public void setup() {

        List<Road> roads = new ArrayList<>();

        /* Set a correct number of cars according the number of roads for an equal subdivision */
        final int numCars = generateNumCars(5000, 5);

        this.setupTimings(0, 1);

        RoadsEnv env = new RoadsEnv();
        this.setupEnvironment(env);

        // Top left crossroad
        TrafficLight tl1 = env.createTrafficLight(new P2d(790, 400), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
        TrafficLight tl2 = env.createTrafficLight(new P2d(800, 390), TrafficLight.TrafficLightState.RED, 75, 25, 100);

        // Top center crossroad
        TrafficLight tl3 = env.createTrafficLight(new P2d(1590, 400), TrafficLight.TrafficLightState.RED, 75, 25, 100);
        TrafficLight tl4 = env.createTrafficLight(new P2d(1600, 390), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);

        // Bottom left crossroad
        TrafficLight tl5 = env.createTrafficLight(new P2d(790, 1000), TrafficLight.TrafficLightState.RED, 75, 25, 100);
        TrafficLight tl6 = env.createTrafficLight(new P2d(800, 990), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);

        // Bottom center crossroad
        TrafficLight tl7 = env.createTrafficLight(new P2d(1590, 1000), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
        TrafficLight tl8 = env.createTrafficLight(new P2d(1600, 990), TrafficLight.TrafficLightState.RED, 75, 25, 100);

        // Top right crossroad
        TrafficLight tl9 = env.createTrafficLight(new P2d(2390, 400), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
        TrafficLight tl10 = env.createTrafficLight(new P2d(2400, 390), TrafficLight.TrafficLightState.RED, 75, 25, 100);

        // Bottom right crossroad
        TrafficLight tl11 = env.createTrafficLight(new P2d(2390, 1000), TrafficLight.TrafficLightState.RED, 75, 25, 100);
        TrafficLight tl12 = env.createTrafficLight(new P2d(2400, 990), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);


        // Top Horizontal road
        Road r1 = env.createRoad(new P2d(0, 400), new P2d(3000, 400));
        r1.addTrafficLight(tl1, 790);
        r1.addTrafficLight(tl3, 1590);
        r1.addTrafficLight(tl9, 2390);
        roads.add(r1);

        // Bottom Horizontal road
        Road r2 = env.createRoad(new P2d(0, 1000), new P2d(3000, 1000));
        r2.addTrafficLight(tl5, 790);
        r2.addTrafficLight(tl7, 1590);
        r2.addTrafficLight(tl11, 2390);
        roads.add(r2);

        // First Vertical road
        Road r3 = env.createRoad(new P2d(800, 0), new P2d(800, 3000));
        r3.addTrafficLight(tl2, 390);
        r3.addTrafficLight(tl6, 990);
        roads.add(r3);

        // Second Vertical road
        Road r4 = env.createRoad(new P2d(1600, 0), new P2d(1600, 3000));
        r4.addTrafficLight(tl4, 390);
        r4.addTrafficLight(tl8, 990);
        roads.add(r4);

        // Third Vertical road
        Road r5 = env.createRoad(new P2d(2400, 0), new P2d(2400, 3000));
        r5.addTrafficLight(tl10, 390);
        r5.addTrafficLight(tl12, 990);
        roads.add(r5);

        /* Cars generation */
        for (int i = 0; i < numCars; i++) {
            double acc = (i * 0.03) + gen.nextDouble(0.5, 1.7);
            double dec = (i * 0.03) + gen.nextDouble(0.3, 0.7);
            Road road = roads.get(i % roads.size());
            CarAgent car = new CarAgentExtended("car-" + i, env, road, i * 10, acc, dec, gen.nextDouble(3.5, 4.5));
            this.addAgent(car);
        }


        if (guiEnabled)
            this.syncWithTime(25);
    }

    private int generateNumCars(int numCars, int numRoads) {
        return numCars % numRoads != 0 ? numCars + (numRoads - numCars % numRoads) : numCars;
    }

}
