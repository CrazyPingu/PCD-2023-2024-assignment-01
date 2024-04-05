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
    private final Lock mutex;
    private final Condition agentsStep, envStep;

    public StepMonitorImpl(int numberOfAgents) {
        this.numberOfAgents = numberOfAgents + 1;
        count = this.numberOfAgents;
        mutex = new ReentrantLock();
        agentsStep = mutex.newCondition();
        envStep = mutex.newCondition();
    }

    @Override
    public void agentStep(Callback selectedAction) throws InterruptedException {
        try {
            mutex.lock();

            while (count >= numberOfAgents) {
                envStep.signal();
                agentsStep.await();
            }
            selectedAction.call();
            count--;
            if (count <= 0) {
                envStep.signal();
            }
            agentsStep.await();

        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void nextStep(Callback step) throws InterruptedException {
        try {
            mutex.lock();

            step.call();
            count--;
            while (count > 0) {
                agentsStep.signalAll();
                envStep.await();
            }
            count = this.numberOfAgents;

        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void signalAllAgents() {
        try {
            mutex.lock();

            agentsStep.signalAll();

        } finally {
            mutex.unlock();
        }
    }

}
