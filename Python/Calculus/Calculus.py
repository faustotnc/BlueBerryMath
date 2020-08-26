from typing import *


class Calculus():

    def __init__(self):
        pass
    '''
    Compute the instantaneous rate of change at x
    using the slope of the target line that passes through two points of the function f with h=0.0000000001.
    :param x The x-value at which the instantaneous rate of change will be calculated.
    :param f The function to calculate the instantaneous rate of change.
    :return The instantaneous rate of change at x for the function f.
    '''
    @staticmethod
    def nDeriv(x: float, f: float) -> float:
        h = 0.0000000001
        return (f * (x + h) - f * (x - h)) / (2 * h)

    '''
      Computes the area under a curve in the xy-plane
      using the method of trapezoidal approximation.
      :param l The lower limit of the integral
      :param u The upper limit of the integral
      :param f The function whose area will be approximated
      :return The approximate area under the curve f from x=l to x=u
     '''

    @staticmethod
    def fnInt(l: float, u: float, f: float) -> float:
        if ((u - l) == 0):
            return 0
        # The number of trapezoids is dynamically
        # adjusted based in the size of the interval
        h = round(100000 * (u - l)) + 100000
        # Total computed
        area = 0
        #Change in x
        deltax = (u - l) / h
        # starting x-value
        x = l

        # Calulate the are of each trapezoid and
        # aggregate it to the total calculated aggregate
        i = 0
        while i <= h:
            area += (f * (x) + f * (x + deltax) / 2) * deltax
            # The next x-value
            x += deltax
            i += 1
        return area

    '''
    Checks if a function is increasing on an interval [a, b].
    :param a The lower bound of the interval.
    :param b The upper bound of the interval.
    :param f The function to be tested.
    :return True if the function is monotone increasing on [a, b], false otherwise.
    '''

    @staticmethod
    def isIncreasing(a: float, b: float, f: float) -> bool:
        testN = b - a
        currentTest = 0

        while currentTest <= testN:
            # If the derivative is less than zero for any x in
            # the interval [a, b], then the function would be decreasing,
            # so we return false.
            if (Calculus().nDeriv(a + currentTest, f) < 0):
                return False
            currentTest += 1.0 / 300  # Perfroms 300 tests per unit destance
        return True

    '''
      Checks if a function is decreasing on an interval [a, b].
      param: a The lower bound of the interval.
      param: b The upper bound of the interval.
      param: f The function to be tested.
      return: True if the function is monotone decreasing on [a, b], false otherwise.
     '''
    @staticmethod
    def isDecreasing(a: float, b: float, f: float) -> bool:
        testN = b - a
        currentTest = 0

        while currentTest <= testN:
            # If the derivative is less than zero for any x in
            # the interval [a, b], then the function would be decreasing,
            # so we return false.
            if (Calculus().nDeriv(a + currentTest, f) > 0):
                return False
            currentTest += 1.0 / 300  # Perfroms 300 tests per unit destance
        return True
