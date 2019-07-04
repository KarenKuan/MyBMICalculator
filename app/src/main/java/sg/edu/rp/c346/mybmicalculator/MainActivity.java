package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnResetData;
    TextView tvCalender;
    TextView tvBMI;
    TextView tvComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnResetData = findViewById(R.id.buttonResetData);
        tvCalender = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvComment = findViewById(R.id.textViewComment);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.putFloat("weight", weight);
                prefEdit.putFloat("height", height);

                prefEdit.commit();

                float BMI = weight / (height*height);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                String str1 = String.format("%.3f", BMI);

                tvCalender.setText("Last Calculated Date: " + datetime);
                System.out.println(str1);
                tvBMI.setText("Last Calculated BMI: " + str1);

                if (BMI < 18.5) {
                    tvComment.setText("You are underweight");
                } else if (BMI < 24.9) {
                    tvComment.setText("You BMI is normal");
                } else if (BMI < 29.9) {
                    tvComment.setText("You are overweight");
                } else {
                    tvComment.setText("You are obese");
                }
            }
        });

        btnResetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText("");
                etHeight.setText("");
                tvCalender.setText("");
                tvBMI.setText("");
                tvComment.setText("");
            }
        });

        // This is new line
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Step 1a: Get the user input from the EditText and store it in a variable
        float weight = Float.parseFloat(etWeight.getText().toString());
        float height = Float.parseFloat(etHeight.getText().toString());

        // Step 1b: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 1c: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        // Step 1d: Add the key-value pair
        prefEdit.putFloat("weight", weight);
        prefEdit.putFloat("height", height);

        // Step 1e: Call commit() to save the changes into SharedPreferences
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data from the SharedPreferences object
        Float weight = prefs.getFloat("weight", 0);
        Float height = prefs.getFloat("height", 0);

        // Step 2c: Update the UI element with the value
        float BMI = weight / (height*height);

        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        String str1 = String.format("%.3f", BMI);

        tvCalender.setText("Last Calculated Date: " + datetime);
        System.out.println(str1);
        tvBMI.setText("Last Calculated BMI: " + str1);
    }
}

