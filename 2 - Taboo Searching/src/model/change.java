package model;

import java.awt.*;

/**
 * Created by Snooze on 06/12/2016.
 */
public class change extends Point {

    public change() {
        super();
    }

    public change(Point p) {
        super(p);
    }

    public change(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return  false;
        if (obj instanceof change){
            change c = (change) obj;
            return c.x == this.x && c.y == this.y;
        }
        return false;
    }
}
