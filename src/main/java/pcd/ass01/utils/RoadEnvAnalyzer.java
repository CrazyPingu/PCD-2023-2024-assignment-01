package pcd.ass01.utils;

import pcd.ass01.simtrafficbase.RoadsEnv;

public class RoadEnvAnalyzer {
    public static void logEnv(RoadsEnv env) {
        System.out.println("RoadsEnv:");

        System.out.println("  Roads:");
        env.getRoads().forEach(road -> System.out.println("    " + "road" + "\n"
                         + "      lenght: " + road.getLen() + "\n"
                         + "      from: " + road.getFrom() + "\n"
                         + "      to: " + road.getTo() + "\n"));

        System.out.println("  Cars:");
        env.getAgentInfo().forEach(car -> System.out.println("    " + car.getCar().getId() + "\n"
                         + "      speed: " + car.getCar().getCurrentSpeed() + "\n"
                         + "      position: " + car.getPos() + "\n"
                         + "      road from: " + car.getRoad().getFrom() + " to: " + car.getRoad().getTo() + "\n"));

        System.out.println("  TrafficLights:");
        env.getTrafficLights().forEach(tl -> System.out.println("    " + tl + "\n"
                         + "      position: " + tl.getPos() + "\n"
                         + "      state: " + tl.state() + "\n"));
    }


}
