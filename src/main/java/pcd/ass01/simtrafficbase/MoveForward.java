package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.Action;

/**
 * Car agent move forward action
 */
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