# Simulated Annealing

The same that the previous but using simulated annealing so we must think about:

## 1. Initial value of Temperature

Let's use this:
T = (μ / -ln(φ)) * cost(initial_solution)

## 2. Annealing mechanism

We'll use Cauchi annealing:

Tk = Ti/(1-k)

## 3. Annealing speed

We'll use two criteria:

1. 80 solutions generated
2. 20 solutions accepted

When one of the two is true then we lower the temperature

## 4. When to stop

When the 10000 iterations are completed
