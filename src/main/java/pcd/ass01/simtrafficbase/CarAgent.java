package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.Action;

import java.util.Optional;

/**
 * Base class modeling the skeleton of an agent modeling a car in the traffic environment
 */
public abstract class CarAgent extends AbstractAgent {

    /* car model */
    protected double maxSpeed;
    protected double currentSpeed;
    protected double acceleration;
    protected double deceleration;

    /* percept and action retrieved and submitted at each step */
    protected CarPercept currentPercept;
    protected Optional<Action> selectedAction;


    public CarAgent(String id, RoadsEnv env, Road road,
                    double initialPos,
                    double acc,
                    double dec,
                    double vmax) {
        super(id);
        this.acceleration = acc;
        this.deceleration = dec;
        this.maxSpeed = vmax;
        env.registerNewCar(this, road, initialPos);
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

        if (selectedAction.isPresent()) {
            env.submitAction(selectedAction.get());
        }
    }

    /**
     * Base method to define the behaviour strategy of the car
     *
     * @param dt
     */
    protected abstract void decide(int dt);

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    protected void log(String msg) {
        System.out.println("[CAR " + this.getId() + "] " + msg);
    }


}
