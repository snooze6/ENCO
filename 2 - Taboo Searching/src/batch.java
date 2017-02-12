import model.matrix;
import model.solution;
import model.traveler;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Snooze on 06/12/2016.
 */
public class batch {

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

        ArrayList<Point2D.Float> res = new ArrayList<>();
        for (int i=0; i<10; i++) {
            traveler t = new traveler(0, m, n);
            t.setSol(t.initSol(n));
            res.add(t.doit());
        }

        float sumacoste = 0;
        float sumaiteracion = 0;
        for (int j=0; j<res.size(); j++){
            System.out.println(res.get(j));
            sumacoste += res.get(j).x;
            sumaiteracion += res.get(j).y;
        }
        System.out.println(" Avg coste: "+(sumacoste/(float)res.size()));
        System.out.println(" Avg iteraciones: "+(sumaiteracion/(float)res.size()));
        System.out.println(" Avg: "+(sumacoste/res.size()));

    }
}
