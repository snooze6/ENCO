package model;

import java.awt.geom.Point2D;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Snooze on 06/12/2016.
 */
public class better_traveller extends traveler{

    public better_traveller(int first, matrix dist, int n) {
        super(first, dist, n);
    }

    public better_traveller(int first, matrix dist, int n, Scanner s) {
        super(first, dist, n, s);
    }

    @Override
    public void voraz(int n, solution sol) {
        sol.push(ThreadLocalRandom.current().nextInt(1,n+1));
        for (int i = 1; i < n+1; i++) {
            int prev;
            if (sol.solucion.size()>0)
                prev = sol.get(sol.solucion.size()-1);
            else
                prev = 0;

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
    public Point2D.Float doit() throws InterruptedException {
        this.calc();
        System.out.println("RECORRIDO INICIAL");
        System.out.println("\tRECORRIDO: "+sol.toString());
        System.out.println("\tFUNCION OBJETIVO (km): "+cost);
        System.out.printf("\tTEMPERATURA INICIAL: %.6f\n",temp);
        System.exit(0);
        return null;
    }
}
