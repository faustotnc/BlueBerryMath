package BlueBerryMath;

// Import the BlueBerryMath libraries
import BlueBerryMath.Calculus.*;
import BlueBerryMath.Statistics.*;
import BlueBerryMath.Other.*;
import BlueBerryMath.LinAlg.*;


public class Main {
    public static void main(String[] args) {
        // **** Calculus
        // Calculates the derivative of x^2 at x=5
        double derivative = Calculus.nDeriv(5, x -> Math.pow(x, 2));
        System.out.println("Derivative: " + derivative);
        // Calculates the integral of e^x from x=1, to x=6
        double integral = Calculus.fnInt(1, 6, Math::exp);
        System.out.println("Integral: " + integral);
        // Checks if a function is increasing on an interval [a, b]
        System.out.println("Is x^2 increasing on x=2 to x=3? " + Calculus.isIncreasing(2, 6, x -> Math.pow(x, 2)));
        // Checks if a function is decreasing on an interval [a, b]
        System.out.println("Is e^x decreasing on x=-3 to x=0? " + Calculus.isDecreasing(-3, 0, Math::exp));

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
        System.out.println("Probability of 12: " + sample.prob(12));

        System.out.println("\n=================================\n");

        // **** Linear Algebra
        Vect vector = new Vect(1, 4);
        // Vectors
        vector.print();
        System.out.println("The vector's magnitude: " + vector.magnitude());
        System.out.println("The vector's angle in radians: " + vector.angle());
        System.out.print("Normalized Vector: "); vector.norm().print();
        System.out.println("Dot products: " + vector.dot(new Vect(1, 2)));
        System.out.print("Cross products: "); new Vect(1, 2, 3).cross(new Vect(1, 4, 5)).print();
        // Matrices
        double[][] myMatrix = {
                {20, 1, 3.2},
                {1, 4, 0},
                {0, 10, 102}
        };
        Matrix matrix = new Matrix(myMatrix);
        matrix.print();
        matrix.RREF().print();

        System.out.println("\n=================================\n");

        // **** Other maths
        System.out.println("Factorial of 10: " + Other.factorial(10));
        System.out.println("4 choose 2: " + Other.choose(4, 2));
    }
}
