package pcd.ass01.simtrafficconc;

import pcd.ass01.simengineconc.Callback;
import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.Action;
import pcd.ass01.simtrafficbase.CarAgent;
import pcd.ass01.simtrafficbase.CarPercept;
import pcd.ass01.simtrafficbase.Road;
import pcd.ass01.simtrafficbase.RoadsEnv;

import java.util.Optional;

/**
 * Base class modeling the skeleton of a concurrent agent modeling a car in the traffic environment
 */
public abstract class ConcurrentCarAgent extends CarAgent {

    protected final StepMonitor monitor;

    public ConcurrentCarAgent(String id, RoadsEnv env, Road road, double initialPos, double acc, double dec, double vmax, StepMonitor monitor) {
        super(id, env, road, initialPos, acc, dec, vmax);
        this.monitor = monitor;
    }

    /**
     * Basic behaviour of a car agent structured into a sense/decide/act structure
     */
    public void step(int dt) {

        /* sense */

        AbstractEnvironment env = this.getEnv();
        currentPercept = (CarPercept) env.getCurrentPercepts(getId());

        /* decide */

        selectedAction = Optional.empty();

        decide(dt);

        /* act */

        try {
            if (selectedAction.isPresent()) {
                Action action = selectedAction.get();
                monitor.agentStep(() -> {
                    env.doAction(getId(), action);
                });
            } else {
                monitor.agentStep(() -> {});
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
