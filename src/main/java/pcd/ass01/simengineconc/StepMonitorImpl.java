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
    private final Condition agentsStep, envStep, agentsStepAll;

    public StepMonitorImpl(int numberOfAgents) {
        this.numberOfAgents = numberOfAgents;
        this.count = this.numberOfAgents;
        this.mutex = new ReentrantLock();
        this.agentsStep = mutex.newCondition();
        this.envStep = mutex.newCondition();
        this.agentsStepAll = mutex.newCondition();
    }

    @Override
    public void agentStep() throws InterruptedException {
        try {
            this.mutex.lock();
            this.count++;
            System.out.println("Agent count: " + this.count);
            while (this.count < this.numberOfAgents) {
                this.agentsStep.await();
            }
            System.out.println("Agent step");
            this.agentsStepAll.signal();
            this.envStep.signal();
        } finally {
            this.mutex.unlock();
        }
    }

    @Override
    public void nextStep() throws InterruptedException {
        try {
            this.mutex.lock();
            while (this.count < this.numberOfAgents) {
                this.envStep.await();
            }
            this.count = 0;
            this.agentsStep.signalAll();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void waitAgentsStep() throws InterruptedException {
        try {
            this.mutex.lock();
//            while (this.count < this.numberOfAgents) {
            this.agentsStepAll.await();
//            }
        } finally {
            this.mutex.unlock();
        }
    }

//    @Override
//    public boolean isEmpty() throws InterruptedException {
//        try {
//            this.mutex.lock();
//            return this.count == 0;
//        } finally {
//            this.mutex.unlock();
//        }
//    }

    @Override
    public boolean isStepDone() throws InterruptedException {
        try {
            this.mutex.lock();
            return count == this.numberOfAgents;
        } finally {
            this.mutex.unlock();
        }
    }
}
