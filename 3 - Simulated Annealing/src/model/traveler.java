package model;

import java.awt.geom.Point2D;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Snooze on 06/12/2016.
 */
public class traveler {

    // General
    int first;
    int n;
    matrix dist;
    Scanner scanner = null;

    // Enfríamiento simulado
    solution sol;
    int cost;
    double temp;
    double µ = 0.01, φ = 0.5;

    public traveler(int first, matrix dist, int n) {
        this.first = first;
        this.n = n;
        this.dist = dist;
    }

    public traveler(int first, matrix dist, int n, Scanner s) {
        this(first, dist, n);
        this.scanner = s;
    }

    public traveler setSol(solution sol) {
        this.sol = sol;
        return this;
    }

    public float getCost(){
        return dist.getCost(sol);
    }

    public solution initSol(int n, Scanner s){
        return initSol(n);
    }

    public solution initSol(int n){
        solution sol = new solution(n);
        voraz(n, sol);
        return sol;
    }

    public void voraz(int n, solution sol) {
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

    private float getRand() {
        float ret = -1;
        if (scanner!=null)
            ret = Float.parseFloat(scanner.nextLine());
        if (ret == -1)
            ret = (float) ThreadLocalRandom.current().nextDouble(0,1);
        return ret;
    }

    public void calc(){
        cost = dist.getCost(sol);
        temp = (-µ/Math.log(φ)) * cost;
    }

    public Point2D.Float doit() throws InterruptedException {
        this.calc();
        System.out.println("RECORRIDO INICIAL");
        System.out.println("\tRECORRIDO: "+sol.toString());
        System.out.println("\tFUNCION OBJETIVO (km): "+cost);
        System.out.printf("\tTEMPERATURA INICIAL: %.6f\n",temp);

        int tested = 0, accepted=0, cooling = 0, backupcost=-1, backupit = -1;
        solution backupsol = null;

        timer t = new timer().start();

        for (int i=0; i<=(10000-1); i++){
            //while (temp>=tempf)
            int bestcost = -1; solution bestsol = null; change bestswitch = null;

            // Generación vecindario
            for (int j = 0; j < this.n; j++) {
                for (int k = 0; k < j; k++) {
                    change aswitch = new change(j,k);
                    solution asol = sol.permute(j, k);
                    int acost = dist.getCost(asol);
                    // System.out.println("\tINTERCAMBIO: ("+aswitch.x+", "+aswitch.y+") - COSTE: "+acost+" - RECORRIDO: "+asol.toString());
                    if (bestsol == null || acost < bestcost){
                        bestsol = asol;
                        bestcost = acost;
                        bestswitch = aswitch;
                    }
                }
            }

            // Decidimos si nos quedamos o no con la solución
            int delta = bestcost - cost;
            double eval = Math.exp(-delta/temp);

            System.out.println("\nITERACION: "+(i+1));
            System.out.println("\tINTERCAMBIO: ("+bestswitch.x+", "+bestswitch.y+")");
            System.out.println("\tRECORRIDO: "+bestsol.toString());
            System.out.println("\tFUNCION OBJETIVO (km): "+bestcost);
            System.out.println("\tDELTA: "+delta);
            System.out.printf("\tTEMPERATURA: %.6f\n",temp);
            System.out.printf("\tVALOR DE LA EXPONENCIAL: %.6f\n", eval);
            if (getRand() < eval || delta < 0) {
                this.sol = bestsol.clone(); accepted++;
                System.out.println("\tSOLUCION CANDIDATA ACEPTADA");
                if (backupcost > bestcost || backupsol==null){
                    backupit = i; backupcost = bestcost; backupsol = bestsol.clone();
//                    System.out.println("\tSOLUCION CANDIDATA GUARDADA");
                }
                this.cost = bestcost;
            } else {
                tested++;
            }
            System.out.println("\tCANDIDATAS PROBADAS: "+(tested+accepted)+", ACEPTADAS: "+accepted);

            if (tested+accepted > 79 || accepted>19){
                cooling++;
                System.out.println("\n============================");
                System.out.println("ENFRIAMIENTO: "+cooling);
                System.out.println("============================");
//                System.out.println("\tRECORRIDO: "+sol.toString());
//                System.out.println("\tFUNCION OBJETIVO (km): "+cost);
//                System.out.println("(1+(1/cooling)) - "+(1+(1d/cooling)));
                temp = temp / (1+(1d/cooling));
                if (cooling==1) {
                    this.sol = bestsol.clone();
                    cost = bestcost;
                }

                accepted = 0; tested = 0;
                System.out.printf("TEMPERATURA: %.6f\n", temp);
            }
//            if (cooling>1 && (tested+accepted > 1)){
//                break;
//            }
        }

        System.out.println("\n\nMEJOR SOLUCION: ");
        System.out.println("\tRECORRIDO: "+backupsol.toString());
        System.out.println("\tFUNCION OBJETIVO (km): "+backupcost);
        System.out.println("\tITERACION: "+(backupit+1));
        double µ = 0.01, φ = 0.5;
        System.out.printf("\tmu = %.2f, phi = %.1f\n", µ, φ);

        t.end().print();

        return new Point2D.Float(backupcost, backupit+1);
    }

}
