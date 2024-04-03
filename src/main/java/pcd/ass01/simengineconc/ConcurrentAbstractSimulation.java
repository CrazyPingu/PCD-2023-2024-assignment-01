package pcd.ass01.simengineconc;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficconc.CarAgentThread;
import pcd.ass01.simtrafficconc.EnvThread;
import pcd.ass01.simtrafficview.ExecutionFlag;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for defining concrete concurrent simulations
 */
public abstract class ConcurrentAbstractSimulation extends AbstractSimulation {

    private int t;
    private int nSteps;
    private long timePerStep;

    protected ConcurrentAbstractSimulation(ExecutionFlag threadFlag) {
        super(threadFlag);
    }

    @Override
    public void run(int numSteps) {
        StepMonitor monitor = ((MonitoredStep) env).getMonitor();

        startWallTime = System.currentTimeMillis();

        /* initialize the env and the agents inside */
        t = t0;

        List<Thread> agentsThreads = new ArrayList<>();

        env.init();
        for (AbstractAgent a : agents) {
            a.init(env);
            agentsThreads.add(new CarAgentThread(a, dt, numSteps, threadFlag));
        }

        this.notifyReset(t, agents, env);

        timePerStep = 0;
        nSteps = 0;

        Thread envThread = new EnvThread(env, dt, numSteps, threadFlag);

        for (Thread thread : agentsThreads) {
            thread.start();
        }

        while (nSteps < numSteps && threadFlag.get()) {

            currentWallTime = System.currentTimeMillis();

            // start env thread
            if (nSteps == 0) {
                envThread.start();
            }

            try {
                monitor.waitAgentsStep(() -> {
                    t += dt;

                    env.processActions();

                    notifyNewStep(t, agents, env);

                    nSteps++;
                    timePerStep += System.currentTimeMillis() - currentWallTime;
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (toBeInSyncWithWallTime) {
                syncWithWallTime();
            }
        }

        endWallTime = System.currentTimeMillis();
        this.averageTimePerStep = timePerStep / numSteps;
    }

}
