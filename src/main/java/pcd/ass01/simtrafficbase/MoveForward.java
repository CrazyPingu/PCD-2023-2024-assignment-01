package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.Action;

/**
 * Car agent move forward action
 */
//public record MoveForward(double distance) implements Action {}

public final class MoveForward implements Action {
    public final double distance;

    public MoveForward(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

}