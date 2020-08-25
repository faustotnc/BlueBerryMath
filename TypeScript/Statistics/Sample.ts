import { Stats } from './Stats';


/**
 * A collection of statistics functions that can
 * be executed on a sample dataset.
 */
export class Sample {
    public sample: number[];
    public min: number;
    public max: number;

    constructor(...sample_elements: number[]) {
        sample_elements = sample_elements.sort(); // Sorts the elements from smallest to largest

        // Property assignments
        this.sample = sample_elements;
        this.min = this.sample[0];
        this.max = this.sample[this.sample.length - 1];
    }

    /**
     * Calculates the sum of a sample.
     * @return The sum of the sample.
     */
    public sum() { return Stats.sum(...this.sample); }

    /**
     * Calculates the mean of a sample.
     * @return The mean of the sample.
     */
    public mean() { return Stats.mean(...this.sample); }

    /**
     * Calculates the median of a sample.
     * @return The median of a sample.
     */
    public median() { return Stats.median(...this.sample); }

    /**
     * Calculates the mode of a sample.
     * @return A list containing each element of the mode of the sample.
     */
    public mode() { return Stats.mode(...this.sample); }

    /**
     * Generates a frequency table in the form of a hash map
     * with the frequency of the elements in the sample.
     * @return A hash map containing the frequency of each element in the sample.
     */
    public frequency() { return Stats.frequency(...this.sample); }

    /**
     * Calculates the range of a sample.
     * @return The range of the the sample.
     */
    public range() { return Stats.range(...this.sample); }

    /**
     * Computes the variance of the sample.
     * As defined in Wikipedia, informally, variance is measure of
     * how far a set of numbers is spread out from their average value.
     * @return The variance of the sample.
     */
    public variance() { return Stats.sampleVariance(...this.sample); }

    /**
     * Calculates the (unbiased) standard deviation of a sample.
     * @return The standard deviation of a sample.
     */
    public stdDeviation() { return Stats.sampleStdDeviation(...this.sample); }

    /**
     * Calculates the quartiles of a sample.
     * @return A HashMap containing the quartiles (q1, q2, q3) of a sample.
     */
    public quartiles() { return Stats.quartiles(...this.sample); }

    /**
     * Calculates the inner quartile range of a sample.
     * @return The inner quartile range of a sample.
     */
    public IQR() { return Stats.IQR(...this.sample); }

    /**
     * Calculates the outliers of a sample.
     * @return A list containing the outliers of a sample.
     */
    public outliers() { return Stats.outliers(...this.sample); }


    /*
     * Calculates the probability of finding the element n in the sample.
     * @param n The element expected element to be found in the sample.
     * @return The probability of finding the element n in the sample.
     */
    public prob(...n: number[]): number {
        if (n.length > 1) {
            let totalProb = 0;
            // Adds up the probabilities of finding each of the elements
            // of the set individually.
            for (let element of n) totalProb += this.prob(element);
            return totalProb;
        } else {
            let nOfOccurrences = 0;
            for (let element of this.sample) if (element === n[0]) nOfOccurrences++;
            return nOfOccurrences / this.sample.length;
        }
    }

}
