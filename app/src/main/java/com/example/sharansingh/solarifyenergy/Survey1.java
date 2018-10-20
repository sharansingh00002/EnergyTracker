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


public class Survey1 extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText name_of_user, age_of_user;
    Spinner gender_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey1);

        sharedPreferences = this.getSharedPreferences("com.example.sharansingh.solarifyenergy", MODE_PRIVATE);
        final SharedPreferences.Editor sharedEditor=sharedPreferences.edit();
        gender_spinner = findViewById(R.id.genderspinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gender_spinner.setAdapter(adapter);

        name_of_user = findViewById(R.id.name_of_user);


        age_of_user = findViewById(R.id.age_of_user);


        Button continue_button = findViewById(R.id.continue_button);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = name_of_user.getText().toString();
                String user_age = age_of_user.getText().toString();
                String gender_value = gender_spinner.getSelectedItem().toString();

                sharedPreferences.edit().putString("user_name", user_name).apply();
                sharedPreferences.edit().putString("user_age", user_age).apply();
                sharedPreferences.edit().putString("user_gender", gender_value).apply();

             /*   sharedEditor.putString("user_name", user_name);
                sharedEditor.commit();*/
                Intent i = new Intent(getApplicationContext(), Presurvey12.class);
                startActivity(i);
            }
        });
    }
}
