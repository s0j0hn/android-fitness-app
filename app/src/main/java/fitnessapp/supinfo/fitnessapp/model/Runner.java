package fitnessapp.supinfo.fitnessapp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jan on 26/03/16.
 */
public class Runner implements Serializable {

    private int weight;
    private Date date;

    public int getWeight() { return weight; }
    public void setWeight(int w) { this.weight = w;}

    public Date getDate() { return date; }
    public void setDate(Date d) { this.date = d; }

    public int toInt(){ return this.getWeight(); }

}
