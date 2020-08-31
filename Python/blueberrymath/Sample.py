from typing import *
from Stats import Stats

class Sample():
    def __init__(self, sample_elements):
        sample_elements = sort(sample_elements)
        #Property assigments
        self.sample = sample_elements
        self.min = self.sample[0]
        self.max = self.sample[len(self.sample) -1]

        '''
        Calculates the sum of a sample
        :return The sum of the sample
        '''
    def sum(self):
        return Stats.sum(self.sample)

        '''
        Calculates the mean of a sample.
        :return The mean of the sample
        '''
    def mean(self):
        return Stats.mean(self.sample)

        '''
        Calulates the median of a sample
        :return The median of a sample
        '''
    def median(self):
        return Stats.median(self.sample)

        '''
        Calculates the mode of a sample
        :return A list containing each element of the mode of the sample
        '''
    def mode(self):
        return Stats.mode(self.sample)

        '''
        Generates a frecuency table in the form of a hash map
        with the frecuency of the elements in the sample
        :return A hash map containing the frecuency of each element in the sample
        '''
    def frecuency(self):
        return Stats.frecuency(self.sample)

        '''
        Calculates the range of a sample
        :return The range pf the sample
        '''
    def range(self):
        return Stats.range(self.sample)

        '''
        Computes the variance of a sample
        As defined in Wikipedia, informally, variance is measure of
        how far a set of numbers is spread out from their avarage value
        :return The variance of the sample
        '''
    def variance(self):
        return Stats.variance(self.sample)

        '''
        Calulates the (unbiased) standard derivation of a sample
        :return The standard derivation of a sample
        '''

    def stdDerivation(self):
        return Statas.sampleStdDerivation(self.sample)

        '''
        Calulates the quartiles of a sample.
        :return A HashMap containing the quartiles (q1, q2, q3) of a sample
        '''

    def quartiles(self):
        return Stats.quartiles(self.sample)

        '''
        Calulates the inner quartiles range of a sample.
        :return The inner quartiles range of a sample
        '''

    def IQR(self):
        return Stats.IQR(self.sample)

        '''
        Calulates the outliers of a sample.
        :return A list containing the outliers of a sample
        '''

    def outliers(self):
        return Stats.outliers(self.sample)

        '''
        Calculates the probability of finding the element n in the sample.
        :param n The elemeny expected element to be found in the sample.
        :return The probability of finding the element n in the sample.
        '''

    def prob(self, n):
        if len(n) > 1:
            totalProd = 0
            # Adds up the probabilities of finding each of the element
            # of the set individually.
            for element in n:
                totalProd += self.prob(element)
                return totalProd
        else:
            nOfCurrences = 0
            for element in self.sample:
                if element == n[0]:
                    nOfCurrences+=1
            return nOfCurrences / len(self.sample)
