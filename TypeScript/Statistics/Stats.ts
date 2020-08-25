

export class Stats {

    /**
     * Calculates the sum of a population
     * @param population The population from which to calculate the sum
     * @return The sum of the population
     */
    public static sum(...population: number[]): number {
        let s = 0;
        for (let x of population) s += x;
        return s;
    }


    /**
     * Calculates the mean of a population
     * @param population The population from which to calculate the mean
     * @return The mean of the population
     */
    public static mean(...population: number[]): number {
        return this.sum(...population) / population.length;
    }


    /**
     * Calculates the median of a population
     * @param population The population from which to calculate the median
     * @return The median of the population
     */
    public static median(...population: number[]): number {
        population = population.sort(); // Sorts the elements from smallest to largest

        if (population.length % 2 === 0) {
            // If the population is even, return the mean of the two central elements
            return Stats.mean(population[(population.length / 2) - 1], population[population.length / 2]);
        } else {
            // If the population is odd, return the middle element
            return population[(population.length + 1) / 2];
        }
    }


    /**
     * Calculates the mode of a population
     * @param population The population from which to compute the mode
     * @return A list containing each element of the mode of the population
     */
    public static mode(...population: number[]): number[] {
        const modeList: number[] = [];

        // Generates and sorts an ArrayList containing the
        // frequencies of each elements in the population.
        const frequency = Stats.frequency(...population);
        let els = Object.values(frequency).sort();
        let highest = els[els.length - 1];

        // Adds the elements with the highest
        // frequencies to the modeList
        for (let el in frequency) {
            let val: number = frequency[el];
            if (val === highest) modeList.push(Number.parseFloat(el))
        }

        return modeList;
    }


    /**
     * Generates a frequency table in the form of a hash map with the
     * frequency of the elements in the population.
     * @param population The population from which to generate the frequency hash map.
     * @return A hash map containing the frequency of each element in the population.
     */
    public static frequency(...population: number[]): { [key: number]: number } {
        let freq: { [key: number]: number } = {};

        for (let element of population) {
            if (!(element in freq)) {
                freq[element] = 1;
            } else {
                freq[element] = freq[element] + 1;
            }
        }

        return freq;
    }


    /**
     * Calculates the range of a population
     * @param population The population from which to generate the range
     * @return The range of the the population
     */
    public static range(...population: number[]): number {
        population = population.sort();
        return population[population.length - 1] - population[0];
    }


    /**
     * Computes the variance of the population.
     * As defined in Wikipedia, informally, variance is a measure of
     * how far a set of numbers is spread out from their average value.
     * @param population The population from which to calculate the variance.
     * @return The variance of the population
     */
    public static popVariance(...population: number[]): number {
        let pMean = this.mean(...population);
        let sum = 0;

        // Sum of the squared differences between the population
        // mean and each element of the population.
        for (let element of population) sum += Math.pow(pMean - element, 2);

        return sum / population.length;
    }


    /**
     * Computes the variance of the sample (unbiased variance).
     * As defined in Wikipedia, informally, variance is a measure of
     * how far a set of numbers is spread out from their average value.
     * @param sample The sample from which to calculate the variance.
     * @return The variance of the sample.
     */
    public static sampleVariance(...sample: number[]): number {
        let pMean = this.mean(...sample);
        let sum = 0;

        // Sum of the squared differences between the population
        // mean and each element of the population.
        for (let element of sample) sum += Math.pow(pMean - element, 2);

        return sum / (sample.length - 1);
    }


    /**
     * Calculates the standard deviation of a population.
     * @param population The population from which to calculate the standard deviation.
     * @return The standard deviation of a population.
     */
    public static popStdDeviation(...population: number[]): number {
        return Math.sqrt(this.popVariance(...population));
    }


    /**
     * Calculates the (unbiased) standard deviation of a sample.
     * @param sample The sample from which to calculate the standard deviation.
     * @return The standard deviation of a sample.
     */
    public static sampleStdDeviation(...sample: number[]): number {
        return Math.sqrt(this.sampleVariance(...sample));
    }


    /**
     * Calculates the quartiles of a population.
     * @param population The population from which to calculate the quartiles.
     * @return A HashMap containing the quartiles (q1, q2, q3) of a population.
     */
    public static quartiles(...population: number[]): { q1: number, q2: number, q3: number } {
        population = population.sort();

        let qs = { q1: 0, q2: 0, q3: 0 };

        // If the population length is even, then both the upper
        // bound of the first half and the lower bound of the second
        // half of the population are equal to half of the population.
        let lowerTo: number, upperFrom: number;
        lowerTo = upperFrom = population.length / 2;

        // On the other hand, if the population length is odd, then the
        // upper bound of the lower half of the population will be equal
        // to half the length of the population minus one, and the lower
        // bound of the upper half of the population will be equal to half
        // the length of the population plus one.
        if (population.length % 2 !== 0) {
            lowerTo = Math.floor(population.length / 2);
            upperFrom = Math.round(population.length / 2);
        }

        // Define the quartiles
        qs.q1 = this.median(...population.slice(0, lowerTo));
        qs.q2 = this.median(...population);
        qs.q3 = this.median(...population.slice(upperFrom, population.length));

        return qs;
    }


    /**
     * Calculates the inner quartile range of a population.
     * @param population The population from which to calculate the inner quartile range.
     * @return The inner quartile range of a population.
     */
    public static IQR(...population: number[]): number {
        let qs = this.quartiles(...population);
        return qs.q3 - qs.q1;
    }


    /**
     * Calculates the outliers of a population.
     * @param population The population from which to calculate the outliers.
     * @return A list containing the outliers of a population.
     */
    public static outliers(...population: number[]): number[] {
        let outList: number[] = [];

        let iqr = this.IQR(...population);
        let qs = this.quartiles(...population);

        let upperLimit = qs.q3 + (1.5 * iqr);
        let lowerLimit = qs.q1 - (1.5 * iqr);

        for (let element of population) {
            if ((element < lowerLimit || element > upperLimit) && !outList.includes(element)) {
                outList.push(element);
            }
        }

        return outList;
    }
}
