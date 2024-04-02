package pcd.ass01.simtrafficbase_improved;

import pcd.ass01.simengineseq_improved.Action;

/**
 * Car agent move forward action
 */
//public record MoveForward(double distance) implements Action {}

public final class MoveForward implements Action  {

    private final String agentId;
    private final double distance;

    public MoveForward(String agentId, double distance) {
        this.agentId = agentId;
        this.distance = distance;
    }

    public double distance() {
        return distance;
    }

    public String agentId() {
        return agentId;
    }

}