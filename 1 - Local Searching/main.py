#!python3

import math
import os
import random
import statistics
import sys

from src.logger import console
from src.matriz import matriz, matriz_distancias


class traveler(object):
    def __init__(self, first, dist, filename=None):
        self.first = first
        self.dist = dist
        self.auxrand = None
        if filename is not None:
            self.loadrnd(filename)
        self.asol = self._genisol()
        self.isol = self.asol[:]
        self.hist = []
        self.rand = []
        self.arand = matriz(8)
        self.count = 0

    """
    Genera la solución dependiendo de si hay aleatorios o no
    """

    def _genisol(self):
        if self.auxrand is None:
            l = [1, 2, 3, 4, 5, 6, 7, 8, 9]
            random.shuffle(l)
            return l
        else:
            r = self.auxrand[0:9]
            ri = []
            for j in r:
                i = (math.floor(j * 9))
                while i + 1 in ri:
                    i = (i + 1) % 9
                ri.append(i + 1)
            self.auxrand = self.auxrand[9:]
            return ri

    def getCost(self):
        return self.dist.getCost(self.asol)

    def print(self):
        console.print('< Traveler >')
        console.print('First city:       ' + str(self.first))
        console.print('Initial solution: 0 -> ' + str(self.isol) + ' -> 0')
        console.print('Initial cost:     ' + str(self.dist.getCost(self.isol)))
        console.print('Actual solution:  0 -> ' + str(self.asol) + ' -> 0')
        console.print('Actual cost:      ' + str(self.getCost()))
        console.print('</Traveler >')

    def __str__(self):
        return 'SOLUCION S_' \
               + str(self.count) + ' -> ' \
               + str(self.asol) + '; ' + \
               str(self.getCost()) + 'km'

    def _getRand(self):
        if self.auxrand is None:
            ran2 = random.randint(0, 8)
            ran1 = random.randint(0, 8)
            while ran2 == ran1:
                ran2 = random.randint(0, 8)
            return ran1, ran2
        else:
            if (len(self.hist) * 2) + 2 > len(self.auxrand):
                return 0, 0
            r = self.auxrand[(len(self.hist) * 2):((len(self.hist) * 2) + 2)]
            ri = []
            for j in r:
                i = (math.floor(j * 9))
                ri.append(i)

            # Si uno es mayor que otro mal
            if ri[1] >= ri[0]:
                if ri[1] > ri[0]:
                    aux = ri[0]
                    ri[0] = ri[1]
                    ri[1] = aux
                else:
                    ri[0] = (ri[0] + 1) % 8
                    ri[1] = 0

            return ri[0], ri[1]

    def next(self):
        if self.auxrand and len(self.hist) > len(self.auxrand):
            return None

        ran1, ran2 = self._getRand()
        count = 0
        while self.arand.get(ran1, ran2):
            count += 1
            # ran1, ran2 = self._getRand()
            if ran2 + 1 < ran1:
                ran2 += 1
            else:
                if ran1 < 8:
                    ran2 = 0
                    ran1 += 1
                else:
                    ran2 = 0
                    ran1 = 0

            console.vrb(count)
            if count >= (36 - 1):
                return None

        self.rand.append([ran1, ran2])
        self.arand.set(ran1, ran2)
        tsol = self.asol[:]
        tsol[ran1] = self.asol[ran2]
        tsol[ran2] = self.asol[ran1]

        return tsol

    def doit(self):
        ret = None

        while True:
            console.log(str(self))
            ran1 = ran2 = 0
            while True:
                # time.sleep(1)
                ret = self.next()

                self.hist.append(ret)
                ran1 = self.rand[-1][0]
                ran2 = self.rand[-1][1]

                if self.dist.getCost(ret) is None:
                    break
                console.log('	VECINO V_' + str(len(self.rand) - 1)
                            + ' -> Intercambio: (' + str(ran1) + ', ' + str(ran2)
                            + '); ' + str(ret) + '; ' + str(self.dist.getCost(ret)) + 'km')
                if self.dist.getCost(ret) < self.dist.getCost(self.asol) and len(self.rand) < 36:
                    break

            console.log('')
            if self.dist.getCost(ret) is not None:
                self.count += 1
                if self.dist.getCost(ret) < self.dist.getCost(self.asol):
                    # console.log('Mejor que el anterior')
                    if not self.auxrand:
                        self.rand = [[ran1, ran2]]
                    else:
                        self.rand = []
                    self.arand = matriz(8)
                    self.asol = ret
                else:
                    # console.log('Igual, entonces mal')
                    break
            else:
                # console.log('Salir')
                break

        console.log(str(self))

    def loadrnd(self, filename):
        ret = True
        self.auxrand = []
        console.vrb('< Loading randoms: ' + filename + ' >')
        try:
            f = open(filename)
            content = f.readlines()
            for line in content:
                self.auxrand.append(float(str(line)))
            for j in self.auxrand:
                console.vrb(j)
        except IOError:
            console.vrb(' + Error loading file')
            ret = False
        console.vrb('</Loading randoms: ' + filename + ' >')
        return ret


def doit(distances, random, verbose):
    if verbose > 1:
        console.toggleDebug(True)
    if verbose > 2:
        console.toggleVerbose(True)

    m = matriz_distancias()
    console.vrb('Loaded: ' + str(m.load(distances)))
    if random:
        t = traveler(0, m, random)
    else:
        t = traveler(0, m)

    t.doit()
    t.print()


def doitbatch(distances, random):
    res = []
    for j in range(0, 10):
        m = matriz_distancias()
        console.vrb('Loaded: ' + str(m.load(distances)))
        if random:
            t = traveler(0, m, random)
        else:
            t = traveler(0, m)

        t.doit()
        console.print(t.getCost())
        res.append(t.getCost())

    console.print('Deviation: '+str(statistics.stdev(res)))
    console.print('Mean:      '+str(statistics.mean(res)))


if __name__ == '__main__':
    distances = None
    randoms = None
    verbose = 0
    # print(sys.argv)
    if len(sys.argv) > 1 and os.path.isfile(sys.argv[1]):
        distances = sys.argv[1]
    else:
        console.print(' + usage: [python3] main.py distancias.txt [aleatorios.txt]')
        console.print('     distancias are required')
        sys.exit(0)
    if len(sys.argv) > 2:
        if sys.argv[2].count('v') == len(sys.argv[2]):
            verbose = sys.argv[2].count('v')
        else:
            if os.path.isfile(sys.argv[2]):
                randoms = sys.argv[2]
            else:
                console.print(' + usage: [python3] main.py distancias.txt [aleatorios.txt]')
                console.print('     Bad aleatorios argument can\'t access file')
                sys.exit(0)
    if len(sys.argv) > 3:
        verbose = sys.argv[3].count('v')

    doit(distances, randoms, verbose)
    # doitbatch(distances, randoms)
