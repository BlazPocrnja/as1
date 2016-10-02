package example.habittracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * View all completion times for a given Habit.
 * Click to delete specific completions times.
 */
public class CompletionsActivity extends AppCompatActivity {

    //Text Views
    TextView nameTextView;
    TextView countTextView;

    //Habit information
    private ArrayList<Habit> habits = new ArrayList<Habit>();
    private Habit habit = new Habit();
    //Location of habit in habits list
    private int location = 0;

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
                //Create pop up menu
                //Source: http://stackoverflow.com/questions/21329132/android-custom-dropdown-popup-menu
                PopupMenu popup = new PopupMenu(CompletionsActivity.this,view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu_delete, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        deleteCompletionDialog(parent.getItemAtPosition(position).toString());
                        return true;
                    }
                });

                popup.show(); //showing popup menu
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
            ++location;
        }

        //Change the habit's completion dates to strings
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a                     " +
                "                     MM/dd/yyyy");
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

    private void deleteCompletionDialog(final String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?");

        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(CompletionsActivity.this,"Completion Deleted!",Toast.LENGTH_LONG).show();
                deleteCompletion(date);
            }
        });

        builder.setPositiveButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void deleteCompletion(String date){

        int index = 0;
        for(String i: completions){
            if(date.equals(i)){
                break;
            }
            ++index;
        }

        habits.get(location).removeCompletion(index);
        completions.remove(index);
        completionsAdapter.notifyDataSetChanged();
        countTextView.setText(habit.getCount().toString() + " Completion(s)");
        FileRetriever retriever = new FileRetriever(this);
        retriever.saveInFile(habits,FILENAME);
    }



}
