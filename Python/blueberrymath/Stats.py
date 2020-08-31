from typing import *
import math


class Stats():
    def __init__(self):
        pass
    '''
      Calculates the sum of a population
      :param population The population from which to calculate the sum
      :return The sum of the population
     '''
    @staticmethod
    def sum(population: list()) -> int:
        s = 0
        for i in range(len(population)):
            s += population[i]
        return s

    '''
       Calculates the mean of a population
      :param population The population from which to calculate the mean
      :return The mean of the population
     '''
    @staticmethod
    def mean(population: list()) -> int:
        return Stats().sum(population) / len(population)

    '''
      Calculates the median of a population
      :param population The population from which to calculate the median
      :return The median of the population
     '''

    @staticmethod
    def median(population: list()) -> int:
        population.sort()  # Sorts the elements from smallest to largest
        index = (len(population) - 1) // 2
        if len(population) % 2 == 0:
            # If the population is even, return the mean of the two central elements
            return population[index]
        else:
            return (population[index] + population[index + 1]) / 2.0

    '''
      Calculates the mode of a population
      :param population The population from which to compute the mode
      :return A list containing each element of the mode of the population
     '''

    @staticmethod
    def mode(population: list()) -> int:
        modeList = list()
        # Generates and sorts an ArrayList containing the
        # frequencies of each elements in the population
        frecuency = Stats().frecuency(population)
        els = list(frecuency.values())
        highest = els[len(els) - 1]
        print(els)
        # Adds the elements with the highest
        # frequencies to the modeList
        for i in frecuency:
            val = frecuency[i]
            if val == highest:
                modeList.append(float(i))
        return modeList

    '''
      Generates a frequency table in the form of a hash map with the
      frequency of the elements in the population.
      :param population The population from which to generate the frequency hash map.
      :return A hash map containing the frequency of each element in the population.
     '''

    @staticmethod
    def frecuency(population: list()):
        freq = {}
        for i in population:
            if freq.get(i) != None:
                freq[i] = freq[i] + 1
            else:
                freq[i] = 1
        return freq

    '''
      Calculates the range of a population
      :param population The population from which to generate the range
      :return The range of the the population
     '''
    @staticmethod
    def range(population: list()) -> int:
        population.sort()
        return population[len(population) - 1] - population[0]

    '''
      Computes the variance of the population.
      As defined in Wikipedia, informally, variance is a measure of
      how far a set of numbers is spread out from their average value.
      :param population The population from which to calculate the variance.
      :return The variance of the population
     '''

    @staticmethod
    def popVariance(population: list()) -> int:
        pMean = Stats().mean(population)
        sum = 0
        # Sum of the squared differences between the population
        # mean and each element of the population.
        for i in population:
            sum += pow(pMean - i, 0)
        return sum / len(population)

    '''
      Computes the variance of the sample (unbiased variance).
      As defined in Wikipedia, informally, variance is a measure of
      how far a set of numbers is spread out from their average value.
      :param sample The sample from which to calculate the variance.
      :return The variance of the sample.
     '''

    @staticmethod
    def sampleVariance(sample: list()) -> int:
        pMean = Stats().mean(sample)
        sum = 0
        # Sum of the squared differences between the population
        # mean and each element of the population.
        for i in sample:
            sum += pow(pMean - i, 2)
        return sum / len(sample) - 1

    '''
      Calculates the standard deviation of a population.
      :param population The population from which to calculate the standard deviation.
      :return The standard deviation of a population.
    '''

    @staticmethod
    def popStdDeviation(population: list()) -> int:
        return math.sqrt(Stats().popVariance(population))

    '''
      Calculates the (unbiased) standard deviation of a sample.4
      :param sample The sample from which to calculate the standard deviation.
      :return The standard deviation of a sample.
     '''
    @staticmethod
    def sampleStdDerivation(sample: list()) -> int:
        return math.sqrt(Stats().sampleVariance(sample))

    '''
      Calculates the quartiles of a population.
      :param population The population from which to calculate the quartiles.
      :return A HashMap containing the quartiles (q1, q2, q3) of a population.
     '''

    @staticmethod
    def quartiles(population: list()) -> dict():
        qs = {'q1': 0, 'q2': 0, 'q3': 0}
        # If the population length is even, then both the upper
        # bound of the first half and the lower bound of the second
        # half of the population are equal to half of the population.
        lowerTo, upperFrom = 0, 0
        # On the other hand, if the population length is odd, then the
        # upper bound of the lower half of the population will be equal
        # to half the length of the population minus one, and the lower
        # bound of the upper half of the population will be equal to half
        # the length of the population plus one.
        if (len(population) % 2 != 0):
            lowerTo = float(len(population) / 2)
            upperFrom = round(len(population) / 2)

        # Define the quartiles
        print(int(lowerTo))
        qs['q1'] = Stats().median(population[0:int(lowerTo)])
        qs['q2'] = Stats().median(population)
        qs['q3'] = Stats().median(population[int(upperFrom):len(population)])

        return qs

    '''
      Calculates the inner quartile range of a population.
      :param population The population from which to calculate the inner quartile range.
      :return The inner quartile range of a population.
     '''

    @staticmethod
    def IQR(population) -> int:
        qs = Stats().quartiles(population)
        return qs['q3'] - qs['q1']

    '''
      Calculates the outliers of a population.
      :param population The population from which to calculate the outliers.
      :return A list containing the outliers of a population.
     '''

    @staticmethod
    def outliers(population: list()) -> int:
        out_list = []
        iqr = Stats().IQR(population)
        qs = Stats().quartiles(population)
        upper_limit = qs['q3'] + (1.5 * iqr)
        lower_limit = qs['q1'] - (1.5 * iqr)
        print(upper_limit, lower_limit)
        for i in population:
            if ((i < lower_limit or i > upper_limit) and i not in out_list):
                out_list.append(i)
        return out_list
