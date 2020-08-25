import { Vect } from "./Vect";


/**
 * JavaScript helper function to generate a 2d array.
 * @param rows The number of rows
 * @param columns The number of columns
 */
export function make2dArray(rows: number, columns: number): number[][] {
    let M: number[][] = [];
    for (let i = 0; i < rows; i++) for (let j = 0; j < rows; j++) M[i] = new Array(columns);
    return M;
}


export class Matrix {
    public matrix: number[][];
    public rowCount: number;
    public columnCount: number;
    public isSquare: boolean;
    /** A seed for the random matrix generation. */
    public static seed: number = 0;



    /**
     * Creates an mxn matrix.
     * @param arr A two-dimensional array containing the elements of the matrix.
     */
    constructor(arr: number[][]) {
        // Checks that the matrix is not missing a value at the ij-th position
        for (let i = 0; i < arr.length; i++) {
            if (arr[i].length !== arr[0].length) {
                throw new Error("Matrix cannot be created because row " + i + " does not match row 0's length.");
            }
        }

        this.matrix = arr;
        this.rowCount = this.matrix.length;
        this.columnCount = this.matrix[0].length;
        this.isSquare = this.rowCount === this.columnCount;
    }


    /**
     * Prints the matrix to the console.
     */
    public print(): void {
        let m = "[";
        for (let i = 0; i < this.rowCount; i++) {
            if (i !== 0) m += " "; // Aligns the elements
            if (i < this.rowCount - 1) {
                m += "[" + this.getRow(i).vector.toString() + ']\n';
            } else {
                m += "[" + this.getRow(i).vector.toString() + ']]';
            }
        }
        console.log(m)
    }


    /**
     * Calculates the size of the (m x n) matrix.
     * @return A tuple [m, n] containing the size of the matrix, where m is
     * the number of rows and n in the number of columns.
     */
    public size(): number[] { return [this.rowCount, this.columnCount]; }


    /**
     * Generates the identity matrix of size s.
     * @param size The size of the identity matrix
     * @return The identity matrix of size s.
     */
    public static identity(size: number): Matrix {
        let M: number[][] = make2dArray(size, size);
        for (let i = 0; i < size; i++) for (let j = 0; j < size; j++) M[i][j] = (i === j) ? 1 : 0;
        return new Matrix(M);
    }


    /**
     * Obtains the element of the mxn matrix at position i,j.
     * @param i The rwo position of the element.
     * @param j The column position of the element.
     * @return The element at position i,j.
     */
    public getElement(i: number, j: number): number { return this.matrix[i][j]; }


    /**
     * Clones the matrix.
     * @return The cloned matrix.
     */
    public cloneMatrix(): Matrix {
        let M = new Array(this.rowCount);
        for (let i = 0; i < this.rowCount; i++) M[i] = [...this.matrix[i]];
        return new Matrix(M);
    }


    /**
     * Converts the matrix into Row-Reduced Echelon Form (RREF).
     * This algorithm is a translation from the pseudo-code found at wikipedia.org
     * (https://en.wikipedia.org/wiki/Gaussian_elimination#Pseudocode) into Java code.
     * @return A new Matrix with the row-reduced version of this matrix.
     */
    public RREF(): Matrix {
        let M: Matrix = this.cloneMatrix();

        let h = 0;
        let k = 0;

        while (h < M.rowCount && k < M.columnCount) {
            /* Find the k-th pivot: */
            let i_max = h;
            for (let i = h + 1; i < M.rowCount; i++) {
                if (Math.abs(M.getElement(i, k)) > Math.abs(M.getElement(i_max, k))) i_max = i;
            }

            if (M.getElement(i_max, k) === 0) {
                /* No pivot in this column, pass to next column */
                k = k + 1;
            } else {
                // swap rows(h, i_max)
                let temp = M.matrix[i_max];
                M.matrix[i_max] = M.matrix[h];
                M.matrix[h] = temp;

                let s = 1 / M.getElement(h, k);
                for (i_max = 0; i_max < M.columnCount; i_max++) M.matrix[h][i_max] *= s;
                for (let i = 0; i < M.rowCount; i++) {
                    if (i != h) {
                        let t = M.getElement(i, k);
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
    public elAdd(n: number): Matrix {
        for (let i = 0; i < this.rowCount; i++) for (let j = 0; j < this.columnCount; j++) this.matrix[i][j] += n;
        return this;
    }


    /**
     * Subtracts the number n from each element of the matrix.
     * @param n The number to subtract from each element of the matrix.
     */
    public elSubtract(n: number): Matrix { return this.elAdd(-1 * n); }


    /**
     * Multiplies each element of the matrix by n.
     * @param n The number by which element of the matrix will be multiplied.
     */
    public elMultiply(n: number): Matrix {
        for (let i = 0; i < this.rowCount; i++) for (let j = 0; j < this.columnCount; j++) this.matrix[i][j] *= n;
        return this;
    }


    /**
     * Divides each element of the matrix by the number n.
     * @param n The number by which each element of the matrix will be divided.
     */
    public elDivide(n: number): Matrix { return this.elMultiply(1 / n); }


    /**
     * Computes the transposed version (M^T) of this matrix.
     * @return The transposed version (M^T) of this matrix.
     */
    public T(): Matrix {
        let M = make2dArray(this.columnCount, this.rowCount);

        for (let i = 0; i < this.columnCount; i++) {
            for (let j = 0; j < this.rowCount; j++) {
                M[i][j] = this.getElement(j, i);
            }
        }

        return new Matrix(M);
    }


    /**
     * Retrieves the row at position r of the matrix.
     * @param r The position of the row to be retrieved.
     * @return A new Vect whose elements are the entries at row r of the matrix.
     */
    public getRow(r: number): Vect { return new Vect(...this.matrix[r]); }


    /**
     * Retrieves the column at position c of the matrix.
     * @param c The position of the column to be retrieved.
     * @return A new Vect whose elements are the entries at column c of the matrix.
     */
    public getColumn(c: number): Vect {
        let column = new Array(this.rowCount);
        for (let i = 0; i < this.rowCount; i++) column[i] = this.getElement(i, c);
        return new Vect(...column);
    }


    /**
     * Performs the augmentation (A.K.A. concatenation) of this matrix and the matrix M.
     * @param M The matrix to be concatenated to this matrix.
     * @return A new Matrix whose elements are the augmentation of this matrix and the matrix M.
     */
    public augment(M: Matrix): Matrix {
        if (this.rowCount != M.rowCount) throw new Error("Matrices must contain the same number of rows.");

        let augM = make2dArray(this.rowCount, this.columnCount + M.columnCount);

        for (let i = 0; i < this.rowCount; i++) {
            for (let j = 0; j < this.columnCount + M.columnCount; j++) {
                augM[i][j] = (j < this.columnCount) ? this.getElement(i, j) : M.getElement(i, j - this.columnCount);
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
    public inverse(): Matrix {
        if (!this.isSquare) throw new Error("Matrix inverse cannot be computed because the matrix is not square.");

        let determinant = this.det();
        if (determinant === 0) throw new Error("Matrix inverse cannot be computed because matrix is singular.");

        return this.adjugate().elDivide(this.det());
    }


    /**
     * Computes the determinant of the the matrix.
     * @return The determinant of the matrix.
     * @throws Error If the matrix is not square.
     */
    public det(): number {
        if (!this.isSquare) throw new Error("Matrix determinant cannot be computed because the matrix is not square.");

        if (this.rowCount === 1 && this.columnCount === 1) return this.getElement(0, 0);

        if (this.rowCount === 2 && this.columnCount === 2) {
            return (this.getElement(0, 0) * this.getElement(1, 1)) - (this.getElement(0, 1) * this.getElement(1, 0));
        }

        let determinant = 0;
        for (let j = 0; j < this.columnCount; j++) {
            determinant += Math.pow(-1, j) * this.getElement(0, j) * this.subMatrix(0, j).det();
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
    public subMatrix(r: number, c: number): Matrix {
        let M = make2dArray(this.rowCount - 1, this.columnCount - 1);

        let row: number;
        let column: number;

        for (let i = 0; i < this.rowCount; i++) {
            row = (i < r) ? i : i - 1;

            for (let j = 0; j < this.columnCount; j++) {
                column = (j < c) ? j : j - 1;
                if (i != r && j != c) M[row][column] = this.getElement(i, j);
            }
        }

        return new Matrix(M);
    }


    /**
     * Computes the matrix of minors of this matrix.
     * @return A new Matrix whose elements are the elements of the matrix of minors for this matrix.
     */
    public minorsMatrix(): Matrix {
        if (!this.isSquare) throw new Error("Matrix inverse cannot be computed because the matrix is not square.");

        let MofMinors = make2dArray(this.rowCount, this.columnCount);

        for (let i = 0; i < this.rowCount; i++) {
            for (let j = 0; j < this.columnCount; j++) {
                MofMinors[i][j] = this.subMatrix(i, j).det();
            }
        }

        return new Matrix(MofMinors);
    }


    /**
     * Computes the matrix of cofactors for this matrix.
     * @return A new Matrix whose elements are the elements of the matrix of cofactors for this matrix.
     */
    public cofactorsMatrix(): Matrix {
        // We start with the matrix of minors
        let MofCofactors = this.minorsMatrix();

        for (let i = 0; i < MofCofactors.rowCount; i++) {
            for (let j = 0; j < MofCofactors.columnCount; j++) {
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
    public adjugate(): Matrix { return this.cofactorsMatrix().T(); }


    /**
     * Maps each element of this matrix to a new Matrix based on the provided mapping function f.
     * @param f The mapping function to be applied to each element of this matrix.
     * @return A new Matrix whole elements are the mapped elements of this matrix based on the mapping function f.
     */
    public map(f: (x: number) => number): Matrix {
        let M = make2dArray(this.rowCount, this.columnCount);
        for (let i = 0; i < this.rowCount; i++) for (let j = 0; j < this.columnCount; j++) M[i][j] = f(this.getElement(i, j));
        return new Matrix(M);
    }


    /**
     * Multiplies the matrices.
     * @param matrices A list of matrices to be multiplied.
     * @return The product of all the matrices.
     * @throws Error If there is a mismatch between the sizes of the matrices.
     * @throws Error If there is only one matrix as argument.
     */
    public static multiply(...matrices: Matrix[]): Matrix {
        if (matrices.length <= 1) throw new Error("Matrix multiplication can only happen between 2 or more matrices.");

        if (matrices.length === 2) {
            let A = matrices[0];
            let B = matrices[1]
            if (A.columnCount !== B.rowCount) {
                let e = "Matrix size mismatch. Matrix A (size: " + A.rowCount + "x" + A.columnCount + ") " +
                    "cannot be multiplied by Matrix B (size: " + B.rowCount + "x" + B.columnCount + ")";
                throw new Error(e);
            } else {
                let M = make2dArray(A.rowCount, B.columnCount);
                for (let i = 0; i < A.rowCount; i++) { // For each row of A
                    let currentVectorA = A.getRow(i);
                    for (let j = 0; j < B.columnCount; j++) { // For each column of B
                        let currentVectorB = B.getColumn(j);
                        // Calculate their dot-product, and place the result
                        // in the appropriate position of the new matrix.
                        M[i][j] = currentVectorA.dot(currentVectorB);
                    }
                }
                return new Matrix(M);
            }
        } else {
            let M = this.multiply(matrices[0], matrices[1]);
            for (let i = 0; i < matrices.length - 2; i++) M = this.multiply(M, matrices[i + 2]);
            return M;
        }
    }


    /**
     * Generates an <code>mxn</code> matrix with random elements between zero and one.
     * @param m The number of rows.
     * @param n The number of columns.
     * @return A new Matrix whose elements are random doubles between zero and one.
     */
    public static rand(m: number, n: number): Matrix {
        // let random = (this.seed && this.seed !== 0) ? new Random(seed) : new Random();
        let matrixEls: number[][] = make2dArray(m, n);

        for (let i = 0; i < m; i++) for (let j = 0; j < n; j++) matrixEls[i][j] = Math.random();
        return new Matrix(matrixEls);
    }


    /**
     * Generates an <code>mxn</code> zero-matrix.
     * @param m The number of rows.
     * @param n The number of columns.
     * @return A new Matrix whose elements are all zeros.
     */
    public static zeros(m: number, n: number): Matrix {
        let matrixEls: number[][] = make2dArray(m, n);
        for (let i = 0; i < m; i++) for (let j = 0; j < n; j++) matrixEls[i][j] = 0;
        return new Matrix(matrixEls);
    }


    /**
     * Computes the sparsity of the matrix.
     * As defined by Wikipedia.org, a sparse matrix or sparse array is a matrix in which most of the elements are zero.
     * @return A float representing the sparsity of the matrix.
     */
    public sparsity(): number {
        let zeroCount = 0;

        for (let i = 0; i < this.rowCount; i++) {
            for (let j = 0; j < this.columnCount; j++) {
                if (this.getElement(i, j) === 0) zeroCount++;
            }
        }

        return (zeroCount / (this.rowCount * this.columnCount));
    }


    /**
     * Computes whether a matrix is sparse.
     * @return True if the sparsity of the matrix is greater than or equal to 0.5
     */
    public isSparse(): boolean { return this.sparsity() >= 0.5; }


    /**
     * Computes the density of the matrix.
     * @return A float representing the density of the matrix.
     */
    public density(): number { return 1 - this.sparsity(); }


    /**
     * Computes whether a matrix is dense.
     * @return True if the density of the matrix is greater than or equal to 0.5
     */
    public isDense(): boolean { return this.density() >= 0.5; }


    /**
     * Determines whether the matrix is a column matrix or not.
     * @return True if the matrix is a column matrix. False otherwise.
     */
    public isColumn() { return this.columnCount == 1; }


    /**
     * Determines whether the matrix is a row matrix or not.
     * @return True if the matrix is a row matrix. False otherwise.
     */
    public isRow() { return this.rowCount == 1; }
}
