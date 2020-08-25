

export class Other {

    /**
     * Computes the factorial of the number n.
     * @param n The number for which to calculate the factorial.
     * @return The factorial of the number n. (n!).
     */
    public static factorial(n: number): number {
        if (n === 0) return 1;
        return n * this.factorial(n - 1);
    }


    /**
     * Computes the binomial combination of the arguments n and k.
     * @param n The number of elements in the sample space.
     * @param k The number of elements to chose from the sample space n.
     * @return The number of ways, disregarding order, that k elements can be chosen from among n elements.
     */
    public static choose(n: number, k: number) {
        // The argument n must be greater than or equal to k.
        if (n < k) throw new Error("Integer 'n' must be greater than or equal to integer 'k'.");

        return this.factorial(n) / (this.factorial(k) * this.factorial(n - k));
    }


    /**
     * Adds the numbers defined by the sequence <code>s</code>.
     * @param start The start of the sequence.
     * @param end The end of the sequence (inclusive).
     * @param s The sequence pattern.
     * @return The sum of the sequence over the integers between <code>start</code> and <code>end</code>.
     */
    public static sum(start: number, end: number, s: (x: number) => number): number {
        let c = 0;
        for (let i = start; i <= end; i++) c += s(i);
        return c;
    }

}
