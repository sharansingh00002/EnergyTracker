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

public class Survey3 extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int homeLights , homeFans , officeFans, officeLights;
    String ref;
    EditText homelight,homefan,officelight,officefan;
    Spinner refrigerator_spinner;
    Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey3);
        skip=findViewById(R.id.skip);
        sharedPreferences = this.getSharedPreferences("com.example.sharansingh.solarifyenergy", MODE_PRIVATE);
        final SharedPreferences.Editor sharedEditor=sharedPreferences.edit();
        refrigerator_spinner = findViewById(R.id.refrigerator_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.refrigerator, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        refrigerator_spinner.setAdapter(adapter2);

        homelight = findViewById(R.id.no_of_lights_in_home);
        homefan = findViewById(R.id.no_of_fans_in_home);
        officelight = findViewById(R.id.no_of_lights_in_office);
        officefan = findViewById(R.id.no_of_fans_in_office);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedEditor.putInt("homeLights", 3);
                sharedEditor.putInt("homeFans", 3);
                sharedEditor.putInt("officeLights", 3);
                sharedEditor.putInt("officeFans", 3);
                sharedEditor.putString("homeRefrigerator", "Yes");
                sharedEditor.commit();

                sharedEditor.putInt("HomeTotal", 0);
                sharedEditor.putInt("OthersTotal", 0);
                sharedEditor.putInt("WorkTotal", 0);
                sharedEditor.putInt("TravelTotal",0);
                sharedEditor.putInt("Total",0);
                sharedEditor.commit();

                Intent i1 = new Intent(getApplicationContext(), PreSurvey34.class);
                startActivity(i1);
            }
        });
        Button continue_further = findViewById(R.id.continue_further);
        continue_further.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeLights = Integer.parseInt(homelight.getText().toString());
                homeFans = Integer.parseInt(homefan.getText().toString());
                officeLights = Integer.parseInt(officelight.getText().toString());
                officeFans = Integer.parseInt(officefan.getText().toString());
                ref = refrigerator_spinner.getSelectedItem().toString();

                sharedEditor.putInt("homeLights", homeLights);
                sharedEditor.putInt("homeFans", homeFans);
                sharedEditor.putInt("officeLights", officeLights);
                sharedEditor.putInt("officeFans", officeFans);
                sharedEditor.putString("homeRefrigerator", ref);
                sharedEditor.commit();

                sharedEditor.putInt("HomeTotal", 0);
                sharedEditor.putInt("OthersTotal", 0);
                sharedEditor.putInt("WorkTotal", 0);
                sharedEditor.putInt("TravelTotal",0);
                sharedEditor.putInt("Total",0);
                sharedEditor.commit();

                /*
                sharedPreferences.edit().putInt("homeLights", homeLights);
                sharedPreferences.edit().putInt("homeFans", homeFans);
                sharedPreferences.edit().putInt("officeLights", officeLights);
                sharedPreferences.edit().putInt("officeFans", officeFans);
                sharedPreferences.edit().putString("homeRefrigerator", ref);
                sharedPreferences.edit().commit();
/*
                sharedEditor.putInt("homeFans",homeFans);
                sharedEditor.commit();*/
                Intent i1 = new Intent(getApplicationContext(), PreSurvey34.class);
                startActivity(i1);
            }
        });
    }
}
