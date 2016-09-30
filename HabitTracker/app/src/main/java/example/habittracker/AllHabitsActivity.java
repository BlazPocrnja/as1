package example.habittracker;

/**
 * Created by pocrn_000 on 9/27/2016.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AllHabitsActivity extends AppCompatActivity {

    //Array of habits
    private ArrayList<Habit> habits = new ArrayList<Habit>();

    //ListView
    private ListView habitsView;

    //Adapter
    private ArrayAdapter<Habit> habitsAdapter;

    //Save File
    private static final String FILENAME = "habitFile.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_habits);

        //onClick listeners set up for both ListViews
        habitsView = (ListView) findViewById(R.id.view_all);
        /*habitsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Load in data from file
        FileRetriever retriever = new FileRetriever(this);
        habits = retriever.loadFromFile(FILENAME);

        //Set up adapter to view
        habitsAdapter = new ArrayAdapter<Habit>(this, R.layout.list_item, habits);
        habitsView.setAdapter(habitsAdapter);
    }
}
