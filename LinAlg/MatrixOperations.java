package BlueBerryMath.LinAlg;

public class MatrixOperations {

    /**
     * Generates the identity matrix of size s.
     * @param s The size of the identity matrix
     * @return The identity matrix of size s.
     */
    public static Matrix identity(int s) {
        double[][] M = new double[s][s];
        for (int i = 0; i < s; i++) for (int j = 0; j < s; j++) M[i][j] = (i == j) ? 1 : 0;
        return new Matrix(M);
    }


    /**
     * Multiplies the matrices A and B.
     * @param A The first matrix (on the left).
     * @param B The second matrix (on the right).
     * @return A new Matrix whose entries are the product of the matrices A and B.
     * @throws Error If there is a mismatch between the sizes of the matrices.
     */
    public static Matrix multiply(Matrix A, Matrix B) {
        if (A.columnCount != B.rowCount) {
            String e = "Matrix size mismatch. Matrix A (size: " + A.rowCount + "x" + A.columnCount + ") " +
                    "cannot be multiplied by Matrix B (size: " + B.rowCount + "x" + B.columnCount + ")";
            throw new Error(e);
        } else {
            double[][] M = new double[A.rowCount][B.columnCount];
            for (int i = 0; i < A.rowCount; i++) { // For each row of A
                Vect currentVectorA = A.getRow(i);
                for (int j = 0; j < B.columnCount; j++) { // For each column of B
                    Vect currentVectorB = B.getColumn(j);
                    // Calculate their dot-product, and place the result
                    // in the appropriate position of the new matrix.
                    M[i][j] = currentVectorA.dot(currentVectorB);
                }
            }
            return new Matrix(M);
        }
    }


    /**
     * Multiplies the matrices.
     * @param matrices A list of matrices to be multiplied.
     * @return The product of all the matrices.
     * @throws Error If there is a mismatch between the sizes of the matrices.
     * @throws Error If there is only one matrix as argument.
     */
    public static Matrix multiply(Matrix... matrices) {
        if (matrices.length <= 1) throw new Error("Matrix multiplication can only happen between 2 or more matrices.");

        Matrix M = multiply(matrices[0], matrices[1]);
        for (int i = 0; i < matrices.length - 2; i++) M = multiply(M, matrices[i + 2]);
        return M;
    }

}
