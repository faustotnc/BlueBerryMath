from typing import *
import math


class VectorCoords:
    def __init__(self, v: callable):

        self.vector = v

    def __vector(self):
        from Vect import Vect
        return Vect(self.vector)

    '''
      Calculates the polar coordinates of a 2-dimensional vector.
      :return The polar coordinates of a 2-dimensional vector;
      :raise Error if the vector is not 2-dimensional.
    '''

    def toPolar(self) -> int:
        if (self.__vector().dim() == 2):
            return [self.__vector().magnitude(), self.__vector().angle()]
        else:
            print("Vector must be 2-dimensional.")
            raise NameError("Error: vector must be 2d")
    '''
      Calculates the spherical coordinates of a 3-dimensional vector.
      :return The spherical coordinates of a 3-dimensional vector.
      :raise Error if the vector is not 3-dimensional.
    '''

    def toSpherical(self) -> list:
        if self.__vector().dim() == 3:
            theta = math.cos(
                self.__vector().vector[2] / self.__vector().magnitude())
            phi = math.atan2(
                self.__vector().vector[1], self.__vector().vector[0])
            return [self.__vector().magnitude(), theta, phi]
        else:
            print("Vector must be 3-d")
            raise NameError("Error: Vector 3d needed")
    '''
      Calculates the cylindrical coordinates of a 3-dimensional vector.
      :return The cylindrical coordinates of a 3-dimensional vector.
      :raise Error if the vector is not 3-dimensional.
    '''

    def toCylindrical(self) -> int:
        if self.__vector().dim() == 3:
            rho = math.sqrt(
                pow(self.__vector().vector[0], 2) + pow(self.__vector().vector[1], 2))
            theta = math.atan2(
                self.__vector().vector[1], self.__vector().vector[0])
            return [rho, theta, self.__vector().vector[2]]
        else:
            print("Vector must be 3-dimensional")
            raise NameError("Error: Vector must be 3-dimensional")
