package BlueBerryMath.Statistics;

import java.util.*;


public class Statistics {

    /**
     * Calculates the sum of a population
     * @param population The population from which to calculate the sum
     * @return The sum of the population
     */
    public static double sum(double... population) {
        double s = 0;
        for (double x: population) s += x;
        return s;
    }


    /**
     * Calculates the mean of a population
     * @param population The population from which to calculate the mean
     * @return The mean of the population
     */
    public static double mean(double... population) {
        return sum(population) / population.length;
    }


    /**
     * Calculates the median of a population
     * @param population The population from which to calculate the median
     * @return The median of the population
     */
    public static double median(double... population) {
        Arrays.sort(population); // Sorts the elements from smallest to largest

        if (population.length % 2 == 0) {
            // If the population is even, return the mean of the two central elements
            return Statistics.mean(population[(population.length / 2) - 1], population[population.length / 2]);
        } else {
            // If the population is odd, return the middle element
            return population[Math.round((float)(population.length / 2.0)) - 1];
        }
    }


    /**
     * Calculates the mode of a population
     * @param population The population from which to compute the mode
     * @return A list containing each element of the mode of the population
     */
    public static List<Double> mode(double... population) {
        List<Double> modeList = new ArrayList<>();

        // Generates and sorts an ArrayList containing the
        // frequencies of each elements in the population.
        HashMap<Double, Integer> frequency = Statistics.frequency(population);
        List<Integer> els = new ArrayList<>(frequency.values());
        Collections.sort(els);
        int highest = els.get(els.size() - 1);

        // Adds the elements with the highest
        // frequencies to the modeList
        frequency.forEach((k, v) -> {
            if (v == highest) modeList.add(k);
        });

        return modeList;
    }


    /**
     * Generates a frequency table in the form of a hash map with the
     * frequency of the elements in the population.
     * @param population The population from which to generate the frequency hash map.
     * @return A hash map containing the frequency of each element in the population.
     */
    public static HashMap<Double, Integer> frequency(double... population) {
        HashMap<Double, Integer> freq = new HashMap<>();

        for (double element: population) {
            if (!freq.containsKey(element)) {
                freq.put(element, 1);
            } else {
                freq.put(element, freq.get(element) + 1);
            }
        }

        return freq;
    }


    /**
     * Calculates the range of a population
     * @param population The population from which to generate the range
     * @return The range of the the population
     */
    public static double range(double... population) {
        Arrays.sort(population);
        return population[population.length - 1] - population[0];
    }


    /**
     * Computes the variance of the population.
     * As defined in Wikipedia, informally, variance is a measure of
     * how far a set of numbers is spread out from their average value.
     * @param population The population from which to calculate the variance.
     * @return The variance of the population
     */
    public static double popVariance(double... population) {
        double pMean = mean(population);
        double sum = 0;

        // Sum of the squared differences between the population
        // mean and each element of the population.
        for (double element: population) sum += Math.pow(pMean - element, 2);

        return sum / population.length;
    }


    /**
     * Computes the variance of the sample (unbiased variance).
     * As defined in Wikipedia, informally, variance is a measure of
     * how far a set of numbers is spread out from their average value.
     * @param sample The sample from which to calculate the variance.
     * @return The variance of the sample.
     */
    public static double sampleVariance(double... sample) {
        double pMean = mean(sample);
        double sum = 0;

        // Sum of the squared differences between the population
        // mean and each element of the population.
        for (double element: sample) sum += Math.pow(pMean - element, 2);

        return sum / (sample.length - 1);
    }


    /**
     * Calculates the standard deviation of a population.
     * @param population The population from which to calculate the standard deviation.
     * @return The standard deviation of a population.
     */
    public static double popStdDeviation(double... population) {
        return Math.sqrt( popVariance(population) );
    }


    /**
     * Calculates the (unbiased) standard deviation of a sample.
     * @param sample The sample from which to calculate the standard deviation.
     * @return The standard deviation of a sample.
     */
    public static double sampleStdDeviation(double... sample) {
        return Math.sqrt( popVariance(sample) );
    }


    /**
     * Calculates the quartiles of a population.
     * @param population The population from which to calculate the quartiles.
     * @return A HashMap containing the quartiles (q1, q2, q3) of a population.
     */
    public static HashMap<String, Double> quartiles(double... population) {
        Arrays.sort(population);

        HashMap<String, Double> qs = new HashMap<>();

        // If the population length is even, then both the upper
        // bound of the first half and the lower bound of the second
        // half of the population are equal to half of the population.
        int lowerTo, upperFrom;
        lowerTo = upperFrom = population.length / 2;

        // On the other hand, if the population length is odd, then the
        // upper bound of the lower half of the population will be equal
        // to half the length of the population minus one, and the lower
        // bound of the upper half of the population will be equal to half
        // the length of the population plus one.
        if (population.length % 2 != 0) {
            lowerTo = (int) Math.floor((float) population.length / 2);
            upperFrom = Math.round((float) population.length / 2);
        }

        // Define the quartiles
        qs.put("q1", median(Arrays.copyOfRange(population, 0, lowerTo)));
        qs.put("q2", median(population));
        qs.put("q3", median(Arrays.copyOfRange(population, upperFrom, population.length)));

        return qs;
    }


    /**
     * Calculates the inner quartile range of a population.
     * @param population The population from which to calculate the inner quartile range.
     * @return The inner quartile range of a population.
     */
    public static double IQR(double... population) {
        HashMap<String, Double> qs = quartiles(population);
        return qs.get("q3") - qs.get("q1");
    }


    /**
     * Calculates the outliers of a population.
     * @param population The population from which to calculate the outliers.
     * @return A list containing the outliers of a population.
     */
    public static List<Double> outliers(double... population) {
        List<Double> outList = new ArrayList<>();

        double iqr = IQR(population);
        HashMap<String, Double> qs = quartiles(population);

        double upperLimit = qs.get("q3") + (1.5 * iqr);
        double lowerLimit = qs.get("q1") - (1.5 * iqr);

        for (double element: population) {
            if (element < lowerLimit || element > upperLimit && !outList.contains(element)) {
                outList.add(element);
            }
        }

        return outList;
    }





    /**
     * A collection of statistics functions that can
     * be executed on a sample dataset.
     */
    public static class Sample {
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
         * @return The sum of the sample.
         */
        public double sum(double... population) { return Statistics.sum(this.sample); }

        /**
         * Calculates the mean of a sample.
         * @return The mean of the population.
         */
        public double mean() {
            return Statistics.mean(this.sample);
        }

        /**
         * Calculates the median of a sample.
         * @return The median of a population.
         */
        public double median() {
            return Statistics.median(this.sample);
        }

        /**
         * Calculates the mode of a sample.
         * @return A list containing each element of the mode of the sample.
         */
        public List<Double> mode() { return Statistics.mode(this.sample); }

        /**
         * Generates a frequency table in the form of a hash map
         * with the frequency of the elements in the sample.
         * @return A hash map containing the frequency of each element in the sample.
         */
        public HashMap<Double, Integer> frequency() { return Statistics.frequency(this.sample); }

        /**
         * Calculates the range of a sample.
         * @return The range of the the sample.
         */
        public double range() { return Statistics.range(this.sample); }

        /**
         * Computes the variance of the sample.
         * As defined in Wikipedia, informally, variance is measure of
         * how far a set of numbers is spread out from their average value.
         * @return The variance of the sample.
         */
        public double variance() { return Statistics.sampleVariance(this.sample); }

        /**
         * Calculates the (unbiased) standard deviation of a sample.
         * @return The standard deviation of a sample.
         */
        public double stdDeviation() { return Statistics.sampleStdDeviation(this.sample); }

        /**
         * Calculates the quartiles of a sample.
         * @return A HashMap containing the quartiles (q1, q2, q3) of a sample.
         */
        public HashMap<String, Double> quartiles() { return Statistics.quartiles(this.sample); }

        /**
         * Calculates the inner quartile range of a sample.
         * @return The inner quartile range of a sample.
         */
        public double IQR() { return Statistics.IQR(this.sample); }

        /**
         * Calculates the outliers of a sample.
         * @return A list containing the outliers of a sample.
         */
        public List<Double> outliers() { return Statistics.outliers(this.sample); }
    }
}
