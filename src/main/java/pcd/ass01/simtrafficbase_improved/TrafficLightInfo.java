package pcd.ass01.simtrafficbase_improved;

//public record TrafficLightInfo(TrafficLight sem, Road road, double roadPos) {}

import pcd.ass01.simtrafficbase_improved.Road;
import pcd.ass01.simtrafficbase_improved.TrafficLight;

public class TrafficLightInfo {
    private final TrafficLight sem;
    private final Road road;
    private final double roadPos;

    public TrafficLightInfo(TrafficLight sem, Road road, double roadPos) {
        this.sem = sem;
        this.road = road;
        this.roadPos = roadPos;
    }

    public TrafficLight sem() {
        return sem;
    }

    public Road getRoad() {
        return road;
    }

    public double roadPos() {
        return roadPos;
    }

}