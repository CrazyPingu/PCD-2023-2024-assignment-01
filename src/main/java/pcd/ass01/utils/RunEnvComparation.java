package pcd.ass01.utils;

public class RunEnvComparation {

    public static void main(String[] args) {
//        String env1 = "seq-cross-roads.txt";
//        String env2 = "conc-cross-roads.txt";
        String env1 = "seq-single-road-massive.txt";
        String env2 = "conc-single-road-massive.txt";
        System.out.println(env1 + " and " + env2 + " are " + (RoadEnvAnalyzer.areEnvEquals(env1, env2) ? "equals." : "different."));
    }

}
