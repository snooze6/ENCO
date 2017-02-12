package model;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Snooze on 06/12/2016.
 */
public class matrix {
    int[][] m;

    public matrix(int n){
        m = new int[n+1][];

        for (int i = 0; i < m.length; i++) {
            m[i] = new int[i];
        }
    }

    public void print(){
        System.out.println("< matriz >");
        System.out.println("Size: "+m.length);
        for (int[] aM : m) {
          if (aM != null) {
            for (int i=0; i<aM.length; i++) {
                if (i < aM.length-1) {
                    System.out.printf(String.format("%3d", aM[i]) + " ");
                } else {
                    System.out.printf(String.format("%3d", aM[i]) + "\n");
                }
            }
          }
        }
        System.out.println("</matriz >");
    }

    public void load(Scanner s){
        for (int i = 0; i < m.length; i++) {
            m[i] = new int[i];
            for (int j = 0; j < i; j++) {
                m[i][j] = s.nextInt();
            }
        }
    }

    public int getMax(){
        int max = -1;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] > max || max == -1)
                    max = m[i][j];
            }
        }
        return max;
    }

    public int getMin(){
        int min = -1;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] > min || min == -1)
                    min = m[i][j];
            }
        }
        return min;
    }

    public int get(int i, int j){
        if (j>i){
            return get(j,i);
        } else {
            if (j==i) {
                return 0;
            } else {
                return m[i][j];
            }
        }
    }

    public int set(int i, int j, int value){
        if (j>i){
            return set(j,i, value);
        } else {
            if (j==i) {
                return 0;
            } else {
                return m[i][j] = value;
            }
        }
    }

    public int getCost(solution sol){
        int suma = 0;
        try {
            ArrayList<Integer> s = sol.getArray();
            suma += m[s.get(0)][0];
            for (int i = 0; i < s.size() - 1; i++) {
                int a = s.get(i), b = s.get(i + 1);
                if (a > b) {
                    suma += m[a][b];
                } else {
                    suma += m[b][a];
                }
            }
            suma += m[s.get(s.size() - 1)][0];
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("\tException!! <"+ sol.get(0)+", 0>");
            System.out.println("\tRECORRIDO: "+sol.toString());
            throw e;
        }
        return suma;
    }

    public float getCost(solution sol, matrix frec){
        int suma = 0;
        try {
            float fmax = (float) frec.getMax(), mu = 0.05f, dmax = getMax(), dmin = getMin();
            ArrayList<Integer> s = sol.getArray();
            suma += m[s.get(0)][0];
            for (int i = 0; i < s.size() - 1; i++) {
                int a = s.get(i), b = s.get(i + 1);
                float f;
                if (a > b) {
                    f = mu * (dmax - dmin) * ((float) frec.m[a][b] / fmax);
                    suma += m[a][b] + f;
                } else {
                    f = mu * (dmax - dmin) * ((float) frec.m[b][a] / fmax);
                    suma += m[b][a] + f;
                }
            }
            suma += m[s.get(s.size() - 1)][0];
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("\tException!! <"+ sol.get(0)+", 0>");
            System.out.println("\tRECORRIDO: "+sol.toString());
            throw e;
        }
        return suma;
    }

//    public static int calcCost(int[][] m, ArrayList<Integer> s){
//        int suma = 0;
//        suma += m[s.get(0)][0];
//        for (int i = 0; i < s.size()-1; i++){
//            int a = s.get(i), b = s.get(i+1);
//            if (a > b){
//                suma += m[a][b];
//            } else {
//                suma += m[b][a];
//            }
//        }
//        suma += m[s.get(s.size()-1)][0];
//        return suma;
//    }

}
