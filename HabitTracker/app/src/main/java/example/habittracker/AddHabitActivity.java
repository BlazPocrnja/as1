package example.habittracker;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Can add new Habits by entering data.
 * "Save" button saves the habit and takes you back to MainActivity.
 */
public class AddHabitActivity extends AppCompatActivity {

    //Save file
    private static final String FILENAME = "habitFile.sav";

    //Habit Creating
    private Habit habit = new Habit();
    private Calendar newDate = Calendar.getInstance();
    private boolean dateSet = false;

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
        //Source: http://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
        dateText = (EditText)findViewById(R.id.habitDate);
        dateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(AddHabitActivity.this, myDateListener,
                        newDate.get(Calendar.YEAR),
                        newDate.get(Calendar.MONTH),
                        newDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    //Pop up for selecting Habit date
    //Source://Source: http://www.tutorialspoint.com/android/android_datepicker_control.htm
    DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            newDate.set(Calendar.YEAR, year);
            newDate.set(Calendar.MONTH, month);
            newDate.set(Calendar.DAY_OF_MONTH, day);
            dateText.setText(newDate.get(Calendar.YEAR) + "/" + newDate.get(Calendar.MONTH) + "/" + newDate.get(Calendar.DAY_OF_MONTH));
            dateSet = true;
        }
    };

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
                //Add the name
                habit.setName(name);

                //Add the date
                if(dateSet){
                    habit.setDate(newDate);
                }
                else habit.setDate(Calendar.getInstance());


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
