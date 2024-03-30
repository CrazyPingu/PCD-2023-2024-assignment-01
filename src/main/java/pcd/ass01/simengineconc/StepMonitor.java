package pcd.ass01.simengineconc;

import pcd.ass01.simengineseq.Action;

import java.util.Optional;

/**
 *
 * Monitor for concurrent simulation.
 *
 */
public interface StepMonitor {

    /**
     *
     * Execute the step of a concurrent agent.
     *
     * @param selectedAction the action selected by the agent
     * @throws InterruptedException
     */
    void agentStep(Callback selectedAction) throws InterruptedException;

    /**
     *
     * Execute the step of the environment.
     *
     * @param step the action to be executed by the environment
     * @throws InterruptedException
     */
    void nextStep(Callback step) throws InterruptedException;

    /**
     *
     * Wait for the end of the current step by all agents and do an action.
     *
     * @param action the action to be executed after the end of the step by all agents
     * @throws InterruptedException
     */
    void waitAgentsStep(Callback action) throws InterruptedException;

    /**
     *
     * Notify all agents.
     *
     * @throws InterruptedException
     */
    void notifyAllAgents() throws InterruptedException;

}
