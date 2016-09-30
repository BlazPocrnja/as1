package example.habittracker;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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

    //Max length of name
    private static final int MAX_LENGTH = 34;


    //--------Constructors-----------
    public Habit(){
        SecureRandom random = new SecureRandom();
        this.name = new BigInteger(130, random).toString(32);
        this.date = Calendar.getInstance();
        this.completions = new ArrayList<Calendar>();
        this.days = new boolean[7];
        Arrays.fill(this.days,false);
    }

    public Habit(String name){
        this.name = name;
        this.date = Calendar.getInstance();
        this.completions = new ArrayList<Calendar>();
        this.days = new boolean[7];
        Arrays.fill(this.days,false);
    }

    public Habit(String name, Calendar date){
        this.name = name;
        this.date = date;
        this.completions = new ArrayList<Calendar>();
        this.days = new boolean[7];
        Arrays.fill(this.days,false);
    }


    //-----------Setters----------------
    //Sets boolean for a specific day
    public void setDays(int i, boolean b){
            this.days[i] = b;
    }

    public void setName(String name) throws NameTooLongException{
        if (name.length() > MAX_LENGTH) {
            this.name = name.substring(0,MAX_LENGTH);
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

    //Return true is no days set
    public boolean daysNotSet(){
        for(int i = 0; i < this.days.length; ++i){
            if(this.days[i] == true){
                return false;
            }
        }
        return true;
    }

    public String getName(){
        return this.name;
    }

    public int getMaxLength(){
        return this.MAX_LENGTH;
    }

    public Calendar getDate(){
        return this.date;
    }

    //Returns the list of completion dates
    public ArrayList<Calendar> getCompletions(){
        return this.completions;
    }

    //Returns number of completions
    public Integer getCount(){
        return completions.size();
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






