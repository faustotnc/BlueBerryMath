package BlueBerryMath.LinAlg;

public class VectorCoords {
    private Vect vector;
    public VectorCoords(Vect v) { this.vector = v; }


    /**
     * Calculates the polar coordinates of a 2-dimensional vector.
     * @return The polar coordinates of a 2-dimensional vector;
     * @throws Error if the vector is not 2-dimensional.
     */
    public double[] toPolar() {
        if (this.vector.dim() == 2) {
            return new double[]{this.vector.magnitude(), this.vector.angle()};
        } else {
            throw new Error("Vector must be 2-dimensional.");
        }
    }


    /**
     * Calculates the spherical coordinates of a 3-dimensional vector.
     * @return The spherical coordinates of a 3-dimensional vector.
     * @throws Error if the vector is not 3-dimensional.
     */
    public double[] toSpherical() {
        if (this.vector.dim() == 3) {
            double theta = Math.acos(this.vector.vector[2] / this.vector.magnitude());
            double phi = Math.atan2(this.vector.vector[1], this.vector.vector[0]);
            return new double[]{ this.vector.magnitude(), theta, phi };
        } else {
            throw new Error("Vector must be 3-dimensional.");
        }
    }


    /**
     * Calculates the cylindrical coordinates of a 3-dimensional vector.
     * @return The cylindrical coordinates of a 3-dimensional vector.
     * @throws Error if the vector is not 3-dimensional.
     */
    public double[] toCylindrical() {
        if (this.vector.dim() == 3) {
            double rho = Math.sqrt( Math.pow(this.vector.vector[0], 2) + Math.pow(this.vector.vector[1], 2) );
            double theta = Math.atan2(this.vector.vector[1], this.vector.vector[0]);
            return new double[]{ rho, theta, this.vector.vector[2] };
        } else {
            throw new Error("Vector must be 3-dimensional.");
        }
    }
}
