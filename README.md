# BlueBerry Math 

This is a collection of packages divided by topics that each contain a collection of useful methods and functions
 related to that topics.
 
## Calculus
```java
// Import all modules from the Calculus package.
import BlueBerryMath.Calculus.*;

public class Main {
    public static void main(String[] args) {
        double intg = Calculus.fnInt(2, 3, Math::exp);
        System.out.println("The integral from 2 to 3 of e^x is: " + intg);
    }
}
```

## Linear Algebra
```java
// Import all modules from the Linear Algebra package.
import BlueBerryMath.LinAlg.*;

public class Main {
    public static void main(String[] args) {
        // Vectors
        Vect vector1 = new Vect(1, 2, 3);
        Vect vector2 = new Vect(4, 6, 8);
        Vect cross = vector1.cross(vector2);
        System.out.println("The cross-product of the vectors is:");
        cross.print();

        // Matrices
        Matrix matrix = new Matrix(new double[][]{
                {1, 2, 3},
                {0, 1, 4},
                {1, 0, 1}
        });
        System.out.println("\nThe inverse of the matrix is: ");
        matrix.inverse().print();
    }
}
```
#### Advanced Example
The Vect and Matrix modules can be used together to describe vector rotation in 2d and 3d space:
```java
import java.util.function.Function;
// Import everything from the Linear Algebra Package
import BlueBerryMath.LinAlg.*;

public class Main {
    public static void main(String[] args) {
        // Rotates the vector [1, 0] by 90degrees -> [0, 1]
        Vect original2dVector = new Vect(1, 0);
        rotate2d(original2dVector, Math.PI / 2).print();

        // Rotates the vector [1, 2, 1] by 45 degrees -> [sqrt(2), 2, 0]
        Vect original3dVector = new Vect(1, 2, 1);
        rotate3d_yAxis(original3dVector, Math.PI / 4).print();
    }


    /**
     * Rotates the given vector counterclockwise by the given angle.
     * @param vector The vector to be rotated.
     * @param angle The angle to rotate by.
     * @return A new Matrix (column vector) whose entries are the rotated vector.
     */
    public static Matrix rotate2d(Vect vector, double angle) {
        // The transformation matrix that rotates the vector
        Function<Double, Matrix> rotation = theta -> new Matrix(new double[][]{
                { Math.cos(theta), -Math.sin(theta) },
                { Math.sin(theta),  Math.cos(theta) }
        });

        // Converts the generic vector to be a column vector
        // so that it can be multiplied by the rotation matrix.
        Matrix columnVector = vector.toColumn();

        // Returns the matrix-vector product.
        return Matrix.multiply(rotation.apply(angle), columnVector);
    }


    /**
     * Rotates the given vector counterclockwise by the given angle about the y-axis.
     * @param vector The vector to be rotated.
     * @param angle The angle to rotate by.
     * @return A new Matrix (column vector) whose entries are the rotated vector.
     */
    public static Matrix rotate3d_yAxis(Vect vector, double angle) {
        // The transformation matrix that rotates the vector.
        // For rotations of 3d vectors around the other axes, visit
        // https://en.wikipedia.org/wiki/Rotation_matrix#In_three_dimensions
        Function<Double, Matrix> rotation = theta -> new Matrix(new double[][]{
                { Math.cos(theta),  0, Math.sin(theta) },
                {       0,          1,         0       },
                { -Math.sin(theta), 0, Math.cos(theta) }
        });

        // Converts the generic vector to be a column vector
        // so that it can be multiplied by the rotation matrix.
        Matrix columnVector = vector.toColumn();

        // Returns the matrix-vector product.
        return Matrix.multiply(rotation.apply(angle), columnVector);
    }
}
```

## Statistics
```java
// Import all modules from the Stats package.
import BlueBerryMath.Statistics.*;

public class Main {
    public static void main(String[] args) {
        Sample sample = new Sample(1, 2, 3, 3, 1, 1, 3, 6, 2, 1, 4);
        System.out.println("The average value is: " + sample.mean());
    }
}
```
