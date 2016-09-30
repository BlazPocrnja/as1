package example.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by pocrn_000 on 9/30/2016.
 */
public class CompletionsActivity extends AppCompatActivity {

    //Text Views
    TextView nameTextView;
    TextView countTextView;

    //Habit information
    private ArrayList<Habit> habits = new ArrayList<Habit>();
    private Habit habit = new Habit();

    //List of completion dates in string form
    private ArrayList<String> completions = new ArrayList<String>();


    //ListView
    private ListView completionsView;

    //Adapter
    private ArrayAdapter<String> completionsAdapter;

    //Save File
    private static final String FILENAME = "habitFile.sav";

    //Passed name
    String habitName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completions);

        //Gets name of habit completion list belongs to
        Intent intent = getIntent();
        habitName = intent.getStringExtra(AllHabitsActivity.EXTRA_HABIT);

        //Puts name in text view
        nameTextView  = (TextView)findViewById(R.id.text_habit);
        nameTextView.setText(habitName);

        //onClick listeners set up for ListView
        completionsView = (ListView) findViewById(R.id.view_completions);
        completionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                //todo
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Load in data from file
        FileRetriever retriever = new FileRetriever(this);
        habits = retriever.loadFromFile(FILENAME);

        //Find habit to view and modify
        for (Habit i : habits) {
            if (i.getName().equals(habitName)) {
                habit = i;
                break;
            }
        }

        //Change the habit's completion dates to strings
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        for(Calendar i: habit.getCompletions()){
            completions.add(dateFormat.format(i.getTime()));
        }

        //Set up adapter to view
        completionsAdapter = new ArrayAdapter<String>(this, R.layout.list_item, completions);
        completionsView.setAdapter(completionsAdapter);

        //Set up amount of completions text view
        countTextView  = (TextView)findViewById(R.id.text_count);
        countTextView.setText(habit.getCount().toString() + " Completion(s)");
    }
}
