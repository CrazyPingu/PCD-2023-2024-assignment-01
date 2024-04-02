package pcd.ass01.utils;

/**
 *
 * Interface for random number generators.
 *
 */
public interface RandomGenerator {

    /**
     *
     * Generate a random double.
     *
     * @return the random double
     */
    Double nextDouble();

    /**
     *
     * Generate a random double in the range [min, max).
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the random double in the range [min, max)
     */
    Double nextDouble(double min, double max);

}
