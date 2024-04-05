package pcd.ass01.simengineconc;

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
     * Signal all agents to proceed.
     *
     */
    void signalAllAgents();
}
