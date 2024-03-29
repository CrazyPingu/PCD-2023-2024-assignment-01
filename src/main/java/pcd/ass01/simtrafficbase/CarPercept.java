package pcd.ass01.simtrafficbase;

import java.util.Optional;

import pcd.ass01.simengineseq.Percept;

/**
 * Percept for Car Agents
 * <p>
 * - position on the road
 * - nearest car, if present (distance)
 * - nearest semaphore, if presente (distance)
 */
//public record CarPercept(double roadPos, Optional<CarAgentInfo> nearestCarInFront, Optional<TrafficLightInfo> nearestSem) implements Percept { }

public class CarPercept implements Percept {
    public final double roadPos;
    public final Optional<CarAgentInfo> nearestCarInFront;
    public final Optional<TrafficLightInfo> nearestSem;

    public CarPercept(double roadPos, Optional<CarAgentInfo> nearestCarInFront, Optional<TrafficLightInfo> nearestSem) {
        this.roadPos = roadPos;
        this.nearestCarInFront = nearestCarInFront;
        this.nearestSem = nearestSem;
    }

    public double roadPos() {
        return roadPos;
    }

    public Optional<CarAgentInfo> nearestCarInFront() {
        return nearestCarInFront;
    }

    public Optional<TrafficLightInfo> nearestSem() {
        return nearestSem;
    }

}