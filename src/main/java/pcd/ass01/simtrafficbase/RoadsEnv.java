package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineconc.Callback;
import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.Action;
import pcd.ass01.simengineseq.Percept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoadsEnv extends AbstractEnvironment {

	protected static final int MIN_DIST_ALLOWED = 5;
	protected static final int CAR_DETECTION_RANGE = 30;
	protected static final int SEM_DETECTION_RANGE = 30;

	/* list of roads */
	private List<Road> roads;

	/* traffic lights */
	protected List<TrafficLight> trafficLights;

	/* cars situated in the environment */
	protected HashMap<String, CarAgentInfo> registeredCars;


	public RoadsEnv() {
		super("traffic-env");
		registeredCars = new HashMap<>();
		trafficLights = new ArrayList<>();
		roads = new ArrayList<>();
	}

	@Override
	public void init() {
		for (TrafficLight tl: trafficLights) {
			tl.init();
		}
	}

	@Override
	public void step(int dt) {
		for (TrafficLight tl: trafficLights) {
			tl.step(dt);
		}
	}

	public void registerNewCar(CarAgent car, Road road, double pos) {
		registeredCars.put(car.getId(), new CarAgentInfo(car, road, pos));
	}

	public Road createRoad(P2d p0, P2d p1) {
		Road r = new Road(p0, p1);
		this.roads.add(r);
		return r;
	}

	public TrafficLight createTrafficLight(P2d pos, TrafficLight.TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration) {
		TrafficLight tl = new TrafficLight(pos, initialState, greenDuration, yellowDuration, redDuration);
		this.trafficLights.add(tl);
		return tl;
	}

	@Override
	public Percept getCurrentPercepts(String agentId) {

		CarAgentInfo carInfo = registeredCars.get(agentId);
		double pos = carInfo.getPos();
		Road road = carInfo.getRoad();
		Optional<CarAgentInfo> nearestCar = getNearestCarInFront(road,pos, CAR_DETECTION_RANGE);
		Optional<TrafficLightInfo> nearestSem = getNearestSemaphoreInFront(road,pos, SEM_DETECTION_RANGE);

		return new CarPercept(pos, nearestCar, nearestSem);
	}

	protected Optional<CarAgentInfo> getNearestCarInFront(Road road, double carPos, double range){
		return
				registeredCars
				.entrySet()
				.stream()
				.map(el -> el.getValue())
				.filter((carInfo) -> carInfo.getRoad() == road)
				.filter((carInfo) -> {
					double dist = carInfo.getPos() - carPos;
					return dist > 0 && dist <= range;
				})
				.min((c1, c2) -> (int) Math.round(c1.getPos() - c2.getPos()));
	}

	private Optional<TrafficLightInfo> getNearestSemaphoreInFront(Road road, double carPos, double range){
		return
				road.getTrafficLights()
				.stream()
				.filter((TrafficLightInfo tl) -> tl.roadPos() > carPos)
				.min((c1, c2) -> (int) Math.round(c1.roadPos() - c2.roadPos()));
	}


	@Override
	public void processActions() {
		List<Callback> effectiveActions = new ArrayList<>();

		for (Action act : submittedActions) {
			if (act instanceof MoveForward) {
				MoveForward mv = (MoveForward) act;
				CarAgentInfo info = registeredCars.get(mv.agentId());
				Road road = info.getRoad();
				Optional<CarAgentInfo> nearestCar = getNearestCarInFront(road, info.getPos(), CAR_DETECTION_RANGE);

				if (nearestCar.isPresent()) {
					double dist = nearestCar.get().getPos() - info.getPos();
					if (dist > mv.distance() + MIN_DIST_ALLOWED) {
						effectiveActions.add(() -> info.updatePos(info.getPos() + mv.distance()));
					}
				} else {
					effectiveActions.add(() -> info.updatePos(info.getPos() + mv.distance()));
				}

				if (info.getPos() > road.getLen()) {
					effectiveActions.add(() -> info.updatePos(0));
				}
			}
		}

		// Execute the actions previously collected in order to avoid problems related with the order of the actions
		for (Callback action : effectiveActions) {
			action.call();
		}
	}


	public List<CarAgentInfo> getAgentInfo(){
		return this.registeredCars.entrySet().stream().map(el -> el.getValue()).collect(Collectors.toList());
	}

	public List<Road> getRoads(){
		return roads;
	}

	public List<TrafficLight> getTrafficLights(){
		return trafficLights;
	}
}
