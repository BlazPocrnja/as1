package example.habittracker;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Tests FileRetriever class.
 */
public class FileTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public FileTest() {
        super(MainActivity.class);
    }

    public void testSave(){
        ArrayList<Habit> habits = new ArrayList<Habit>();
        Habit habit = new Habit("Unique Name");
        habits.add(habit);

        Context context = getActivity();

        FileRetriever retriever = new FileRetriever(context);
        retriever.saveInFile(habits, "TestFile.sav");

        ArrayList<Habit> alsoHabits = new ArrayList<Habit>();
        alsoHabits = retriever.loadFromFile("TestFile.sav");

        assertTrue(alsoHabits.get(0).getName().equals("Unique Name"));
    }

    public void testLoad(){
        ArrayList<Habit> moreHabits = new ArrayList<Habit>();

        Context context = getActivity();
        FileRetriever retriever = new FileRetriever(context);
        moreHabits = retriever.loadFromFile("TestFile.sav");

        assertTrue(moreHabits.get(0).getName().equals("Unique Name"));
    }


}