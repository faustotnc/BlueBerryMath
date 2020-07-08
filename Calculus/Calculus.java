package BlueBerryMath.Calculus;

import java.util.function.Function;

public class Calculus {

    /**
     * Computes the instantaneous rate of change at x
     * using the slope of the tangent line that passes through
     * two points of the function f with h=0.0000000001.
     * @param x The x-value at which the instantaneous rate of change will be calculates.
     * @param f The function to calculate the instantaneous rate of change.
     * @return The instantaneous rate of change at x for the function f.
     */
    public static double nDeriv(double x, Function<Double, Double> f) {
        double h = 0.0000000001;
        return (f.apply(x + h) - f.apply(x - h)) / (2 * h);
    }


    /**
     * Computes the area under a curve in the xy-plane
     * using the method of trapezoidal approximation.
     * @param l The lower limit of the integral
     * @param u The upper limit of the integral
     * @param f The function whose area will be approximated
     * @return The approximate area under the curve f from x=l to x=u
     */
    public static double fnInt(double l, double u, Function<Double, Double> f) {
        // Returns zero is the size of the interval is zero
        if ((u - l) == 0) return 0;

        // The number of trapezoids is dynamically
        // adjusted based on the size of the interval
        // with a minimum of 10,000 trapezoids
        long h = Math.round((100000 * (u - l)) + 10000);
        // Total Computed Area
        double area = 0;
        // Change in x
        double deltaX = (u - l) / h;
        // Starting x-value
        double x = l;

        // Calculates the total area of each trapezoid and
        // aggregates it to the total calculated area
        for (int i = 0; i <= h; i++) {
            area += ((f.apply(x) + f.apply(x + deltaX)) / 2) * deltaX;
            // The next x-value
            x += deltaX;
        }
        return area;
    }
}
