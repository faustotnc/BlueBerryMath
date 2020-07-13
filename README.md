# BlueBerry Math 

This is a collection of packages divided by topics that each contain a collection of useful methods and functions
 related to that topics.
 
### Calculus
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

### Linear Algebra
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

### Statistics
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
