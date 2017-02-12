#!python3

from .logger import console


class matriz(object):
    def __init__(self, n=None):
        self.m = []
        if n:
            for j in range(0, n):
                ds = [False for s in range(0, j + 1)]
                self.m.append(ds)
                # self.print()

    def print(self):
        for j in self.m:
            console.log(j)

    def set(self, i, j):
        if j > i:
            self.set(j, i)
        else:
            if j != i:
                self.m[i - 1][j] = True

    def get(self, i, j):
        if j > i:
            return self.get(j, i)
        else:
            if j == i:
                return True
            else:
                return self.m[i - 1][j]


class matriz_distancias(matriz):
    def get(self, i, j):
        if j > i:
            return self.get(j, i)
        else:
            if j == i:
                return 0
            else:
                return self.m[i - 1][j]

    def getCost(self, sol):
        if sol:
            suma = 0
            suma += self.get(0, sol[0])
            for i in sol:
                j = sol[sol.index(i) + 1] \
                    if (sol.index(i) + 1) < len(sol) else None
                if j:
                    suma += self.get(i, j)
            suma += self.get(0, sol[-1])
            return suma
        else:
            return None

    def load(self, filename):
        ret = True
        console.vrb('< Loading file: ' + filename + ' >')
        try:
            f = open(filename)
            content = f.readlines()
            for line in content:
                ds = [int(s) for s in line.split() if s.isdigit()]
                self.m.append(ds)
            self.print()
        except IOError:
            console.vrb(' + Error loading file')
            ret = False
        console.vrb('</Loading file: ' + filename + ' >')
        return ret
