package model;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by arman on 06/01/2017.
 */
public class random {

    private static boolean flag = false;
    private static Scanner s = null;

    public static float getRand(){
        return flag ? Float.parseFloat(s.nextLine()) : (float)ThreadLocalRandom.current().nextDouble(0, 1);
    }

//    private float getRand() {
//        float ret = -1;
//        if (scanner!=null)
//            ret = scanner.nextFloat();
//        if (ret == -1)
//            ret = (float) ThreadLocalRandom.current().nextDouble(0,1);
//        return ret;
//    }

    public static void setThings(Scanner b){
        s = b; flag = true;
    }


}
