package pcd.ass01.simengineconc;

import gov.nasa.jpf.vm.Verify;
import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficconc.CarAgentThread;
import pcd.ass01.simtrafficconc.SimulationThread;
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

    protected ConcurrentAbstractSimulation(ExecutionFlag threadFlag, int seed) {
        super(threadFlag, seed);
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

        for (Thread thread : agentsThreads) {
            thread.start();
        }

        Thread simulationThread = createSimulationThread(numSteps, monitor);

        /* start the effective simulation */
        simulationThread.start();

    }

    private Thread createSimulationThread(int numSteps, StepMonitor monitor) {
        return new SimulationThread(() -> {
            while (nSteps < numSteps && threadFlag.get()) {

                currentWallTime = System.currentTimeMillis();

                env.step(dt);

                t += dt;

                env.processActions();

                notifyNewStep(t, agents, env);

                nSteps++;
                timePerStep += System.currentTimeMillis() - currentWallTime;

                if (toBeInSyncWithWallTime) {
                    syncWithWallTime();
                }
            }
            monitor.signalAllAgents();

            endWallTime = System.currentTimeMillis();
            this.averageTimePerStep = timePerStep / numSteps;
        });
    }

}
