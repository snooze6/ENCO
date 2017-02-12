import model.better_traveller;
import model.matrix;
import model.solution;
import model.traveler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Snooze on 06/12/2016.
 */
public class mainbetter {

    public static void main(String[] args) throws InterruptedException {
        int n = 99;
        matrix m = new matrix(n);
        solution ini = null;

        Scanner scanner = null;

        if (args.length < 1 || args.length > 2) {
            System.out.println(" + usage: run.jar main.py distancias.txt [aleatorios.txt]");
            System.exit(-1);
        }

        Path dist = Paths.get(args[0]);
        try {
            scanner = new Scanner(dist);
            m.load(scanner);
            scanner.close();
        } catch (IOException ex) {
            System.out.println(" + usage: run.jar distancias.txt [aleatorios.txt]");
            System.out.println("     No se puede leer distancias.txt");
            System.exit(-1);
        }

        traveler t = null;
        if (args.length == 2) {
            Path rand = Paths.get(args[1]);
            try {
                scanner = new Scanner(rand);
                t = new traveler(0, m, n, scanner);
            } catch (IOException ex) {
                System.out.println(" + usage: run.jar distancias.txt [aleatorios.txt]");
                System.out.println("     No se puede leer aleatorios.txt");
                System.exit(-1);
            }
        } else {
            t = new better_traveller(0, m, n);
        }
        ini = t.initSol(n, scanner);
        t.setSol(ini);
        t.doit();
        scanner.close();
    }
}
