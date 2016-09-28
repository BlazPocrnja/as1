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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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
        completedAdapter = new ArrayAdapter<Habit>(this, R.layout.list_item, completedHabits);

        incompleteView.setAdapter(incompleteAdapter);
        completedView.setAdapter(completedAdapter);
    }

    //Source: http://www.tutorialspoint.com/android/android_alert_dialoges.htm
    private void completedDialog(String habitName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Completed?");

       builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(MainActivity.this,"Habit Completed!",Toast.LENGTH_LONG).show();
                //TO DO SET HABIT COMPLETION
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

}
