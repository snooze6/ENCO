import model.matrix;
import model.random;
import model.solution;
import model.traveler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Snooze on 06/12/2016.
 */
public class main {

    public static void main(String[] args) throws InterruptedException, IOException {
        int n = 9;
//        int n = 99;
        solution ini = null;

        Scanner scanner = null;

        if (args.length < 1 || args.length > 2) {
            System.out.println(" + usage: run.jar main.py distancias.txt [aleatorios.txt]");
            System.exit(-1);
        }

        Path dist = Paths.get(args[0]);
        // Contar lineas de distancias
        scanner = new Scanner(dist);
        int count = 0;
        while (scanner.hasNextLine()) {
            count++;
            scanner.nextLine();
        }
        scanner.close();
//        n = (int) Files.lines(dist).count();
        n = count;

        matrix m = new matrix(n);
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
                random.setThings(scanner);
            } catch (IOException ex) {
                System.out.println(" + usage: run.jar distancias.txt [aleatorios.txt]");
                System.out.println("     No se puede leer aleatorios.txt");
                System.exit(-1);
            }
        }

        t = new traveler(0, m, n);

        t.doit();

        scanner.close();
    }
}
