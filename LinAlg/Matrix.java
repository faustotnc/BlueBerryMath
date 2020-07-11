package BlueBerryMath.LinAlg;

import java.util.Arrays;

public class Matrix {
    public double[][] matrix;

    public Matrix(double[][] arr) {
        this.matrix = arr;
    }


    /**
     * Prints the matrix to the console.
     */
    public void print() {
        String m = Arrays.deepToString(this.matrix)
                .replace("], ", "]\n") // Each row takes a line
                .replace("[", " [") // Align the beginning of each row
                .replace(" [ [", "[["); // Correct the beginning of the matrix
        System.out.println(m);
    }


    /**
     * Calculates the size of the (m x n) matrix.
     * @return A tuple [m, n] containing the size of the matrix, where m is
     * the number of rows and n in the number of columns.
     */
    public int[] size() {
        return new int[]{ this.matrix.length, this.matrix[0].length };
    }


    /**
     * Converts the matrix into Row-Reduced Echelon Form (RREF).
     * This algorithm is a translation from the pseudo-code found at rosettacode.org
     * (https://rosettacode.org/wiki/Reduced_row_echelon_form) into Java code.
     * @return A new Matrix with the row-reduced version of this matrix.
     */
    public Matrix RREF() {
        double[][] M = this.matrix.clone();
        int lead = 0;
        int rowCount = size()[0];
        int columnCount = size()[1];

        for (int r = 0; r < rowCount; r++) {
            if (columnCount <= lead) break;

            int i = r;
            while (M[i][lead] == 0) {
                i++;
                if (rowCount == i) {
                    i = r; lead++;
                    if (columnCount == lead) break;
                }
            }

            // Swap rows i and r
            double[] rowI = M[i].clone();
            double[] rowR = M[r].clone();
            M[i] = rowR;
            M[r] = rowI;
            
            // If M[r, lead] is not 0 divide row r by M[r, lead]
            if (M[r][lead] != 0) {
                for (int j = 0; j < M[r].length; j++) {
                    M[r][j] = M[r][j] / M[r][lead];
                }
            }

            for (int j = 0; j < rowCount; j++) {
                if (j != r) {
                    // Subtract (M[j, lead] multiplied by row r) from row i
                    for (int k = 0; k < M[j].length; k++) {
                        double el = M[j][lead] * M[r][k];
                        M[j][k] = M[j][k] - el;
                    }
                }
            }

            lead++;
        }

        return new Matrix(M);
    }



}
