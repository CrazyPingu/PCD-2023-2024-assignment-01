package pcd.ass01.simengineseq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Base class for defining concrete simulations
 *  
 */
public abstract class AbstractSimulation {

	/* environment of the simulation */
	protected AbstractEnvironment env;
	
	/* list of the agents */
	protected List<AbstractAgent> agents;
	
	/* simulation listeners */
	protected List<SimulationListener> listeners;

	/* logical time step */
	protected int dt;
	
	/* initial logical time */
	protected int t0;

	/* in the case of sync with wall-time */
	protected boolean toBeInSyncWithWallTime;
	protected int nStepsPerSec;
	
	/* for time statistics*/
	protected long currentWallTime;
	protected long startWallTime;
	protected long endWallTime;
	protected long averageTimePerStep;
	protected boolean threadFlag;


	protected AbstractSimulation(boolean threadFlag) {
		this.threadFlag = threadFlag;
		agents = new ArrayList<AbstractAgent>();
		listeners = new ArrayList<SimulationListener>();
		toBeInSyncWithWallTime = false;
	}
	
	/**
	 * 
	 * Method used to configure the simulation, specifying env and agents
	 * 
	 */
	protected abstract void setup();
	
	/**
	 * Method running the simulation for a number of steps,
	 * using a sequential approach
	 * 
	 * @param numSteps
	 */
	public void run(int numSteps) {		

		startWallTime = System.currentTimeMillis();

		/* initialize the env and the agents inside */
		int t = t0;

		env.init();
		for (AbstractAgent a: agents) {
			a.init(env);
		}

		this.notifyReset(t, agents, env);
		
		long timePerStep = 0;
		int nSteps = 0;
		
		while (nSteps < numSteps && threadFlag) {
			System.out.println("Thread flag: " + threadFlag);


			currentWallTime = System.currentTimeMillis();
		
			/* make a step */
			
			env.step(dt);
			
			/* clean the submitted actions */
			
			env.cleanActions();
			
			/* ask each agent to make a step */
			
			for (AbstractAgent agent: agents) {
				agent.step(dt);
			}
			t += dt;
						
			/* process actions submitted to the environment */
			
			env.processActions();
			
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

	public AbstractEnvironment getEnvironment() {
		return env;
	}
	
	public long getSimulationDuration() {
		return endWallTime - startWallTime;
	}
	
	public long getAverageTimePerCycle() {
		return averageTimePerStep;
	}
	
	/* methods for configuring the simulation */
	
	protected void setupTimings(int t0, int dt) {
		this.dt = dt;
		this.t0 = t0;
	}
	
	protected void syncWithTime(int nCyclesPerSec) {
		this.toBeInSyncWithWallTime = true;
		this.nStepsPerSec = nCyclesPerSec;
	}
		
	protected void setupEnvironment(AbstractEnvironment env) {
		this.env = env;
	}

	protected void addAgent(AbstractAgent agent) {
		agents.add(agent);
	}
	
	/* methods for listeners */
	
	public void addSimulationListener(SimulationListener l) {
		this.listeners.add(l);
	}

	protected void notifyReset(int t0, List<AbstractAgent> agents, AbstractEnvironment env) {
		for (SimulationListener l: listeners) {
			l.notifyInit(t0, agents, env);
		}
	}

	protected void notifyNewStep(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
		for (SimulationListener l: listeners) {
			l.notifyStepDone(t, agents, env);
		}
	}

	/* method to sync with wall time at a specified step rate */
	
	protected void syncWithWallTime() {
		try {
			long newWallTime = System.currentTimeMillis();
			long delay = 1000 / this.nStepsPerSec;
			long wallTimeDT = newWallTime - currentWallTime;
			if (wallTimeDT < delay) {
				Thread.sleep(delay - wallTimeDT);
			}
		} catch (Exception ex) {}		
	}
}
