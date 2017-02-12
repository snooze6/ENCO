package model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Snooze on 06/12/2016.
 */
public class traveler {

    solution sol;
    int first;
    int n;
    matrix dist;
    ArrayList<change> tabu = new ArrayList<>();

    public traveler(int first, matrix dist, int n) {
        this.first = first;
        this.n = n;
        this.dist = dist;
    }

    public traveler setSol(solution sol) {
        this.sol = sol;
        return this;
    }

    public float getCost(){
        return dist.getCost(sol);
    }

    public solution initSol(int n, Scanner s){
        solution sol = new solution(n);
        for (int i = 0; i < n; i++) {
            double t;
            if (s!=null)
                t = Double.parseDouble(s.nextLine());
            else
                t = Math.random();
//            System.out.println(str + " -> " + t);
            int v = (int) (1.0D + Math.floor(t * 99.0D));
            sol.push(v);
        }
        return sol;
    }

    public solution initSol(int n){
        solution sol = new solution(n);
        for (int i = 0; i < n; i++) {
            int v = ThreadLocalRandom.current().nextInt(1, 100);
            sol.push(v);
        }
        return sol;
    }

    public Point2D.Float doit(){
        System.out.println("RECORRIDO INICIAL");
        System.out.println("\tRECORRIDO: "+sol.toString());
        System.out.println("\tCOSTE (km): "+dist.getCost(sol));

        tabu = new ArrayList<>();
        int nobetter = 0, reset = 0, backupcost = dist.getCost(sol), backupit = 0;
        solution backupsol = sol;

        timer t = new timer().start();

        for (int i=0; i<=(10000-1); i++){
            int bestcost = -1; solution bestsol = null; change bestswitch = null;

            for (int j = 0; j < this.n; j++) {
                for (int k = 0; k < j; k++) {
                    change aswitch = new change(j,k);
                    if (!tabu.contains(aswitch)) {
                        solution asol = sol.permute(j, k);
                        int acost = dist.getCost(asol);
                        if (bestsol == null || acost < bestcost){
                            bestsol = asol;
                            bestcost = acost;
                            bestswitch = aswitch;
                        }
                    }
                }
            }

            if (bestcost >= backupcost){
                nobetter++;
            } else {
                nobetter = 0; backupsol = bestsol; backupcost = bestcost; backupit = i;
            }

            this.sol = bestsol;
            this.tabu.add(bestswitch);
            if (tabu.size() > n+1){
                tabu.remove(0);
            }

            System.out.println("\nITERACION: "+(i+1));
            System.out.println("\tINTERCAMBIO: ("+bestswitch.x+", "+bestswitch.y+")");
            System.out.println("\tRECORRIDO: "+bestsol.toString());
            System.out.println("\tCOSTE (km): "+bestcost);
            System.out.println("\tITERACIONES SIN MEJORA: "+nobetter);
            System.out.println("\tLISTA TABU:");
            for (change aTabu : tabu) {
                System.out.println("\t" + aTabu.x + " " + aTabu.y);
            }

            if (nobetter>n){
                reset++; tabu.clear(); nobetter=0; sol = backupsol;
                System.out.println("\n***************");
                System.out.println("REINICIO: "+reset);
                System.out.println("***************");
            }
        }

        System.out.println("\nMEJOR SOLUCION: ");
        System.out.println("\tRECORRIDO: "+backupsol.toString());
        System.out.println("\tCOSTE (km): "+backupcost);
        System.out.println("\tITERACION: "+(backupit+1));

        t.end().print();

        return new Point2D.Float(backupcost, backupit+1);
    }

}
