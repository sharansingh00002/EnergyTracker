package com.example.sharansingh.solarifyenergy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Survey4 extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Spinner travel_spinner, light_spinner, app_spinner, rating_spinner;
    Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey4);

        skip=findViewById(R.id.skip);
        sharedPreferences = this.getSharedPreferences("com.example.sharansingh.solarifyenergy", MODE_PRIVATE);
        travel_spinner = findViewById(R.id.travel_s);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.travel, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        travel_spinner.setAdapter(adapter2);

        light_spinner = findViewById(R.id.lights_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.lighttypes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        light_spinner.setAdapter(adapter3);

        app_spinner = findViewById(R.id.fans_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.fantypes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        app_spinner.setAdapter(adapter4);

        rating_spinner = findViewById(R.id.ratings_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.ratings, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        rating_spinner.setAdapter(adapter5);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences.edit().putString("travelMode","Public").apply();
                sharedPreferences.edit().putString("lightsType","CFL" ).apply();
                sharedPreferences.edit().putString("fansType", "Regular").apply();
                sharedPreferences.edit().putString("applianceRatings", "4*").apply();
                Intent i3 = new Intent(getApplicationContext(), Presurvey45.class);
                startActivity(i3);
            }
        });

        Button finish_button = findViewById(R.id.finish);
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String travel_mode = travel_spinner.getSelectedItem().toString();
                String lights_type = light_spinner.getSelectedItem().toString();
                String fan_type = app_spinner.getSelectedItem().toString();
                String appliance_ratings = rating_spinner.getSelectedItem().toString();

                sharedPreferences.edit().putString("travelMode", travel_mode).apply();
                sharedPreferences.edit().putString("lightsType", lights_type).apply();
                sharedPreferences.edit().putString("fansType", fan_type).apply();
                sharedPreferences.edit().putString("applianceRatings", appliance_ratings).apply();

                Toast.makeText(getApplicationContext(), "Your App is being customized, please wait", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(getApplicationContext(), Presurvey45.class);
                startActivity(i3);
            }
        });
    }
}
