#!/usr/bin/env bash
rm *.out

python "1 - Local Searching/main.py" "1 - Local Searching/files/distancias_test_BL.txt" "1 - Local Searching/files/aleatorios_test_BL.txt" > 1.out
java -jar "2 - Taboo Searching/main.jar" "2 - Taboo Searching/files/distancias_ts_100_2016_test.txt" "2 - Taboo Searching/files/aleatorios_ts_2016_test.txt" > 2.out
java -jar "3 - Simulated Annealing/main.jar" "3 - Simulated Annealing/files/distancias_sa_100_2016_test.txt" "3 - Simulated Annealing/files/aleatorios_sa_2016_test.txt" > 3.out
java -jar "4 - Evolutionary Computation/main.jar" "4 - Evolutionary Computation/files/distancias_ce_10_2016_test.txt" "4 - Evolutionary Computation/files/aleatorios_ce_10_2016_test.txt" > 4.out