

export class Calculus {

    /**
     * Computes the instantaneous rate of change at x
     * using the slope of the tangent line that passes through
     * two points of the function f with h=0.0000000001.
     * @param x The x-value at which the instantaneous rate of change will be calculated.
     * @param f The function to calculate the instantaneous rate of change.
     * @return The instantaneous rate of change at x for the function f.
     */
    public static nDeriv(x: number, f: (x: number) => number): number {
        const h = 0.0000000001;
        return (f(x + h) - f(x - h)) / (2 * h);
    }


    /**
     * Computes the area under a curve in the xy-plane
     * using the method of trapezoidal approximation.
     * @param l The lower limit of the integral
     * @param u The upper limit of the integral
     * @param f The function whose area will be approximated
     * @return The approximate area under the curve f from x=l to x=u
     */
    public static fnInt(l: number, u: number, f: (x: number) => number, n?: number): number {
        // Returns zero is the size of the interval is zero
        if ((u - l) === 0) return 0;

        // The number of trapezoids is dynamically
        // adjusted based on the size of the interval
        // with a minimum of 10,000 trapezoids
        const h = (!n) ? Math.round((10000 * (u - l)) + 10000) : n;
        // Total Computed Area
        let area = 0;
        // Change in x
        const deltaX = (u - l) / h;
        // Starting x-value
        let x = l;

        // Calculates the area of each trapezoid and
        // aggregates it to the total calculated area
        for (let i = 0; i <= h; i++) {
            area += ((f(x) + f(x + deltaX)) / 2) * deltaX;
            // The next x-value
            x += deltaX;
        }
        return area;
    }


    /**
     * Checks if a function is increasing on an interval [a, b].
     * @param a The lower bound of the interval.
     * @param b The upper bound of the interval.
     * @param f The function to be tested.
     * @return True if the function is monotone increasing on [a, b], false otherwise.
     */
    public static isIncreasing(a: number, b: number, f: (x: number) => number): boolean {
        const testN = b - a;
        let currentTest = 0;

        while (currentTest <= testN) {
            // If the derivative is less than zero for any x in
            // the interval [a, b], then the function would be decreasing,
            // so we return false.
            if (this.nDeriv(a + currentTest, f) < 0) return false;
            currentTest += 1.0 / 300; // Performs 300 tests per unit distance
        }

        return true;
    }


    /**
     * Checks if a function is decreasing on an interval [a, b].
     * @param a The lower bound of the interval.
     * @param b The upper bound of the interval.
     * @param f The function to be tested.
     * @return True if the function is monotone decreasing on [a, b], false otherwise.
     */
    public static isDecreasing(a: number, b: number, f: (x: number) => number): boolean {
        const testN = b - a;
        let currentTest = 0;

        while (currentTest <= testN) {
            // If the derivative is greater than zero for any x in
            // the interval [a, b], then the function would be increasing,
            // so we return false.
            if (this.nDeriv(a + currentTest, f) > 0) return false;
            currentTest += 1.0 / 300; // Performs 300 tests per unit distance
        }

        return true;
    }
}
