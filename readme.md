# Traveller salesman problem

## Introduction

The travelling salesman problem (TSP) asks the following question: "Given a list of cities and the distances between each pair of cities, what is the shortest possible route that visits each city exactly once and returns to the origin city?" It is an NP-hard problem in combinatorial optimization, important in operations research and theoretical computer science.

So the objective is to solve this problem with n cities and with "solve" I mean get an ok solution.

## Finite approach

For educational purpose we'll use a finite problem with 10 and 100 cities. Their distances will be:

The distances will be given in a triangular matrix like this:

|| city0   | city1  | city2  | city3  |
|---|---|---|---|---|
|city1| 1  | 2  | 3  | 4  |
|city2| 2  | 3  | 4  | 5  |
| city3 | 3 |   4|  5 |  6 |

In this way we don't have redundant information

## Random (Not so random)

In order to have the same executions over time and debug if the algorithm is working as expected we'll have a set of predefined random numbers that the program will be able to read from a file.

In fact the program must be called like this:

```bash
./program dist.txt [random.txt] > out
```

## Doing the thing

In this repo we are going to solve the traveller problem using diferent approaches like:

1. Local Searching - First Better
2. Taboo Searching
3. Simulated Annealing
4. Evolutionary Computation
