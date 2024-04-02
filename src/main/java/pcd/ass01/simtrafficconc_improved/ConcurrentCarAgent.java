package pcd.ass01.simtrafficconc_improved;

import gov.nasa.jpf.vm.Verify;
import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simengineseq_improved.AbstractEnvironment;
import pcd.ass01.simtrafficbase_improved.CarAgent;
import pcd.ass01.simtrafficbase_improved.CarPercept;
import pcd.ass01.simtrafficbase_improved.Road;
import pcd.ass01.simtrafficbase_improved.RoadsEnv;

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
            Verify.beginAtomic();
            if (selectedAction.isPresent()) {
                monitor.agentStep(() -> env.submitAction(selectedAction.get()));
            } else {
                monitor.agentStep(() -> {});
            }
            Verify.endAtomic();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
