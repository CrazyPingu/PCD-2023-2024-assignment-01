package pcd.ass01.utils;

public class RunEnvComparation {

    public static void main(String[] args) {
        String env1 = "test-seq.txt";
        String env2 = "test-conc.txt";
        System.out.println(env1 + " and " + env2 + " are " + (RoadEnvAnalyzer.areEnvEquals(env1, env2) ? "equals." : "different."));
    }

}
