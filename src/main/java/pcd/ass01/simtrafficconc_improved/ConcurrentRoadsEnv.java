package pcd.ass01.simtrafficconc_improved;

import pcd.ass01.simengineconc.MonitoredStep;
import pcd.ass01.simengineconc.StepMonitor;
import pcd.ass01.simtrafficbase_improved.RoadsEnv;
import pcd.ass01.simtrafficbase_improved.TrafficLight;

/**
 *
 * Environment for the concurrent simulation of a road network with traffic lights.
 *
 */
public class ConcurrentRoadsEnv extends RoadsEnv implements MonitoredStep {

	protected StepMonitor monitor;

	public ConcurrentRoadsEnv(StepMonitor monitor) {
		super();
		this.monitor = monitor;
	}
	
	@Override
	public void step(int dt) {
        try {
            monitor.nextStep(() -> {
				for (TrafficLight tl : trafficLights) {
					tl.step(dt);
				}
			});
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	@Override
	public StepMonitor getMonitor() {
		return monitor;
	}
}
