package pcd.ass01.simengineconc_improved;

import pcd.ass01.simengineconc.Callback;
import pcd.ass01.simengineconc.StepMonitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of {@link StepMonitor} that act like a barrier.
 */
public class StepMonitorImpl implements StepMonitor {

    private final int numberOfAgents;
    private int count;
    private boolean continueFlagAgent, continueFlagEnv;
    private final Lock mutex;
    private final Condition agentsStep, envStep, agentsStepAll;

    public StepMonitorImpl(int numberOfAgents) {
        this.numberOfAgents = numberOfAgents + 1;
        count = 0;
        mutex = new ReentrantLock();
        agentsStep = mutex.newCondition();
        envStep = mutex.newCondition();
        agentsStepAll = mutex.newCondition();
        continueFlagAgent = continueFlagEnv =false;
    }

    @Override
    public void agentStep(Callback selectedAction) throws InterruptedException {
        try {
            mutex.lock();

            continueFlagEnv = true;
            while (count == 0 || count >= numberOfAgents) {
                envStep.signal();
                agentsStep.await();
            }
            continueFlagAgent = false;
            selectedAction.call();
            count++;
            while (count < numberOfAgents || !continueFlagAgent) {
                agentsStepAll.signal();
                agentsStep.await();
            }

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
            while (count != 0 && !continueFlagEnv) {
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

            while (count < numberOfAgents || continueFlagAgent) {
                agentsStepAll.await();
            }
            action.call();
            continueFlagAgent = true;
            envStep.signal();

        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void notifyAllAgents() {
        try {
            mutex.lock();

            continueFlagEnv = true;
            continueFlagAgent = true;
            agentsStep.signalAll();
            envStep.signalAll();
            agentsStepAll.signalAll();

        } finally {
            mutex.unlock();
        }
    }


}
