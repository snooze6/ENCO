package model;

import java.util.ArrayList;

/**
 * Created by Snooze on 06/12/2016.
 */
public class solution {
    ArrayList<Integer> solucion;
    int n = 0;

    public solution(int n) {
        solucion = new ArrayList<>();
        this.n = n;
    }

    public solution(solution s) {
        this.n = s.n;
        this.solucion = (ArrayList<Integer>) s.solucion.clone();
    }

//    public model.solution(ArrayList<Integer> solucion) {
//        this.solucion = solucion;
//    }

    public ArrayList<Integer> getArray() {
        return solucion;
    }

    public void print() {
        System.out.println(this.toString());
    }

    public void push(int n) {
        int it = 0;
        while (it <= this.n) {
            it++;
            if (!solucion.contains(n)) {
                solucion.add(n);
                break;
            } else {
                n++;
                if (n > this.n)
                    n -= this.n;
            }
        }
    }

    public void put(int n){
        solucion.add(n);
    }

    public int put(int n, int pos){
        if (!solucion.contains(n)){
            if (solucion.size()<=pos){
               solucion.add(n);
            } else {
                solucion.set(pos, n);
            }
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < solucion.size(); i++) {
            ret += solucion.get(i) + " ";
        }
        return ret;
    }

    @Override
    protected solution clone() throws CloneNotSupportedException {
        return new solution(this);
    }

    public int get(int i) {
        return solucion.get(i);
    }

    public solution permute(int i, int j) {
        solution sol = new solution(this);

        int aux = sol.get(i);
        sol.solucion.set(i, sol.get(j));
        sol.solucion.set(j, aux);

        return sol;
    }

    //------------------------------------------------------------------

    public static solution generateRandSol(int n) {
        solution sol = new solution(n);
        for (int i = 0; i < n; i++) {
            float rand = random.getRand();
//            System.out.println("    - Random: "+rand+" - "+(Math.floor(rand * n)+1));
            sol.push((int) Math.floor(rand * n) + 1);
        }
//        System.out.println(sol.toString());
//        // INDIVIDUO 0 = {FUNCION OBJETIVO (km): 4725, RECORRIDO: 8 7 2 3 5 4 6 9 1 }
//        // INDIVIDUO 49 = {FUNCION OBJETIVO (km): 4490, RECORRIDO: 3 9 1 2 4 8 5 7 6 }
//        System.exit(0);
        return sol;
    }

    public static solution generateVorazSol(int n, matrix dist) {
        solution sol = new solution(n);

        sol.push((int) Math.floor(random.getRand() * n) + 1);

        for (int i = 2; i < n + 1; i++) {
            int prev;
            if (sol.solucion.size() > 0)
                prev = sol.get(sol.solucion.size() - 1);
            else
                prev = 0;

            int coste = -1;
            int min = -1;

            for (int j = 1; j < n + 1; j++) {
                if (j != prev && !sol.solucion.contains(j)) {
                    if (dist.get(prev, j) < coste || coste == -1) {
                        coste = dist.get(prev, j);
                        min = j;
                    }
                }
            }

            sol.push(min);
        }

//        // 7 2 8 5 4 9 3 1 6
//        // 9 2 7 6 1 8 5 4 3
//        System.out.println(sol.toString());
//        System.exit(0);
        return sol;
    }
}