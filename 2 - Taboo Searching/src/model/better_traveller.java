package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Snooze on 06/12/2016.
 */
public class better_traveller extends traveler{

    matrix hist;

    public better_traveller(int first, matrix dist, int n) {
        super(first, dist, n);
        hist = new matrix(n);
    }

    public solution initSol(int n, Scanner s){
        solution sol = new solution(n);
        double t = Double.parseDouble(s.nextLine());
        int v = (int) (1.0D + Math.floor(t * 99.0D));
        sol.push(v);
        voraz(n, sol);
        return sol;
    }

    public solution initSol(int n){
        solution sol = new solution(n);
        int v = ThreadLocalRandom.current().nextInt(1, 100);
        sol.push(v);
        voraz(n, sol);
        return sol;
    }

    private void voraz(int n, solution sol) {
        for (int i = 1; i < n; i++) {
            int prev = sol.get(sol.solucion.size()-1);
            int coste = -1;
            int min = -1;

            for (int j = 1; j < n+1; j++) {
                if (j!=prev && !sol.solucion.contains(j)){
                    if (dist.get(prev,j) < coste || coste==-1) {
                        coste = dist.get(prev, j);
                        min = j;
                    }
                }
            }

            sol.push(min);
        }
    }

    @Override
    public Point2D.Float doit() {
        System.out.println("RECORRIDO INICIAL");
        System.out.println("\tRECORRIDO: "+sol.toString());
//        System.out.println("\tLONGITUD: "+sol.solucion.size());
        System.out.println("\tCOSTE (km): "+dist.getCost(sol));

        tabu = new ArrayList<>();
        int nobetter = 0, reset = 0, backupit = 0;
        float backupcost = dist.getCost(sol);
        ArrayList<change> backuptabu = new ArrayList<>();
        solution backupsol = sol;

        timer t = new timer().start();

        for (int i=0; i<=(10000-1); i++){
            float bestcost = -1; solution bestsol = null; change bestswitch = null;

            for (int j = 0; j < this.n; j++) {
                for (int k = 0; k < j; k++) {
                    change aswitch = new change(j,k);
                    if (!tabu.contains(aswitch)) {
                        solution asol = sol.permute(j, k);
                        float acost = dist.getCost(asol);
//                        float acost = dist.getCost(asol, hist);
                        if (bestsol == null || acost < bestcost){
                            bestsol = asol;
                            bestcost = acost;
                            bestswitch = aswitch;
                        }
                    }
                }
            }

            this.sol = bestsol;
            this.tabu.add(bestswitch);
            hist.set(bestswitch.x, bestswitch.y, hist.get(bestswitch.x, bestswitch.y)+1);
            if (tabu.size() > n+1){
                tabu.remove(0);
            }

            if (bestcost >= backupcost){
                nobetter++;
            } else {
                nobetter = 0; backupsol = bestsol; backupcost = bestcost; backupit = i;
                backuptabu = (ArrayList<change>) this.tabu.clone();
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
                reset++; nobetter=0; sol = backupsol;
                tabu = backuptabu;
//                tabu.clear();
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
        hist.print();
        System.out.println("\tMAX: "+hist.getMax());

        return new Point2D.Float(backupcost, backupit+1);
    }
}
