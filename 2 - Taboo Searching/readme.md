# Taboo Searching

We'll switch to java because python starts to be slow and I don't want to expend my time in optimizations. Almost all the code is the same as the Local Searching but now we'll have a taboo list.

## 1. Taboo List

Will store movements and will have a lenght of n elements so when the list is full will lost the most old element.

## 2. Reinitialization

If we go for 100 iterations without obtaining a better solution the algotirhm will go back to the best solution.

# Citius, Altus, Fortus

## 1. Initial solution

For the initial solution we'll use a voraz initialization

## 2. Reinitialization

We'll store the taboo table to restore it in reinitializations
