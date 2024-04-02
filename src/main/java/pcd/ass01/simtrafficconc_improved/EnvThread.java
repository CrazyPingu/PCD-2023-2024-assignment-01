package pcd.ass01.simtrafficconc_improved;

import gov.nasa.jpf.vm.Verify;
import pcd.ass01.simengineseq_improved.AbstractEnvironment;

public class EnvThread extends Thread {

    private final AbstractEnvironment env;
    private final int dt;
    private final int numberOfSteps;
    private int steps = 0;

    public EnvThread(AbstractEnvironment env, int dt, int numberOfSteps) {
        this.env = env;
        this.dt = dt;
        this.numberOfSteps = numberOfSteps;
    }

    @Override
    public void run() {
        while (steps < numberOfSteps) {
            Verify.beginAtomic();
            env.step(dt);
            Verify.endAtomic();
            steps++;
        }
    }

}
