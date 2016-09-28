package example.habittracker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by pocrn_000 on 9/26/2016.
 */
public class Habit {

    //--------Private Data-----------
    private String name;
    private Calendar date;

    //7 values to toggle specific days of the week; correspond to
    // 0 , 1 ,  2 , 3 ,  4  , 5 , 6
    //Sun,Mon,Tues,Wed,Thurs,Fri,Sat
    private boolean[] days;

    //Array of dates when this habit was completed
    private ArrayList<Calendar> completions;


    //--------Constructors-----------
    public Habit(String name){
        this.name = name;
        this.date = Calendar.getInstance();
        this.completions = new ArrayList<Calendar>();
        this.days = new boolean[7];
    }

    public Habit(String name, Calendar date){
        this.name = name;
        this.date = date;
        this.completions = new ArrayList<Calendar>();
        this.days = new boolean[7];
    }


    //-----------Setters----------------
    //Sets boolean for a specific day
    public void setDays(int i, boolean b){
            this.days[i] = b;
    }

    public void setName(String name) throws NameTooLongException{
        if (name.length() > 25) {
            this.name = name.substring(0,25);
            throw new NameTooLongException();
        }
        else this.name = name;
    }

    public void setDate(Calendar date){
        this.date = date;
    }

    public void addCompletion(Calendar date){
        this.completions.add(date);
    }

    //Removes first element with specified date(which are unique to the millisecond)
    public void removeCompletion(Calendar date){
        this.completions.remove(date);
    }


    //-----------Getters----------------
    //Returns a value of specific day
    public boolean getDay(int i){
        return days[i];
    }

    public String getName(){
        return this.name;
    }

    public Calendar getDate(){
        return this.date;
    }

    //Returns a copy of the list of completion dates
    //Source: http://stackoverflow.com/questions/7042182/how-to-make-a-deep-copy-of-java-arraylist
    public ArrayList<Calendar> getCompletions(){
        ArrayList<Calendar> tmp = new ArrayList<Calendar>();
        for(Calendar c : this.completions){
            tmp.add((Calendar) c.clone());
        }
        return tmp;
    }

    //Checks if habit has been completed at least once
    public boolean isComplete(){
        if(this.completions.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
}






