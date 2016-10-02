package example.habittracker;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Tests Habit class.
 */
public class HabitTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public HabitTest() {
        super(MainActivity.class);
    }

    public void testSetDays(){
        Habit habit = new Habit();

        habit.setDays(0,true);

        assertEquals(habit.getDay(0),true);
        assertEquals(habit.getDay(1), false);
        assertEquals(habit.daysNotSet(),false);

    }

    public void testSetName(){
        Habit habit = new Habit();
        String name = "Testing";

        try {
            habit.setName(name);
        }
        catch(NameTooLongException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        assertTrue(habit.getName().equals(name));

    }

    public void testSetDate(){
        Habit habit = new Habit();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        assertTrue(!cal.equals(habit.getDate()));

        habit.setDate(cal);

        assertTrue(cal.equals(habit.getDate()));
    }
    public void testAddCompletion(){
        Habit habit = new Habit();

        assertTrue(habit.getCompletions().isEmpty());

        Calendar cal = Calendar.getInstance();
        habit.addCompletion(cal);

        assertTrue(!habit.getCompletions().isEmpty());

    }

    public void testRemoveCompletion(){
        Habit habit = new Habit();
        Calendar cal = Calendar.getInstance();
        habit.addCompletion(cal);
        habit.addCompletion(cal);

        assertTrue(!habit.getCompletions().isEmpty());

        habit.removeCompletion(1);

        assertTrue(!habit.getCompletions().isEmpty());

        habit.removeCompletion(0);

        assertTrue(habit.getCompletions().isEmpty());

    }

    public void testGetDay(){
        Habit habit = new Habit();

        assertTrue(habit.getDay(1) == false);

        habit.setDays(1, true);

        assertTrue(habit.getDay(1) == true);
    }

    public void testDaysNotSet(){
        Habit habit = new Habit();

        assertTrue(habit.daysNotSet());

        habit.setDays(0,true);

        assertTrue(!habit.daysNotSet());
    }

    public void testGetName(){
        Habit habit = new Habit("TestName");

        assertTrue(habit.getName().equals("TestName"));
    }

    public void testGetMaxLength(){
        Habit habit = new Habit("TestName");

        assertTrue(habit.getMaxLength() == 34);
    }

    public void testGetDate(){
        Habit habit = new Habit();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        assertTrue(!habit.getDate().equals(cal));

        habit.setDate(cal);

        assertTrue(habit.getDate().equals(cal));

    }

    public void testGetCompletions(){
        Habit habit = new Habit();
        ArrayList<Calendar> notcompletions = new ArrayList<Calendar>();

        assertTrue(notcompletions != habit.getCompletions());

        ArrayList<Calendar> completions = new ArrayList<Calendar>(habit.getCompletions());

        assertEquals(habit.getCompletions(),completions);

    }

    public void testGetCount(){
        Habit habit = new Habit();

        assertTrue(habit.getCount() == 0);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        habit.addCompletion(cal);

        assertTrue(habit.getCount() == 1);

    }

    public void testIsComplete(){
        Habit habit = new Habit();

        assertTrue(!habit.isComplete());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        habit.addCompletion(cal);

        assertTrue(habit.isComplete());

    }

    public void testToString(){
        Habit habit = new Habit("ToString");

        assertTrue(habit.toString().equals("ToString"));
    }
}
