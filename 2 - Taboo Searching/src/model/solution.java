package model;

import java.util.ArrayList;

/**
 * Created by Snooze on 06/12/2016.
 */
public class solution {
    ArrayList<Integer> solucion;
    int n = 0;

    public solution(int n){
        solucion = new ArrayList<>();
        this.n = n;
    }

    public solution(solution s){
        this.n = s.n;
        this.solucion = (ArrayList<Integer>)s.solucion.clone();
    }

//    public solution(ArrayList<Integer> solucion) {
//        this.solucion = solucion;
//    }

    public ArrayList<Integer> getArray(){
        return solucion;
    }

    public void print(){
        System.out.println(this.toString());
    }

    public void push(int n){
        int it = 0;
        while (it <= this.n) {
            it++;
            if (!solucion.contains(n)) {
                solucion.add(n);
                break;
            } else {
                n++;
                if (n > 99)
                    n -= 99;
            }
        }
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i<solucion.size(); i++){
            ret += solucion.get(i) + " ";
//        for (int i = 0; i<solucion.size(); i++){
//            if (i<(solucion.size()-1)) {
//                ret += solucion.get(i) + " ";
//            } else {
//                ret += solucion.get(i);
//            }
//            if (i<(solucion.size()-1)) {
//                ret += String.format("%2d", solucion.get(i)) + " ";
//            } else {
//                ret += String.format("%2d", solucion.get(i));
//            }
        }
        return ret;
    }

    @Override
    protected solution clone() throws CloneNotSupportedException {
        return new solution(this);
    }

    public int get(int i){
        return solucion.get(i);
    }

    public solution permute(int i, int j){
        solution sol = new solution(this);

        int aux = sol.get(i);
        sol.solucion.set(i, sol.get(j));
        sol.solucion.set(j, aux);

        return sol;
    }
}
