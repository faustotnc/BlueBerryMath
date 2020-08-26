package Java.Statistics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A collection of statistics functions that can be executed on a sample
 * dataset.
 */
public class Sample {
    public double[] sample;
    public double min;
    public double max;

    public Sample(double... sample_elements) {
        Arrays.sort(sample_elements); // Sorts the elements from smallest to largest

        // Property assignments
        this.sample = sample_elements;
        this.min = this.sample[0];
        this.max = this.sample[this.sample.length - 1];
    }

    /**
     * Calculates the sum of a sample.
     * 
     * @return The sum of the sample.
     */
    public double sum() {
        return Stats.sum(this.sample);
    }

    /**
     * Calculates the mean of a sample.
     * 
     * @return The mean of the sample.
     */
    public double mean() {
        return Stats.mean(this.sample);
    }

    /**
     * Calculates the median of a sample.
     * 
     * @return The median of a sample.
     */
    public double median() {
        return Stats.median(this.sample);
    }

    /**
     * Calculates the mode of a sample.
     * 
     * @return A list containing each element of the mode of the sample.
     */
    public List<Double> mode() {
        return Stats.mode(this.sample);
    }

    /**
     * Generates a frequency table in the form of a hash map with the frequency of
     * the elements in the sample.
     * 
     * @return A hash map containing the frequency of each element in the sample.
     */
    public HashMap<Double, Integer> frequency() {
        return Stats.frequency(this.sample);
    }

    /**
     * Calculates the range of a sample.
     * 
     * @return The range of the the sample.
     */
    public double range() {
        return Stats.range(this.sample);
    }

    /**
     * Computes the variance of the sample. As defined in Wikipedia, informally,
     * variance is measure of how far a set of numbers is spread out from their
     * average value.
     * 
     * @return The variance of the sample.
     */
    public double variance() {
        return Stats.sampleVariance(this.sample);
    }

    /**
     * Calculates the (unbiased) standard deviation of a sample.
     * 
     * @return The standard deviation of a sample.
     */
    public double stdDeviation() {
        return Stats.sampleStdDeviation(this.sample);
    }

    /**
     * Calculates the quartiles of a sample.
     * 
     * @return A HashMap containing the quartiles (q1, q2, q3) of a sample.
     */
    public HashMap<String, Double> quartiles() {
        return Stats.quartiles(this.sample);
    }

    /**
     * Calculates the inner quartile range of a sample.
     * 
     * @return The inner quartile range of a sample.
     */
    public double IQR() {
        return Stats.IQR(this.sample);
    }

    /**
     * Calculates the outliers of a sample.
     * 
     * @return A list containing the outliers of a sample.
     */
    public List<Double> outliers() {
        return Stats.outliers(this.sample);
    }

    /*
     * Calculates the probability of finding the element n in the sample.
     * 
     * @param n The element expected element to be found in the sample.
     * 
     * @return The probability of finding the element n in the sample.
     */
    public double prob(double n) {
        int nOfOccurrences = 0;
        for (double element : this.sample)
            if (element == n)
                nOfOccurrences++;
        return (double) nOfOccurrences / this.sample.length;
    }

    /**
     * Calculates the probability of finding any element of the set eSet in the
     * sample.
     * 
     * @param eSet The set of expected values.
     * @return The probability of finding any element of the set eSet in the sample.
     */
    public double prob(double... eSet) {
        double totalProb = 0;

        // Adds up the probabilities of finding each of the elements
        // of the set individually.
        for (double element : eSet)
            totalProb += prob(element);

        return totalProb;
    }

}
