package model;

/**
 * Created by Snooze on 06/12/2016.
 */
public class individual {
    int i;
    solution solution;
    float cost;
    int generated;

    public individual(int i, model.solution solution, float cost, int generated) {
        this.i = i;
        this.solution = solution;
        this.cost = cost;
        this.generated = generated;
    }

    @Override
    public String toString() {
        return "INDIVIDUO "+this.i+" = {FUNCION OBJETIVO (km): "+(int)this.cost+", RECORRIDO: "+solution.toString()+"}";
//        return "INDIVIDUO "+this.i+" = {GEN: "+this.generated+" FUNCION OBJETIVO (km): "+(int)this.cost+", RECORRIDO: "+solution.toString()+"}";
    }

    public individual clone(){
        try {
            return new individual(i, this.solution.clone(), cost, generated);
        } catch (CloneNotSupportedException e) {
            System.out.println(" ++ Exception clone not supported");
            return new individual(i, solution, cost, generated);
        }
    }
}
