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
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        //Load in data from file
        FileRetriever retriever = new FileRetriever(this);
        habits = retriever.loadFromFile(FILENAME);
        updateArrays();

        incompleteAdapter = new ArrayAdapter<Habit>(this, R.layout.list_item, incompleteHabits);
        incompleteView.setAdapter(incompleteAdapter);

        completedAdapter = new ArrayAdapter<Habit>(this, R.layout.list_item, completedHabits);
        completedView.setAdapter(completedAdapter);

        ListUtils.setDynamicHeight(completedView);
        ListUtils.setDynamicHeight(incompleteView);


    }

    //Source: http://www.tutorialspoint.com/android/android_alert_dialoges.htm
    protected void completedDialog(final String habit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Complete?");

       builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(MainActivity.this,"Habit Completed!",Toast.LENGTH_LONG).show();
                addCompletion(habit);
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

    private void addCompletion(String habitName) {
        for (Habit i : habits) {
            if (i.getName().equals(habitName)) {
                i.addCompletion(Calendar.getInstance());
                updateArrays();
                completedAdapter.notifyDataSetChanged();
                incompleteAdapter.notifyDataSetChanged();
                ListUtils.setDynamicHeight(completedView);
                ListUtils.setDynamicHeight(incompleteView);
                FileRetriever retriever = new FileRetriever(this);
                retriever.saveInFile(habits,FILENAME);
                break;
            }
        }

    }

    private void updateArrays(){

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
                    //Check if has completions today; habits are only completed for a specific day
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

    @Override
    protected void onResume() {
        super.onResume();
        habits.clear();
        FileRetriever retriever = new FileRetriever(this);
        habits = retriever.loadFromFile(FILENAME);
        updateArrays();
        completedAdapter.notifyDataSetChanged();
        incompleteAdapter.notifyDataSetChanged();
        ListUtils.setDynamicHeight(completedView);
        ListUtils.setDynamicHeight(incompleteView);

    }

    //Source:http://stackoverflow.com/questions/17693578/android-how-to-display-2-listviews-in-one-activity-one-after-the-other
    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = MeasureSpec.makeMeasureSpec(mListView.getWidth(), MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

}


