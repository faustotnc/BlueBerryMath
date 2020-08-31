from typing import *
import math
import random
# from VectorCoords import VectorCoords


class Vect():

    def __init__(self, elements: list()):
        '''
         Creates an n-dimensional vector.
         :param elements The elements of the vector.
        '''

        self.vector = elements
        # A seed for the random vector generation
        self.seed = 0

        '''
        Prints out the vector to the terminal
        '''

    def print(self) -> None:
        print(self.vector)

    '''
      Calculates the magnitude of the vector
      :return The magnitude of the vector
     '''

    def magnitude(self) -> float:
        mag = 0
        for i in self.vector:
            mag += pow(i, 2)
        return math.sqrt(mag)

    '''
      Calculates the angle of a 2-dimensional vector
      :return The angle of a 2-dimensional vector
     '''

    def angle(self) -> float:
        return math.atan2(self.vector[1], self.vector[0]) if (self.dim() == 2) else 0

    '''
      Calculates the dimensions of the vector

      :return The dimensions of the vector
     '''

    def dim(self) -> int:
        return len(self.vector)

    '''
      Checks that the passed vector matched the dimension of the current vector.
      If the dimensions do not match, throws an error.
      :param v The secondary vector to check.
      :return True if the dimensions match.
      :throws Error if the dimensions do not match.
     '''

    def __dimsMatch(self, v: list()) -> bool:
        if (self.dim() != len(v)):
            raise NameError('The dimensions of the vectors do not match.')
        else:
            return True

    '''
      Obtains the coordinates of the vector (for 2d and 3d vectors)
      in different coordinate systems.
     '''

    def Coords(self) -> callable:
        from VectorCoords import VectorCoords
        return VectorCoords(self.vector)

    '''
      Adds the elements of the vectors to form a new vector.
      :param v The secondary vector to add to the current vector.
      :return A new Vect with the sum of the added vectors.
     '''

    def plus(self, v: list()) -> callable:
        w = [_ for _ in range(self.dim())]
        if (self.__dimsMatch(v)):
            for i in range(0, self.dim()):
                w[i] = self.getElement(i) + v[i]
        return Vect(w)

    '''
      Subtracts the elements of the vectors to form a new vector.
      :param v The secondary vector to subtract from the current vector.
      :return A new Vect with the subtraction of the vectors.
    '''

    def minus(self, v: list()) -> callable:
        w = [_ for _ in range(self.dim())]
        if (self.__dimsMatch(v)):
            for i in range(0, self.dim()):
                w[i] = self.getElement(i) - v[i]
        return Vect(w)

    '''
      Scales the vector by alpha.
      :param alpha The quantity by which to scale the vector.
      :return A new Vect as the scaled version of this vector.
    '''

    def scale(self, alpha: int) -> callable:
        w = [_ for _ in range(self.dim())]
        for i in range(0, self.dim()):
            w[i] = self.getElement(i) * alpha
        return Vect(w)
    '''
      Generates a new Vect with the normalized form of the this vector.
      :return A new Vect with the normalized form of the this vector.
    '''

    def norm(self) -> callable:
        vMag = self.magnitude()
        vNorm = [_ for _ in range(self.dim())]
        for i in range(0, self.dim()):
            vNorm[i] = self.getElement(i) / vMag
        return Vect(vNorm)

    '''
      Calculates the dot-product of two vectors.
      :param v The secondary vector to calculate the dot-product.
      :return The dot product of the two vectors.
      :throws Error if the dimensions of the two vectors do not match.
    '''

    def dot(self, v: list) -> int:
        prod = 0
        if (self.__dimsMatch(v)):
            for i in range(0, self.dim()):
                el = self.getElement(i)
                prod += el * v[i]
        return prod
    '''
      Calculates the cross-product of two vectors in R^3.
      :param v The secondary vector in R^3 to calculate the cross-product.
      :return A new Vect in R^3 as the result of the cross-product of the two vectors.
    '''

    def cross(self, v: list) -> callable:
        if(self.dim() != 3 or len(v) != 3):
            raise NameError("Vectors must form a subset of R^3.")
        else:
            e1 = self.vector[1] * v[2] - v[1] * self.vector[2]
            e2 = self.vector[2] * v[0] - v[2] * self.vector[0]
            e3 = self.vector[0] * v[1] * v[0] * self.vector[1]
            return Vect([e1, e2, e3])
    '''
      Obtains the element of the vector at position pos.
      :param pos The position of the element to obtain.
      :return The element of this vector at position pos.
     '''

    def getElement(self, post: int) -> int:
        return self.vector[post]

    '''
     Sets the element of the vector at position <code>pos</code> to the value <code>newEl</code>.
     :param pos The position where to set the new element.
     :param newEl The new element to set at position <code>pos</code>.
    '''

    def setElement(self, pos: int, newEl: int) -> None:
        self.vector[pos] = newEl
    '''
      Converts the vector into a row vector (a matrix with one column and n columns).
      :return A new Matrix with one row and n rows.
    '''

    def toColumn(self) -> callable:
        from Matrix import make2dArray, Matrix
        c = make2dArray(self.dim(), 1)
        for i in range(0, self.dim()):
            c[i][0] = self.getElement(i)
        return Matrix(c)
    '''
      Converts the vector into a row vector (a matrix with one row and n columns).
      :return A new Matrix with one row and n columns.
    '''

    def toRow(self) -> callable:
        from Matrix import make2dArray, Matrix
        c = make2dArray(1, self.dim())
        for i in range(0, self.dim()):
            c[0][i] = self.getElement(i)
        return Matrix(c)
    '''
      Maps each element of this vector to a new Vect based on the provided mapping function f.
      :param f The mapping function to be applied to each element of this vector.
      :return A new Vect whole elements are the mapped elements of this vector based on the mapping function f.
    '''

    def map(self, f: callable) -> callable:
        V = [_ for _ in range(self.dim())]
        for i in range(0, self.dim()):
            V[i] = f(self.getElement(i))
        return Vect(V)
    '''
      Generates a vector of dimension <code>dim</code> with random elements between zero and one.
      :param dim The dimension of the vector.
      :return A new Vect whose elements are random doubles between zero and one.
    '''
    @staticmethod
    def rand(dim: int) -> callable:
        vectEls = [_ for _ in range(dim)]
        for i in range(0, dim):
            vectEls[i] = random.random()
        return Vect(vectEls)
    '''
      Generates a zero-vector of dimension <code>dim</code>.
      :param dim The dimension of the zero vector.
      :return A new Vect of dimension <code>dim</code> whose elements are all zeros.
    '''
    @staticmethod
    def zeros(dim: int) -> callable:
        vectEls = [_ for _ in range(dim)]
        for i in range(0, dim):
            vectEls[i] = 0
        return Vect(vectEls)
    '''
      Generates a one-hot vector.
      A one-hot vector is a vector whose elements are all zeros,
      except for one element with value of 1 at position x.
      :param dim The dimension of the vector.
      :param hotPos The position of the 1 in the one-hot vector.
      :return A new Vect whose elements are all zeros, except for the
      :lement at position <code>pos</code> with value of 1.
    '''
    @staticmethod
    def oneHot(dim: int, hotPos: int) -> callable:
        vectEls = [_ for _ in range(0, dim)]
        for i in range(0, len(vectEls)):
            if i == hotPos:
                vectEls[i] = 1
            else:
                vectEls[i] = 0
        return Vect(vectEls)
