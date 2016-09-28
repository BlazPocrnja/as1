/*
HabitTracker

Copyright (C) 2016 Blaz Pocrnja blaz@ualberta.ca
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package example.habittracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //Arrays
    private ArrayList<Habit> habits = new ArrayList<Habit>();
    private ArrayList<Habit> completedHabits = new ArrayList<Habit>();
    private ArrayList<Habit> incompleteHabits = new ArrayList<Habit>();

    //ListViews
    private ListView incompleteView;
    private ListView completedView;

    //Adapters
    private ArrayAdapter<Habit> incompleteAdapter;
    private ArrayAdapter<Habit> completedAdapter;

    //Save File
    private static final String FILENAME = "habitFile.sav";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //onClick listeners set up for both ListViews
        //Source: http://stackoverflow.com/questions/4709870/setonitemclicklistener-on-custom-listview
        incompleteView = (ListView) findViewById(R.id.incompleteView);
        incompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get habit name
                String str = parent.getItemAtPosition(position).toString();
                completedDialog(str);

            }
        });

        completedView = (ListView) findViewById(R.id.completeView);
        completedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get habit name
                String str = parent.getItemAtPosition(position).toString();
                completedDialog(str);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        incompleteAdapter = new ArrayAdapter<Habit>(this, R.layout.list_item, incompleteHabits);
        incompleteView.setAdapter(incompleteAdapter);

        completedAdapter = new ArrayAdapter<Habit>(this, R.layout.list_item, completedHabits);
        completedView.setAdapter(completedAdapter);

        loadFromFile();
        updateAdapters();
    }

    //Source: http://www.tutorialspoint.com/android/android_alert_dialoges.htm
    protected void completedDialog(final String habit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Completed?");

       builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(MainActivity.this,"Habit Completed!",Toast.LENGTH_LONG).show();
                addCompletion(habit);
            }
        });

        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(habits, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();

            habits = gson.fromJson(in,listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            habits = new ArrayList<Habit>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void addCompletion(String habitName) {
        for (Habit i : habits) {
            if (i.getName().equals(habitName)) {
                i.addCompletion(Calendar.getInstance());
                updateAdapters();
                saveInFile();
                break;
            }
        }

    }

    private void updateAdapters(){

        completedHabits.clear();
        incompleteHabits.clear();

        Calendar today = Calendar.getInstance();
        //DAY_OF_WEEK is 1-7 while mine is 0-6
        int dayOfWeek= today.get(Calendar.DAY_OF_WEEK) - 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if(!habits.isEmpty()) {
            for (Habit i : habits) {
                //If the habit is for today
                if (i.getDay(dayOfWeek)) {
                    //Check if has completions today or not
                    if (i.isComplete()) {
                        for (Calendar d : i.getCompletions()) {
                            if (dateFormat.format(d.getTime()).equals(dateFormat.format(today.getTime()))) {
                                completedHabits.add(i);
                                break;
                            }
                        }

                    } else {
                        incompleteHabits.add(i);
                    }
                }

            }
        }

        completedAdapter.notifyDataSetChanged();
        incompleteAdapter.notifyDataSetChanged();
    }

    //Called when user clicks add button
    public void addHabit(View view) {
        Intent intent = new Intent(this, AddHabitActivity.class);
        startActivity(intent);
    }

    //Called when user clicks see all button
    public void allHabits(View view){
        Intent intent = new Intent(this, AllHabitsActivity.class);
        startActivity(intent);
    }

}
