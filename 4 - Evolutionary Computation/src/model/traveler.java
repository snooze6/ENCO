package model;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static model.solution.generateRandSol;
import static model.solution.generateVorazSol;

/**
 * Created by Snooze on 06/12/2016.
 */
public class traveler {

    // General
    int first;
    int n;
    matrix dist;
//    Scanner scanner = null;

    // Computación genética
    solution sol;
    individual[] population = new individual[100];

    public traveler(int first, matrix dist, int n) {
        this.first = first;
        this.n = n;
        this.dist = dist;
    }

    public traveler setSol(solution sol) {
        this.sol = sol;
        return this;
    }

    public void initPopulation(){
        solution asol;
        for (int i = 0; i < 50; i++){
            // Completamente random
            asol = generateRandSol(n);
            population[i] = new individual(i, asol, dist.getCost(asol),0);
            System.out.println(population[i].toString());
        }
        for (int i = 50; i < 100; i++){
            // Voraz
            asol = generateVorazSol(n, dist);
            population[i] = new individual(i, asol, dist.getCost(asol), 0);
            System.out.println(population[i].toString());
        }

        // TODO Tampoco me deja ordenarla al principio, mecagoentó
//        ArrayList<individual> newpopulation = new ArrayList<>();
//        for (individual aPopulation : population) newpopulation.add(aPopulation);
//
//        newpopulation.sort(new Comparator<individual>() {
//            @Override
//            public int compare(individual o1, individual o2) {
//                return (o1.cost!=o2.cost) ? (int) (o1.cost - o2.cost) : (o1.generated - o2.generated);
//            }
//        });
//
//        for (int k=0; k<newpopulation.size(); k++)
//            population[k] = newpopulation.get(k);

    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public Point2D.Float doit() throws InterruptedException {
//        System.out.println("MATRIZ DE DISTANCIAS");
//        this.dist.print();

//        try {
//            System.setOut(new PrintStream(new File("output-file.txt")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println();
        System.out.println("POBLACION INICIAL");
        initPopulation();

        individual best1 = null, best2 = null, very_best = null;
        best1 = new individual(0,new solution(n),Integer.MAX_VALUE, Integer.MAX_VALUE);
        best2 = new individual(0,new solution(n),Integer.MAX_VALUE, Integer.MAX_VALUE);
        // Getting the very best
        for (individual in : population) {
            if ((in.cost < best2.cost) || ((in.cost == best2.cost) && (in.generated < best2.generated))) {
                if ((in.cost < best1.cost) || ((in.cost == best1.cost) && (in.generated < best1.generated))) {
                    best1 = in;
                } else {
                    best2 = in;
                }
            }
        }
        very_best = best1.clone();

        for (int i=0; i<1000; i++){
            System.out.println();
            System.out.println("ITERACION: "+(i+1)+", SELECCION");

            // GRAND TOURNAMENT
            ArrayList<individual> newpopulation = new ArrayList<>();
            for (int j=0; j<population.length-2; j++){
                int a = (int)(random.getRand() * population.length), b = (int)(random.getRand() * population.length);
                int winner = (population[a].cost>population[b].cost) ? b : a;
                System.out.println("\tTORNEO "+j+": "+a+" "+b+" GANA "+winner);
                newpopulation.add(population[winner]);
            }

            System.out.println();
            System.out.println("ITERACION: "+(i+1)+", CRUCE ");

            // ORGY
            for (int j=0; j<(population.length / 2)-1; j++){
                float rand = random.getRand();
                int ia = (j*2), ib = ((j*2)+1);
                System.out.println("\tCRUCE: ("+ia+", "+ib+") (ALEATORIO: "+String.format(Locale.ROOT, "%.6f", rand)+")");

                individual father1 = newpopulation.get(ia).clone(), father2 = newpopulation.get(ib).clone();
                System.out.println("\t\tPADRE: = {FUNCION OBJETIVO (km): "+(int)father1.cost+", RECORRIDO: "+father1.solution.toString()+"}");
                System.out.println("\t\tPADRE: = {FUNCION OBJETIVO (km): "+(int)father2.cost+", RECORRIDO: "+father2.solution.toString()+"}");

                if (rand<=0.9){
                    int a = (int)(random.getRand() * n), b = (int)(random.getRand() * n);
                    System.out.println("\t\tCORTES: ("+a+", "+b+")");

                    solution sol1 = new solution(n), sol2 = new solution(n);
                    int max = a > b ? a : b,  min = a < b ? a : b;
                    for (int k=0; k<min; k++){
                        sol1.put(0);
                        sol2.put(0);
                    }
                    for (int k=min; k<=max; k++){
                        sol1.put(father1.solution.get(k));
                        sol2.put(father2.solution.get(k));
                    }
                    int mark1 = (max+1) % n , mark2 = (max+1) % n;
                    for (int k=max+1; k<n; k++){
                        mark1 = (mark1 + sol1.put(father2.solution.get(k), mark1)) % n;
                        mark2 = (mark2 + sol2.put(father1.solution.get(k), mark2)) % n;
                    }
                    for (int k=0; k<=max; k++){
                        mark1 = (mark1 + sol1.put(father2.solution.get(k), mark1)) % n;
                        mark2 = (mark2 + sol2.put(father1.solution.get(k), mark2)) % n;
                    }

                    individual son1 = father1, son2 = father2;
                        son1.generated = i+1;
                        son2.generated = i+1;
                    son1.solution = sol1; son2.solution = sol2; son1.cost = dist.getCost(sol1); son2.cost = dist.getCost(sol2);
                    System.out.println("\t\tHIJO: = {FUNCION OBJETIVO (km): "+(int)son1.cost+", RECORRIDO: "+son1.solution.toString()+"}");
                    System.out.println("\t\tHIJO: = {FUNCION OBJETIVO (km): "+(int)son2.cost+", RECORRIDO: "+son2.solution.toString()+"}");
                    System.out.println();
                    newpopulation.set(ia, son1);
                    newpopulation.set(ib, son2);
                } else {
                    System.out.println("\t\tNO SE CRUZA");
                    System.out.println();
                    newpopulation.set(ia, father1);
                    newpopulation.set(ib, father2);
                }
            }

            // RADIOACTIVITY
            System.out.println("ITERACION: "+(i+1)+", MUTACION"); int c = 0;
            for (individual ind: newpopulation){
                boolean b = false;
                System.out.println("\tINDIVIDUO "+c); c++;
                System.out.println("\tRECORRIDO ANTES: "+ind.solution.toString());
                ArrayList<Integer> arr = ind.solution.solucion;
                float rand;
                for (int k=0; k<arr.size(); k++){
                    rand = random.getRand();
                    if (rand<=0.01){
                        int in = (int) (random.getRand()*n);
                        System.out.println("\t\tPOSICION: "+k+" (ALEATORIO "+String.format(Locale.ROOT, "%.6f", rand)+") INTERCAMBIO CON: "+in);
                        b = true;
                        {
                            int aux = arr.get(in);
                            arr.set(in, arr.get(k));
                            arr.set(k, aux);
                        }
                    } else {
                        System.out.println("\t\tPOSICION: "+k+" (ALEATORIO "+String.format(Locale.ROOT, "%.6f", rand)+") NO MUTA");
                    }
                }
                System.out.println("\tRECORRIDO DESPUES: "+ind.solution.toString());
                System.out.println();

                if (b){
                    ind.cost = dist.getCost(ind.solution);
                }
            }

            System.out.println("ITERACION: "+(i+1)+", REEMPLAZO");

//            // TODO: Ordenar todo como a mi me de la gana
//            newpopulation.add(best1);
//            newpopulation.add(best2);

            newpopulation.sort(new Comparator<individual>() {
                @Override
                public int compare(individual o1, individual o2) {
                    return (int) (o1.cost - o2.cost);
                    // Ni esto me dejan hacer
//                    return (o1.cost!=o2.cost) ? (int) (o1.cost - o2.cost) : (o1.generated - o2.generated);
                }
            });

            newpopulation.add(0,best1.clone());
            newpopulation.add(0,best2.clone());

            for (int k=0; k<newpopulation.size(); k++){
                individual in = newpopulation.get(k);
                in.i = k;
                System.out.println(in.toString());
                population[k] = in;
            }

            // Getting the very best
            best1 = new individual(0,new solution(n),Integer.MAX_VALUE, Integer.MAX_VALUE);
            best2 = new individual(0,new solution(n),Integer.MAX_VALUE, Integer.MAX_VALUE);
            for (individual in : population) {
                if ((in.cost < best2.cost) || ((in.cost == best2.cost) && (in.generated < best2.generated))) {
                    if ((in.cost < best1.cost) || ((in.cost == best1.cost) && (in.generated < best1.generated))) {
//                        System.out.println("Old best1: "+best1.toString());
                        best2 = best1;
                        best1 = in;
//                        System.out.println("New best1: "+best1.toString());
                    } else {
//                        System.out.println("Old best2: "+best2.toString());
                        best2 = in;
//                        System.out.println("New best2: "+best2.toString());
                    }
                }
            }
            best1 = best1.clone(); best2 = best2.clone();
            if (best1.cost < very_best.cost){
                very_best = best1.clone();
            }

//            System.out.println("\tLO MEJOR:");
//            System.out.println("\t\t"+best1.toString());
//            System.out.println("\t\t"+best2.toString());

//            if (i>30-2)
//                break;
        }

        best1 = new individual(0,null, Integer.MAX_VALUE, Integer.MAX_VALUE);
        // TODO: Quitar esto cuando ordene como yo quiero
        for (individual in: population){
            best1 = (in.cost < best1.cost) || ((in.cost == best1.cost) && (in.generated < best1.generated)) ? in : best1;
        }
        System.out.println();
        System.out.println();
        System.out.println("MEJOR SOLUCION:");
        System.out.println("RECORRIDO: "+best1.solution.toString());
        System.out.println("FUNCION OBJETIVO (km): "+(int)best1.cost);
        System.out.println("ITERACION: "+best1.generated);

        return new Point2D.Float();
    }

}
