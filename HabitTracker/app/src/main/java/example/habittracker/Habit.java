package example.habittracker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pocrn_000 on 9/26/2016.
 */
public class Habit {

    private String name;
    private Date date;
    private ArrayList<Date> completions;

    public Habit(String name){
        this.name = name;
        this.date = new Date();
        this.completions = new ArrayList<Date>();
    }

    public Habit(String name, Date date){
        this.name = name;
        this.date = date;
        this.completions = new ArrayList<Date>();
    }

    public void setName(String name){
        if(name.length() > 25){

            //To do
        }
        this.name = name;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String getName(){
        return this.name;
    }

    public Date getDate(){
        return this.date;
    }

    public void addCompletion(Date date){
        completions.add(date);
    }

    public void getCompletion(){

    }
}
