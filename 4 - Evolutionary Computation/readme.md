# Simulated Annealing

Things we must think about:

## 1. Initial population

* 50% Voraz initialization (Selecting the first component using random)
* 50% Random initialization

## 2. Selection

We'll use binary tournament

## 3. Cross

We'll use order crossover which don't needs reparation

* F1: {1 2 3 4 5 6 7 8 9}
* F2: {9 3 7 8 2 6 5 1 4}
* Op: {X X X _ _ _ _ X X}

Results in:

* S1: {_ _ _ 4 5 6 7 _ _ } -> {3 8 2 4 5 6 7 1 9}
* S2: {_ _ _ 8 2 6 5 _ _ } -> {3 4 7 8 2 6 5 9 1}

## 4. Mutation

We'll use mutual interchange so two components randomly changes their position with the probability of 0.01 which means about 1 by individual using n=100

## 5. Substitution

We'll keep only the two best individuals of a population and replace the rest.

## 6. When to stop

We'll stop in 1000 generations


# Mejorando

Posibles mejoras:

 ## 1. Número de individuos

Esta posibilidad no se exploró puesto que haría la ejecución más lenta, no obstante, aumentaría las probabilidades de mejora, pero requeriría más poder de cómputo

## 2. Operador de inicialización

El operador de inicialización también se considera correcto puesto que proporciona una aleatoriedad que no se obtendría utilizando únicamente la solución voraz.

## 3. Operador de selección

El operador de selección también parece adecuado y difícilmente influye en la solución final del problema

## 4. Operaciones de cruce o mutación
Podríamos aumentar la frecuencia con la que ocurren estas operaciones pero también hemos decidido dejarlo así para conseguir el equilibrio entre explotación y exploración.

## 5. Mecanismo de reemplazo

El mecanismo de reemplazo no se ha modificado aunque se ha barajado aumentar el elitismo para mantener el mayor número posible de soluciones mejores.

## 6. Criterio de parada
Tampoco se cambiará este aspecto puesto que realmente no mejora la solución sino que haría que el algoritmo acabase antes y ese no es el objetivo que perseguimos

## 7. Introducción de estrategia de reinicialización
Básicamente esta es la medida que se ha explorado, se mantiene una variable que es la media de costes de las soluciones, en el caso de que la media no mejore durante 50 iteraciones se reiniciará la población usando aquellas soluciones que sean mejores a la mejor media y se duplicarán una y otra vez.

El objetivo de esto es explotar más las mejores soluciones que se han creado.
