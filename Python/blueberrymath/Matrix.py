from typing import *
import random
import math

'''
  Python helper function to generate a 2d array.
  :param rows The number of rows
  :param columns The number of columns
'''


def make2dArray(rows: int, columns: int):
    M = [[0 for i in range(columns)] for j in range(rows)]
    return M


class Matrix:

    def __init__(self, arr):
        self.matrix = [[]]
        self.columnCount = 0
        self.isSquare = False
        # A seed for the random matrix generate
        self.seed = 0
        self.arr = arr

        '''
         Creates an mxn matrix.
         :param arr A two-dimensional array containing the elements of the matrix.
        '''
        # Checks that the matrix is not missing a value at the ij-th position
        for i in range(len(self.arr)):
            if len(self.arr[i]) != len(self.arr[0]):
                print(
                    "Matrix cannot be created because {} does not match row 0's length.".format(i))
                raise NameError("Error: Invalid Matrix Format")

        self.matrix = self.arr
        self.rowCount = len(self.matrix)
        self.columnCount = len(self.matrix[0])
        self.isSquare = self.rowCount == self.columnCount

    def colunmCount(self):
        return self.columnCount

    def show(self) -> None:
        print(self.matrix)

        '''
        Calculates the size of the (m x n) matrix.
        :return A tuple [m, n] containing the size of the matrix, where m is
        the number of rows and n in the number of columns.
        '''

    def size(self):
        return [self.rowCount, self.columnCount]

        '''
         Generates the identity matrix of size s.
         param size The size of the identity matrix
         return The identity matrix of size s.
        '''
    @staticmethod
    def identity(size: int) -> callable:
        M = make2dArray(size, size)
        for i in range(0, size):
            for j in range(0, size):
                M[i][j] = 1 if (i == j) else 0

        '''
         Obtains the element of the mxn matrix at position i,j.
         :param i The rwo position of the element.
         :param j The column position of the element.
         :return The element at position i,j.
        '''

    def getElement(self, i: int, j: int) -> callable:
        return self.matrix[i][j]

    '''
    Clones the matrix.
    :return The cloned matrix.
    '''

    def cloneMatrix(self) -> callable:
        M = [_ for _ in range(self.rowCount)]
        for i in range(0, self.rowCount):
            M[i] = self.matrix[i]
        return Matrix(M)
    '''
      Converts the matrix into Row-Reduced Echelon Form (RREF).
      This algorithm is a translation from the pseudo-code found at wikipedia.org
      (https://en.wikipedia.org/wiki/Gaussian_elimination#Pseudocode) into python code.
      :return A new Matrix with the row-reduced version of this matrix.
    '''

    def RRFE(self) -> callable:
        M = self.cloneMatrix()
        h = 0
        k = 0

        while (h < M.rowCount and k < M.columnCount):
            i_max = h
            i = 1
            for i in range(0, M.rowCount):
                if (abs(M.getElement(i, k)) > abs(M.getElement(i_max, k))):
                    i_max = i
                if (M.getElement(i_max, k) == 0):
                    k = k + 1
                else:
                    temp = M.matrix[i_max]
                    M.matrix[i_max] = M.matrix[h]
                    M.matrix[h] = temp

                    s = 1 / M.getElement(h, k)
                    for i_max in range(0, M.columnCount):
                        M.matrix[h][i_max] *= s
                    for i in range(0, M.rowCount):
                        if i != h:
                            t = M.getElement(i, k)
                            for i_max in range(0, M.columnCount):
                                M.matrix[i][i_max] -= t * \
                                    M.getElement(h, i_max)
                    h += 1
                    k += 1
        return M

    '''
    Adds the number n to each element of the matrix.
    :param n The number to add to each element of the matrix.
    '''

    def elAdd(self, n: int) -> callable:
        for i in range(0, self.rowCount):
            for j in range(0, self.columnCount):
                self.matrix[i][j] += n  # NOTE: Add n to each element
        return self
    '''
    Subtracts the number n from each element of the matrix.
    :param n The number to subtract from each element of the matrix.
    '''

    def elSubtract(self, n: int) -> callable:
        return self.elAdd(-1 * n)

    '''
    Multiplies each element of the matrix by n.
    :param n The number by which element of the matrix will be multiplied.
    '''

    def elMultiply(self, n: int) -> callable:
        for i in range(0, self.rowCount):
            for j in range(0, self.columnCount):
                self.matrix[i][j] *= n  # NOTE: Multiply n to each element
        return self

    '''
    Divides each element of the matrix by the number n.
    :param n The number by which each element of the matrix will be divided.
    '''

    def elDivide(self, n: int) -> callable:
        # call elMultiply method to Multiply by 1/n each element
        return self.elMultiply(1 / n)

    '''
    Computes the transposed version (M^T) of this matrix.
    :return The transposed version (M^T) of this matrix.
    '''

    def T(self) -> callable:
        M = make2dArray(self.columnCount, self.rowCount)
        for i in range(0, self.columnCount):
            for j in range(0, self.rowCount):
                M[i][j] = self.getElement(j, i)
            return Matrix(M)

    '''
    Retrieves the row at position r of the matrix.
    :param r The position of the row to be retrieved.
    :return A new Vect whose elements are the entries at row r of the matrix.
    '''

    def getColumn(self, c: int) -> callable:

        column = [_ for _ in range(0, self.rowCount)]
        for i in range(0, self.rowCount):
            column[i] = self.getElement(i, c)
        return column

    '''
    Performs the augmentation (A.K.A. concatenation) of this matrix and the matrix M.
    :param M The matrix to be concatenated to this matrix.
    :return A new Matrix whose elements are the augmentation of this matrix and the matrix M.
    '''

    def augement(self, M: callable) -> callable:
        if self.rowCount != M.rowCount:
            print("Matrices must contai the same number of rows.")
            raise NameError("Error: Matrix row number error")
        augM = make2dArray(self.rowCount, self.columnCount + M.columnCount)
        for i in range(0, self.rowCount):
            for j in range(0, self.columnCount):
                augM[i][j] = self.getElement(i, j) if (
                    j < self.columnCount) else M.getElement(i, j - self.columnCount)
        return Matrix(augM)

    '''
    Computes the inverse of this matrix.
    :return The inverse of the matrix.
    :throws Error If the matrix is not square.
    :throws Error If the matrix has a zero-determinant.
    '''

    def inverse(self) -> callable:
        if self.isSquare == False:
            print("Matrix inverse cannot be computed because the matrix is not square")
            raise NameError("Error: Matrix not square")
        determinant = self.det()
        if (determinant == 0):
            print("Matrix inverse cannot be computed because matrix is singular")
            raise NameError("Error: Matrix is singular")
        return self.adjugate().elDivide(self.det())

    '''
     Computes the determinant of the the matrix.
     :return The determinant of the matrix.
     :throws Error If the matrix is not square.
    '''

    def det(self) -> int:
        if self.isSquare == False:
            print(
                "Matrix determinant cannot be computed because the matrix is not square")
            raise NameError("Error: Matrix not square")
        if self.rowCount == 1 and self.columnCount == 1:
            return self.getElement(0, 0)
        if self.rowCount == 2 and self.columnCount == 2:
            return (self.getElement(0, 0) * self.getElement(1, 1)) - (self.getElement(0, 1) * self.getElement(1, 0))
        determinant = 0
        for j in range(0, self.getColumn):
            determinant += math.pow(-1, j) * self.getElement(0,
                                                             j) * self.subMatrix(0, j).det()
        return determinant

    '''
     Computes the sub-matrix of this matrix when the row r and the column c are ignored from this matrix.
     :param r The row to ignore.
     :param c The column to ignore.
     :return A new Matrix whose elements are the elements of the sub-matrix of this matrix when the row r and the
     column c are ignored from this matrix.
    '''

    def subMatrix(self, r: int, c: int) -> callable:
        M = make2dArray(self.rowCount - 1, self.columnCount - 1)
        row = 0
        column = 0
        for i in range(0, self.rowCount):
            row = i if (i < r) else i - 1
            for j in range(0, self.columnCount):
                column = j if (j < c) else j - 1
                if (i != r and j != c):
                    M[row][column] = self.getElement(i, j)
        return Matrix(M)

    '''
      Computes the matrix of minors of this matrix.
      :return A new Matrix whose elements are the elements of the matrix of minors for this matrix.
    '''

    def mirorsMatrix(self) -> callable:
        if self.isSquare == False:
            print(
                "Matrix inverse cannot be computed because the matrix is not square")
            raise NameError("Error: Matrix is not square")
        MofMinors = make2dArray(self.rowCount, self.columnCount)
        for i in range(0, self.rowCount):
            for j in range(0, self.columnCount):
                MofMinors[i][j] = self.subMatrix(i, j).det()
        return Matrix(MofMinors)

    '''
      Computes the matrix of cofactors for this matrix.
      :return A new Matrix whose elements are the elements of the matrix of cofactors for this matrix.
    '''

    def cofactorsMatrix(self) -> callable:
        MofCofactors = self.mirorsMatrix()
        for i in range(0, MofCofactors.rowCount):
            for j in range(0, MofCofactors.columnCount):
                MofCofactors.matrix[i][j] = math.pow(
                    -1, i + j) * MofCofactors.getElement(i, j)
        return MofCofactors

    '''
     Returns the adjugate of this matrix.
     As defined by wikipedia, the adjugate, classical adjoint, or adjunct of
     a square matrix is the transpose of its cofactor matrix
     :return A new Matrix whose elements are the elements of the adjugate of this matrix.
    '''

    def adjugate(self) -> callable:
        return self.cofactorsMatrix().T()

    '''
     Maps each element of this matrix to a new Matrix based on the provided mapping function f.
     :param f The mapping function to be applied to each element of this matrix.
     :return A new Matrix whole elements are the mapped elements of this matrix based on the mapping function f.
    '''

    def map(self, f: callable) -> callable:
        M = make2dArray(self.rowCount, self.columnCount)
        for i in range(0, self.rowCount):
            for j in range(0, self.columnCount):
                M[i][j] = f(self.getElement(i, j))
        return Matrix(M)

    '''
     Multiplies the matrices.
     :param matrices A list of matrices to be multiplied.
     :return The product of all the matrices.
     :throws Error If there is a mismatch between the sizes of the matrices.
     :throws Error If there is only one matrix as argument.
    '''
    @staticmethod
    def multiply(matrices: list()) -> callable:
        if len(matrices) <= 1:
            print("Matrix multiplication can only happen between 2 or more matrices")
            raise NameError("Error: Error to multiply")
        if len(matrices) == 2:
            A = Matrix(matrices[0])
            B = Matrix(matrices[1])

            if A.colunmCount() != B.rowCount:
                e = "matrix size mismatch. Matrix A (size: {0}) cannot be multiplied by matrix B (size: {1})".format(
                    (A.rowCount + "x" + A.colunmCount()), (B.rowCount + "x" + B.colunmCount()))
                print(e)
                raise NameError("Error: Matrix cannot be multiplied")
            else:
                M = make2dArray(A.rowCount, B.colunmCount())
                for i in range(0, A.rowCount):
                    currentVectorA = A.getRow(i)
                    for j in range(0, B.colunmCount()):
                        currentVectoB = B.getColumn(j)
                        # Calulate their dot-product and palce the result
                        # in the appropriate position of the new matrix.
                        M[i][j] = currentVectorA.dot(currentVectoB)
                return Matrix(M)
        else:
            M = self.multiply(matrices[0], matrices[1])
            for i in range(0, (len(matrices) - 2)):
                M = self.multiply(M, matrices[i + 2])
                return M
    '''
     Generates an <code>mxn</code> matrix with random elements between zero and one.
     :param m The number of rows.
     :param n The number of columns.
     :return A new Matrix whose elements are random doubles between zero and one.
    '''
    @staticmethod
    def rand(m: int, n: int) -> callable:
        matrixEls = make2dArray(m, n)
        for i in range(0, m):
            for j in range(0, n):
                matrixEls[i][j] = random.random()
        return Matrix(matrixEls)

    '''
    Generates an <code>mxn</code> zero-matrix.
    :param m The number of rows.
    :param n The number of columns.
    :return A new Matrix whose elements are all zeros.
    '''
    @staticmethod
    def zeros(m: int, n: int) -> callable:
        matrixEls = make2dArray(m, n)
        for i in range(0, m):
            for j in range(0, n):
                matrixEls[i][j] = 0
        return Matrix(matrixEls)
    '''
     Computes the sparsity of the matrix.
     As defined by Wikipedia.org, a sparse matrix or sparse array is a matrix in which most of the elements are zero.
     :return A float representing the sparsity of the matrix.
    '''

    def sparsity(self) -> float:
        zeroCount = 0
        for i in range(0, self.rowCount):
            for j in range(0, self.columnCount):
                if self.getElement(i, j) == 0:
                    zeroCount += 1
        return (zeroCount / (self.rowCount * self.columnCount))

    '''
      Computes whether a matrix is sparse.
      :return True if the sparsity of the matrix is greater than or equal to 0.5
    '''

    def isSparse(self) -> bool:
        return self.sparsity() >= 0.5

    '''
     Computes the density of the matrix.
     :return A float representing the density of the matrix.
    '''

    def density(self) -> int:
        return 1 - self.sparsity()

    '''
      Computes whether a matrix is dense.
      :return True if the density of the matrix is greater than or equal to 0.5
    '''

    def isDense(self) -> bool:
        return self.density() >= 0.5

    '''
    Determines whether the matrix is a column matrix or not.
    :return True if the matrix is a column matrix. False otherwise.
    '''

    def isColumn(self):
        return self.columnCount == 1

    '''
     Determines whether the matrix is a row matrix or not.
     :return True if the matrix is a row matrix. False otherwise.
    '''

    def isRow(self):
        return self.rowCount == 1
    '''
     Get the row  of one matrix
     :return Return a new Object Vect with the row.
    '''

    def getRow(self, r: int) -> callable:
        from Vect import Vect
        return Vect(self.matrix[r])
