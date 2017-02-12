# Local searching

First things first we'll test the most simple of the solutions the local searching best-first, for doing this we have to:

## 1. Represent the solution

Something like an array:
{1,2,3,4,5,6,7,8,9}

As we always start and end in city 0 we won't represent this in the solution as is implicit

## 2. Generate an initial solution

We'll do it random way althought it's not the best

## 3. Generate a neighborhood

We have the two-elements change operator and it's ok so let's use it

## 4. Have a cost function

Should be easy to implement having the distances-matrix

## 5. Mecanism to choose the solution

We'll chose the first solution that is better than the actual solution

## Run it!

```bash
python main.py files/distancias_10.txt files/aleatorios_ls_2016.txt
python main.py files/distancias_test_BL.txt files/aleatorios_test_BL.txt
```

Got it!
