package BlueBerryMath.LinAlg;

import java.util.Arrays;

public class Vect {
    public double[] vector;
    public Vect(double... elements) { this.vector = elements; }


    /**
     * Prints out the vector to the console.
     */
    public void print() {
        System.out.println(Arrays.toString(this.vector));
    }


    /**
     * Calculates the magnitude of the vector
     * @return The magnitude of the vector
     */
    public double magnitude() {
        double mag = 0;
        for (double element: this.vector) mag += Math.pow(element, 2);
        return Math.sqrt(mag);
    }


    /**
     * Calculates the angle of a 2-dimensional vector
     * @return The angle of a 2-dimensional vector
     */
    public double angle() {
        return (this.vector.length == 2) ? Math.atan2(this.vector[1], this.vector[0]) : Double.NaN;
    }


    /**
     * Calculates the dimensions of the vector
     * @return The dimensions of the vector
     */
    public int dim() {
        return this.vector.length;
    }


    /**
     * Checks that the passed vector matched the dimension of the current vector.
     * If the dimensions do not match, throws an error.
     * @param v The secondary vector to check.
     * @return True if the dimensions match.
     * @throws Error if the dimensions do not match.
     */
    private boolean dimsMatch(Vect v) {
        if (this.dim() != v.dim()) {
            throw new Error("The dimensions of the vectors do not match. dim("+ this.dim() +") != " + "dim(" + v.dim() + ").");
        } else {
            return true;
        }
    }


    /**
     * Obtains the coordinates of the vector (for 2d and 3d vectors)
     * in different coordinate systems.
     */
    public VectorCoords Coords = new VectorCoords(this);


    /**
     * Adds the elements of the vectors to form a new vector.
     * @param v The secondary vector to add to the current vector.
     * @return A new Vect with the sum of the added vectors.
     */
    public Vect plus(Vect v) {
        double[] w = new double[this.vector.length];
        if (dimsMatch(v)) for (int i = 0; i < this.vector.length; i++) w[i] = this.vector[i] + v.vector[i];
        return new Vect(w);
    }


    /**
     * Subtracts the elements of the vectors to form a new vector.
     * @param v The secondary vector to subtract from the current vector.
     * @return A new Vect with the subtraction of the vectors.
     */
    public Vect minus(Vect v) {
        double[] w = new double[this.vector.length];
        if (dimsMatch(v)) for (int i = 0; i < this.vector.length; i++) w[i] = this.vector[i] - v.vector[i];
        return new Vect(w);
    }


    /**
     * Scales the vector by alpha.
     * @param alpha The quantity by which to scale the vector.
     * @return A new Vect as the scaled version of this vector.
     */
    public Vect scale(double alpha) {
        double[] w = new double[this.vector.length];
        for (int i = 0; i < this.vector.length; i++) w[i] = this.vector[i] * alpha;
        return new Vect(w);
    }


    /**
     * Generates a new Vect with the normalized form of the this vector.
     * @return A new Vect with the normalized form of the this vector.
     */
    public Vect norm() {
        double vMag = this.magnitude();
        double[] vNorm = new double[this.vector.length];
        for (int i = 0; i < this.vector.length; i++) vNorm[i] = this.vector[i] / vMag;
        return new Vect(vNorm);
    }


    /**
     * Calculates the dot-product of two vectors.
     * @param v The secondary vector to calculate the dot-product.
     * @return The dot product of the two vectors.
     * @throws Error if the dimensions of the two vectors do not match.
     */
    public double dot(Vect v) {
        double prod = 0;
        if (dimsMatch(v)) {
            for (int i = 0; i < this.vector.length; i++) {
                double el = this.vector[i];
                prod += el * v.vector[i];
            }
        }
        return prod;
    }


    /**
     * Calculates the cross-product of two vectors in R^3.
     * @param v The secondary vector in R^3 to calculate the cross-product.
     * @return A new Vect in R^3 as the result of the cross-product of the two vectors.
     */
    public Vect cross(Vect v) {
        if (this.dim() != 3 || v.dim() != 3) {
            throw new Error("Vectors must form a subset of R^3.");
        } else {
            double e1 = this.vector[1] * v.vector[2] - v.vector[1] * this.vector[2];
            double e2 = this.vector[2] * v.vector[0] - v.vector[2] * this.vector[0];
            double e3 = this.vector[0] * v.vector[1] - v.vector[0] * this.vector[1];
            return new Vect(e1, e2, e3);
        }
    }
}