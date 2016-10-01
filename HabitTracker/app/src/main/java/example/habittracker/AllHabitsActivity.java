package example.habittracker;

/**
 * Created by pocrn_000 on 9/27/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AllHabitsActivity extends AppCompatActivity {

    //Array of habits
    private ArrayList<Habit> habits = new ArrayList<Habit>();

    //ListView
    private ListView habitsView;

    //Adapter
    private ArrayAdapter<Habit> habitsAdapter;

    //Save File
    private static final String FILENAME = "habitFile.sav";

    //Habit name to pass into next activity
    public final static String EXTRA_HABIT = "com.example";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_habits);

        //onClick listeners set up for ListView
        habitsView = (ListView) findViewById(R.id.view_all);
        habitsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                //Create pop up menu
                //Source: http://stackoverflow.com/questions/21329132/android-custom-dropdown-popup-menu
                PopupMenu popup = new PopupMenu(AllHabitsActivity.this,view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String selected = item.getTitle().toString();
                        //Clicked see completions
                        if(selected.equalsIgnoreCase("See Completions")){
                            Intent intent = new Intent(AllHabitsActivity.this,CompletionsActivity.class);
                            //Passes name of habit clicked into completions activity
                            intent.putExtra(EXTRA_HABIT, parent.getItemAtPosition(position).toString());
                            startActivity(intent);
                        }
                        //Else you clicked delete
                        else{
                            deleteHabitDialog(parent.getItemAtPosition(position).toString());
                        }

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

        //Set up adapter to view
        habitsAdapter = new ArrayAdapter<Habit>(this, R.layout.list_item, habits);
        habitsView.setAdapter(habitsAdapter);
    }

    private void deleteHabitDialog(final String habit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?");

        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(AllHabitsActivity.this,"Habit Deleted!",Toast.LENGTH_LONG).show();
                deleteHabit(habit);
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

    //Delete habit with specified name
    private void deleteHabit(String name){
        for (Habit i : habits) {
            if (i.getName().equals(name)) {
                habits.remove(i);
                habitsAdapter.notifyDataSetChanged();
                FileRetriever retriever = new FileRetriever(this);
                retriever.saveInFile(habits,FILENAME);
                break;
            }
        }
    }

    //Called when user clicks add button
    public void addHabit(View view) {
        Intent intent = new Intent(this, AddHabitActivity.class);
        startActivity(intent);
    }


}



