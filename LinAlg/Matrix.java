package BlueBerryMath.LinAlg;

import java.util.Arrays;
import java.util.function.Function;

public class Matrix {
    public double[][] matrix;
    public int rowCount;
    public int columnCount;
    public boolean isSquare;

    /**
     * Creates an mxn matrix.
     * @param arr A two-dimensional array containing the elements of the matrix.
     */
    public Matrix(double[][] arr) {
        // Checks that the matrix is not missing a value at the ij-th position
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != arr[0].length) {
                throw new Error("Matrix cannot be created because row " + i + " does not match row 0's length.");
            }
        }

        this.matrix = arr;
        this.rowCount = this.matrix.length;
        this.columnCount = this.matrix[0].length;
        this.isSquare = rowCount == columnCount;
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
    public int[] size() { return new int[]{ rowCount,  columnCount }; }


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
     * Obtains the element of the mxn matrix at position i,j.
     * @param i The rwo position of the element.
     * @param j The column position of the element.
     * @return The element at position i,j.
     */
    public double getElement(int i, int j) { return this.matrix[i][j]; }


    /**
     * Clones the matrix.
     * @return The cloned matrix.
     */
    public Matrix cloneMatrix() {
        double[][] M = new double[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) M[i] = this.matrix[i].clone();
        return new Matrix(M);
    }


    /**
     * Converts the matrix into Row-Reduced Echelon Form (RREF).
     * This algorithm is a translation from the pseudo-code found at wikipedia.org
     * (https://en.wikipedia.org/wiki/Gaussian_elimination#Pseudocode) into Java code.
     * @return A new Matrix with the row-reduced version of this matrix.
     */
    public Matrix RREF() {
        Matrix M = cloneMatrix();

        int h = 0;
        int k = 0;

        while (h < M.rowCount && k < M.columnCount) {
            /* Find the k-th pivot: */
            int i_max = h;
            for (int i = h + 1; i < M.rowCount; i++) {
                if (Math.abs(M.getElement(i, k)) > Math.abs(M.getElement(i_max, k))) i_max = i;
            }

            if (M.getElement(i_max, k) == 0) {
                /* No pivot in this column, pass to next column */
                k = k + 1;
            } else {
                // swap rows(h, i_max)
                double[] temp = M.matrix[i_max];
                M.matrix[i_max] = M.matrix[h];
                M.matrix[h] = temp;

                double s = 1.0 / M.getElement(h, k);
                for (i_max = 0; i_max < M.columnCount; i_max++) M.matrix[h][i_max] *= s;
                for (int i = 0; i < M.rowCount; i++) {
                    if (i != h) {
                        double t = M.getElement(i, k);
                        for (i_max = 0; i_max < M.columnCount; i_max++) {
                            M.matrix[i][i_max] -= t * M.getElement(h, i_max);
                        }
                    }
                }

                /* Increase pivot row and column */
                h++; k++;
            }
        }

        return M;
    }


    /**
     * Adds the number n to each element of the matrix.
     * @param n The number to add to each element of the matrix.
     */
    public Matrix elAdd(double n) {
        for (int i = 0; i < rowCount; i++) for (int j = 0; j < columnCount; j++) this.matrix[i][j] += n;
        return this;
    }


    /**
     * Subtracts the number n from each element of the matrix.
     * @param n The number to subtract from each element of the matrix.
     */
    public Matrix elSubtract(double n) { return elAdd(-1 * n); }


    /**
     * Multiplies each element of the matrix by n.
     * @param n The number by which element of the matrix will be multiplied.
     */
    public Matrix elMultiply(double n) {
        for (int i = 0; i < rowCount; i++) for (int j = 0; j < columnCount; j++) this.matrix[i][j] *= n;
        return this;
    }


    /**
     * Divides each element of the matrix by the number n.
     * @param n The number by which each element of the matrix will be divided.
     */
    public Matrix elDivide(double n) { return elMultiply(1.0 / n); }


    /**
     * Computes the transposed version (M^T) of this matrix.
     * @return The transposed version (M^T) of this matrix.
     */
    public Matrix T() {
        double[][] M = new double[columnCount][rowCount];

        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                M[i][j] = getElement(j, i);
            }
        }

        return new Matrix(M);
    }


    /**
     * Retrieves the row at position r of the matrix.
     * @param r The position of the row to be retrieved.
     * @return A new Vect whose elements are the entries at row r of the matrix.
     */
    public Vect getRow(int r) { return new Vect(this.matrix[r].clone()); }


    /**
     * Retrieves the column at position c of the matrix.
     * @param c The position of the column to be retrieved.
     * @return A new Vect whose elements are the entries at column c of the matrix.
     */
    public Vect getColumn(int c) {
        double[] column = new double[rowCount];
        for (int i = 0; i < rowCount; i++) column[i] = getElement(i, c);
        return new Vect(column);
    }


    /**
     * Performs the augmentation (A.K.A. concatenation) of this matrix and the matrix M.
     * @param M The matrix to be concatenated to this matrix.
     * @return A new Matrix whose elements are the augmentation of this matrix and the matrix M.
     */
    public Matrix augment(Matrix M) {
        if (this.rowCount != M.rowCount) throw new Error("Matrices must contain the same number of rows.");

        double[][] augM = new double[this.rowCount][this.columnCount + M.columnCount];

        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount + M.columnCount; j++) {
                augM[i][j] = (j < this.columnCount) ? getElement(i, j) : M.getElement(i,j - this.columnCount);
            }
        }

        return new Matrix(augM);
    }


    /**
     * Computes the inverse of this matrix.
     * @return The inverse of the matrix.
     * @throws Error If the matrix is not square.
     * @throws Error If the matrix has a zero-determinant.
     */
    public Matrix inverse() {
        if (!isSquare) throw new Error("Matrix inverse cannot be computed because the matrix is not square.");

        double determinant = det();
        if (determinant == 0) throw new Error("Matrix inverse cannot be computed because matrix is singular.");

        return adjugate().elDivide(det());
    }


    /**
     * Computes the determinant of the the matrix.
     * @return The determinant of the matrix.
     * @throws Error If the matrix is not square.
     */
    public double det() {
        if (!isSquare) throw new Error("Matrix determinant cannot be computed because the matrix is not square.");

        if (rowCount == 2 && columnCount == 2) {
            return (getElement(0, 0) * getElement(1, 1)) - (getElement(0, 1) * getElement(1, 0));
        }

        double determinant = 0;
        for (int j = 0; j < columnCount; j++) {
            determinant += Math.pow(-1, j) * getElement(0, j) * subMatrix(0, j).det();
        }
        return determinant;
    }


    /**
     * Computes the sub-matrix of this matrix when the row r and the column c are ignored from this matrix.
     * @param r The row to ignore.
     * @param c The column to ignore.
     * @return A new Matrix whose elements are the elements of the sub-matrix of this matrix when the row r and the
     * column c are ignored from this matrix.
     */
    public Matrix subMatrix(int r, int c) {
        double[][] M = new double[rowCount - 1][columnCount - 1];

        int row;
        int column;

        for (int i = 0; i < rowCount; i++) {
            row = (i < r) ? i : i - 1;

            for (int j = 0; j < columnCount; j++) {
                if (i != r && j != c) {
                    column = (j < c) ? j : j - 1;

                    M[row][column] = getElement(i, j);
                }
            }
        }

        return new Matrix(M);
    }


    /**
     * Computes the matrix of minors of this matrix.
     * @return A new Matrix whose elements are the elements of the matrix of minors for this matrix.
     */
    public Matrix minorsMatrix() {
        if (!isSquare) throw new Error("Matrix inverse cannot be computed because the matrix is not square.");

        double[][] MofMinors = new double[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                MofMinors[i][j] = subMatrix(i, j).det();
            }
        }

        return new Matrix(MofMinors);
    }


    /**
     * Computes the matrix of cofactors for this matrix.
     * @return A new Matrix whose elements are the elements of the matrix of cofactors for this matrix.
     */
    public Matrix cofactorsMatrix() {
        // We start with the matrix of minors
        Matrix MofCofactors = minorsMatrix();

        for (int i = 0; i < MofCofactors.rowCount; i++) {
            for (int j = 0; j < MofCofactors.columnCount; j++) {
                MofCofactors.matrix[i][j] = Math.pow(-1, i + j) * MofCofactors.getElement(i, j);
            }
        }

        return MofCofactors;
    }


    /**
     * Returns the adjugate of this matrix.
     * As defined by wikipedia, the adjugate, classical adjoint, or adjunct of
     * a square matrix is the transpose of its cofactor matrix
     * @return A new Matrix whose elements are the elements of the adjugate of this matrix.
     */
    public Matrix adjugate() { return cofactorsMatrix().T(); }


    /**
     * Maps each element of this matrix to a new Matrix based on the provided mapping function f.
     * @param f The mapping function to be applied to each element of this matrix.
     * @return A new Matrix whole elements are the mapped elements of this matrix based on the mapping function f.
     */
    public Matrix map(Function<Double, Double> f) {
        double[][] M = new double[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) for (int j = 0; j < columnCount; j++) M[i][j] = f.apply(getElement(i, j));
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


    /**
     * Generates an <code>mxn</code> matrix with random elements between zero and one.
     * @param m The number of rows.
     * @param n The number of columns.
     * @return A new Matrix whose elements are random doubles between zero and one.
     */
    public static Matrix rand(int m, int n) {
        double[][] matrixEls = new double[m][n];
        for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) matrixEls[i][j] = Math.random();
        return new Matrix(matrixEls);
    }


    /**
     * Generates an <code>mxn</code> zero-matrix.
     * @param m The number of rows.
     * @param n The number of columns.
     * @return A new Matrix whose elements are all zeros.
     */
    public static Matrix zeros(int m, int n) {
        double[][] matrixEls = new double[m][n];
        for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) matrixEls[i][j] = 0;
        return new Matrix(matrixEls);
    }
}
