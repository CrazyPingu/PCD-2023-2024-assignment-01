package pcd.ass01.simengineconc;

/**
 *
 * Monitor for concurrent simulation.
 *
 */
public interface StepMonitor {

    /**
     *
     * Notify the end of the current step by an agent.
     *
     * @throws InterruptedException
     */
    void agentStep() throws InterruptedException;

    /**
     *
     * Notify the end of the current step by all agents.
     *
     * @throws InterruptedException
     */
    void nextStep() throws InterruptedException;

    /**
     *
     * Wait for the end of the current step by all agents.
     *
     * @throws InterruptedException
     */
    void waitAgentsStep() throws InterruptedException;

//    /**
//     *
//     * Check if the buffer is empty.
//     *
//     * @throws InterruptedException
//     */
//    boolean isEmpty() throws InterruptedException;

    /**
     *
     * Check if the step has been completed by all the agents.
     *
     * @throws InterruptedException
     */
    boolean isStepDone() throws InterruptedException;

}
