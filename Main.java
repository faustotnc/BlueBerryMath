package BlueBerryMath;

// Import the BlueBerryMath libraries
import BlueBerryMath.Calculus.*;
import BlueBerryMath.Statistics.*;
import BlueBerryMath.Other.*;


public class Main {
    public static void main(String[] args) {
        // **** Calculus
        // Calculates the derivative of x^2 at x=5
        double derivative = Calculus.nDeriv(5, x -> Math.pow(x, 2));
        System.out.println("Derivative: " + derivative);
        // Calculates the integral of e^x from x=1, to x=6
        double integral = Calculus.fnInt(1, 6, Math::exp);
        System.out.println("Integral: " + integral);

        System.out.println("\n=================================\n");

        // ****  Statistics
        Statistics.Sample sample = new Statistics.Sample(9, 10, 12, 12, 13, 13, 13, 15, 15, 15, 16, 16, 18, 22, 23, 24, 24, 100, 100);
        System.out.println("The mean: " + sample.mean());
        System.out.println("The median: " + sample.median());
        System.out.println("The mode: " + sample.mode());
        System.out.println("The range: " + sample.range());
        System.out.println("The minimum: " + sample.min);
        System.out.println("The maximum: " + sample.max);
        System.out.println("The sum: " + sample.sum());
        System.out.println("The quartiles: " + sample.quartiles());
        System.out.println("The inner-quartile-range: " + sample.IQR());
        System.out.println("The outliers: " + sample.outliers());
        System.out.println("The frequency: " + sample.frequency());
        System.out.println("The variance: " + sample.variance());
        System.out.println("The Std. Deviation: " + sample.stdDeviation());

        System.out.println("\n=================================\n");

        // **** Other maths
        System.out.println("Factorial of 10: " + Other.factorial(10));
        System.out.println("4 choose 2: " + Other.choose(4, 2));
    }
}
