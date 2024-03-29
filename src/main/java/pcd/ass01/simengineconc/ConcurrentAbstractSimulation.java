package pcd.ass01.simengineconc;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficconc.ThreadCarAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for defining concrete concurrent simulations
 */
public abstract class ConcurrentAbstractSimulation extends AbstractSimulation {

    @Override
    public void run(int numSteps) {
        StepMonitor monitor = ((MonitoredStep) env).getMonitor();

        startWallTime = System.currentTimeMillis();

        /* initialize the env and the agents inside */
        int t = t0;

        List<Thread> agentsThreads = new ArrayList<>();

        env.init();
        for (AbstractAgent a : agents) {
            a.init(env);
            agentsThreads.add(new ThreadCarAgent(a, dt, numSteps));
        }

        this.notifyReset(t, agents, env);

        long timePerStep = 0;
        int nSteps = 0;

        while (nSteps < numSteps) {

            currentWallTime = System.currentTimeMillis();

            /* make a step */

            env.step(dt);

            // start agents threads
            if (nSteps == 0) {
                for (Thread thread : agentsThreads) {
                    thread.start();
                }
            }
            try {
                monitor.waitAgentsStep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            t += dt;

            notifyNewStep(t, agents, env);

            nSteps++;
            timePerStep += System.currentTimeMillis() - currentWallTime;

            if (toBeInSyncWithWallTime) {
                syncWithWallTime();
            }
        }

        endWallTime = System.currentTimeMillis();
        this.averageTimePerStep = timePerStep / numSteps;
    }

}
