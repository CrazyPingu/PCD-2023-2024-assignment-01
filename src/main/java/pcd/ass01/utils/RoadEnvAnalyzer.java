package pcd.ass01.utils;

import pcd.ass01.simtrafficbase.RoadsEnv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class RoadEnvAnalyzer {

    private static final String SIM_RESULTS_PATH = "sim-results/";

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

    public static void saveEnvToFile(RoadsEnv env, String filename) {
        try (PrintWriter writer = new PrintWriter(SIM_RESULTS_PATH + filename, "UTF-8")) {
            writer.println("RoadsEnv:");

            writer.println("  Roads:");
            env.getRoads().forEach(road -> writer.println("    " + "road" + "\n"
                    + "      length: " + road.getLen() + "\n"
                    + "      from: " + road.getFrom() + "\n"
                    + "      to: " + road.getTo() + "\n"));

            writer.println("  Cars:");
            env.getAgentInfo().forEach(car -> writer.println("    " + car.getCar().getId() + "\n"
                    + "      speed: " + car.getCar().getCurrentSpeed() + "\n"
                    + "      position: " + car.getPos() + "\n"
                    + "      road from: " + car.getRoad().getFrom() + " to: " + car.getRoad().getTo() + "\n"));

            writer.println("  TrafficLights:");
            env.getTrafficLights().forEach(tl -> writer.println("    " + tl + "\n"
                    + "      position: " + tl.getPos() + "\n"
                    + "      state: " + tl.state() + "\n"));

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static boolean areEnvEquals(String env1, String env2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(SIM_RESULTS_PATH + env1));
             BufferedReader reader2 = new BufferedReader(new FileReader(SIM_RESULTS_PATH + env2))) {

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            while (line1 != null && line2 != null) {
                if (!line1.equals(line2)) {
                    System.out.println("The files are different." +
                            "File1: " + line1 +
                            "\nFile2: " + line2);
                    return false;
                }

                line1 = reader1.readLine();
                line2 = reader2.readLine();
            }

            if (line1 == null && line2 == null) {
                return true;
            } else {
                System.out.println("The files have different number of lines." +
                        "File1: " + (line1 == null ? "null" : line1) +
                        "File2: " + (line2 == null ? "null" : line2));
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
