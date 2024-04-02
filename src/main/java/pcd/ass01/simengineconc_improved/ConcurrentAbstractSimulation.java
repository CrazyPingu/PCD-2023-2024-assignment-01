package pcd.ass01.simengineconc_improved;

import gov.nasa.jpf.vm.Verify;
import pcd.ass01.simengineconc.MonitoredStep;
import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineseq_improved.AbstractAgent;
import pcd.ass01.simengineseq_improved.AbstractSimulation;
import pcd.ass01.simtrafficconc_improved.CarAgentThread;
import pcd.ass01.simtrafficconc_improved.EnvThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for defining concrete concurrent simulations
 */
public abstract class ConcurrentAbstractSimulation extends AbstractSimulation {

    private int t;
    private int nSteps;
    private long timePerStep;

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
            agentsThreads.add(new CarAgentThread(a, dt, numSteps));
        }

        this.notifyReset(t, agents, env);

        timePerStep = 0;
        nSteps = 0;

        Thread envThread = new EnvThread(env, dt, numSteps);

        for (Thread thread : agentsThreads) {
            thread.start();
        }

        while (nSteps < numSteps) {

            currentWallTime = System.currentTimeMillis();

            // start env thread
            if (nSteps == 0) {
                envThread.start();
            }

            try {
                Verify.beginAtomic();
                monitor.waitAgentsStep(() -> {
                    t += dt;

                    env.processActions();

                    notifyNewStep(t, agents, env);

                    nSteps++;
                    timePerStep += System.currentTimeMillis() - currentWallTime;
                });
                Verify.endAtomic();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (toBeInSyncWithWallTime) {
                syncWithWallTime();
            }
        }

        endWallTime = System.currentTimeMillis();
        this.averageTimePerStep = timePerStep / numSteps;

        try {
            Thread.sleep(1000);
            monitor.notifyAllAgents();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
