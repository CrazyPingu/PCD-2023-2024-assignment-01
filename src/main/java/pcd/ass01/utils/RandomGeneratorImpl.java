package pcd.ass01.utils;

import java.util.Random;

/**
 *
 * Implementation of {@link RandomGenerator} with fixed seed for randomisation.
 *
 */
public class RandomGeneratorImpl implements RandomGenerator {

    private final Random random;

    public RandomGeneratorImpl(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public Double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public Double nextDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
