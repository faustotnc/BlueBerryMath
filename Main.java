package BlueBerryMath;

// Import the BlueBerryMath libraries

import BlueBerryMath.Statistics.*;

public class Main {
    public static void main(String[] args) {
        Statistics.Sample sample = new Statistics.Sample(1, 2, 3, 3, 1, 1, 3, 6, 2, 1, 4);
        System.out.println("The average value is: " + sample.mean());
    }
}
