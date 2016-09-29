package example.habittracker;

/**
 * Created by pocrn_000 on 9/27/2016.
 */

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddHabitActivity extends AppCompatActivity {

    private static final String FILENAME = "habitFile.sav";
    //Habit Creating
    private Habit habit = new Habit();

    //Text fields
    private EditText nameText;
    private EditText dateText;

    //Check field
    private CheckBox checkBox;

    //Habits
    private ArrayList<Habit> habits = new ArrayList<Habit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);

        //Get Habit list
        FileRetriever retriever = new FileRetriever(this);
        habits = retriever.loadFromFile(FILENAME);

        //Name field
        nameText = (EditText)findViewById(R.id.habitName);

        //Date field
        dateText = (EditText)findViewById(R.id.habitDate);
        dateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TO DO
            }
                                    }
        );



    }

    //When a day box is filled
    public void onCheckBoxClicked(View view) {
        // Is the button now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which box was clicked
        switch(view.getId()) {
            case R.id.check_sun:
                if (checked){
                    habit.setDays(0,true);
                }
                else habit.setDays(0,false);
                break;
            case R.id.check_mon:
                if (checked){
                    habit.setDays(1,true);
                }
                else habit.setDays(1,false);
                break;
            case R.id.check_tues:
                if (checked){
                    habit.setDays(2,true);
                }
                else habit.setDays(2,false);
                break;
            case R.id.check_wed:
                if (checked){
                    habit.setDays(3,true);
                }
                else habit.setDays(3,false);
                break;
            case R.id.check_thurs:
                if (checked){
                    habit.setDays(4,true);
                }
                else habit.setDays(4,false);
                break;
            case R.id.check_fri:
                if (checked){
                    habit.setDays(5,true);
                }
                else habit.setDays(5,false);
                break;
            case R.id.check_sat:
                if (checked){
                    habit.setDays(6,true);
                }
                else habit.setDays(6,false);
                break;
        }
    }

    public void saveHabit(View view){
        String name = nameText.getText().toString();
        if(name.isEmpty()){
            nameText.setError("Habit must have a name!");
        }
        else if(habitExists(name)){
            nameText.setError("Habit already exists!");
        }
        else if(name.length() > habit.getMaxLength()){
            nameText.setError("Name too long!");
        }
        else if(habit.daysNotSet()){
            daysDialog();
        }
        else{
            try {
                habit.setName(name);

                Calendar now = Calendar.getInstance();
                habit.setDate(now);

                //Add habit to list
                habits.add(habit);

                //Save to file
                FileRetriever retriever = new FileRetriever(this);
                retriever.saveInFile(habits,FILENAME);
                finish();

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }

        }

    }

    private boolean habitExists(String name){
        for(Habit h : habits){
            if(h.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    //Source: http://www.tutorialspoint.com/android/android_alert_dialoges.htm
    protected void daysDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("At least one day must be selected.");

        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
