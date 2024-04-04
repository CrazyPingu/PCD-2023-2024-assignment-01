package pcd.ass01.simengineconc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of {@link StepMonitor} that act like a barrier.
 */
public class StepMonitorImpl implements StepMonitor {

    private final int numberOfAgents;
    private int count;
    private boolean continueFlagAgent, continueFlagEnv, continueFlagAll;
    private final Lock mutex;
    private final Condition agentsStep, envStep, agentsStepAll;

    public StepMonitorImpl(int numberOfAgents) {
        this.numberOfAgents = numberOfAgents + 1;
        count = 0;
        mutex = new ReentrantLock();
        agentsStep = mutex.newCondition();
        envStep = mutex.newCondition();
        agentsStepAll = mutex.newCondition();
        continueFlagAgent = continueFlagEnv = continueFlagAll = false;
    }

    @Override
    public void agentStep(Callback selectedAction) throws InterruptedException {
        try {
            mutex.lock();

            while (count == 0 || count >= numberOfAgents) {
                envStep.signal();
                agentsStep.await();
            }
            continueFlagAgent = false;
            selectedAction.call();
            count++;
            while (count < numberOfAgents || !continueFlagAgent) {
                if (count == numberOfAgents) {
                    continueFlagAll = true;
                }
                agentsStepAll.signal();
                agentsStep.await();
            }
            continueFlagEnv = true;
            envStep.signal();

        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void nextStep(Callback step) throws InterruptedException {
        try {
            mutex.lock();

            step.call();
            count++;
            while (count < numberOfAgents) {
                agentsStep.signalAll();
                envStep.await();
            }
            continueFlagEnv = false;
            while (!continueFlagEnv) {
                agentsStep.signalAll();
                envStep.await();
            }
            count = 0;

        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void waitAgentsStep(Callback action) throws InterruptedException {
        try {
            mutex.lock();

            continueFlagAll = false;
            while (!continueFlagAll) {
                agentsStepAll.await();
            }
            action.call();
            continueFlagAgent = true;
            envStep.signal();

        } finally {
            mutex.unlock();
        }
    }

}
