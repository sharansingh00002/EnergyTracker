package com.example.sharansingh.solarifyenergy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Survey2 extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Spinner time_spinner;
    EditText time_spent_home, time_spent_office, time_spent_travel;
    Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);
        skip=(Button)findViewById(R.id.skip);
        sharedPreferences = this.getSharedPreferences("com.example.sharansingh.solarifyenergy", MODE_PRIVATE);
        time_spinner = findViewById(R.id.timespinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.timeSpent_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        time_spinner.setAdapter(adapter2);

        time_spent_home = findViewById(R.id.time_spent_at_home);
        time_spent_office = findViewById(R.id.time_spent_at_office);
        time_spent_travel = findViewById(R.id.time_spent_in_travel);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString("max_time_spent", "5").apply();
                sharedPreferences.edit().putString("time_spent_at_home", "5").apply();
                sharedPreferences.edit().putString("time_spent_at_office", "5").apply();
                sharedPreferences.edit().putString("time_spent_at_travel", "5").apply();
                startActivity(new Intent(getApplicationContext(), Presurvey23.class));
            }
        });

        Button con = findViewById(R.id.continue_2);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String max_time_spent = time_spinner.getSelectedItem().toString();
                String time_spent_at_home = time_spent_home.getText().toString();
                String time_spent_at_office = time_spent_office.getText().toString();
                String time_spent_in_travel = time_spent_travel.getText().toString();

                sharedPreferences.edit().putString("max_time_spent", max_time_spent).apply();
                sharedPreferences.edit().putString("time_spent_at_home", time_spent_at_home).apply();
                sharedPreferences.edit().putString("time_spent_at_office", time_spent_at_office).apply();
                sharedPreferences.edit().putString("time_spent_at_travel", time_spent_in_travel).apply();


                startActivity(new Intent(getApplicationContext(), Presurvey23.class));
            }
        });
    }
}
