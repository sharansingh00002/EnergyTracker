package com.example.sharansingh.solarifyenergy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Edit_Profile extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText ed_1, ed_2, ed_3, ed_4;
    Spinner ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        setTitle("EDIT YOUR PROFILE");

        sharedPreferences = this.getSharedPreferences("com.example.sharansingh.solarifyenergy", MODE_PRIVATE);

        ed_1 = findViewById(R.id.n_light_home);
        ed_2 = findViewById(R.id.n_fan_home);
        ed_3 = findViewById(R.id.n_light_work);
        ed_4 = findViewById(R.id.n_fan_work);

        ref = findViewById(R.id.home_refri_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> ref_adapter = ArrayAdapter.createFromResource(this,
                R.array.refrigerator, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        ref_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ref.setAdapter(ref_adapter);

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n_light_update = Integer.parseInt(ed_1.getText().toString());
                sharedPreferences.edit().putInt("homeLights", n_light_update).apply();
                Toast.makeText(Edit_Profile.this,"Values Changed",Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton imageButton1 = findViewById(R.id.imageButton2);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fan_update = Integer.parseInt(ed_2.getText().toString());
                sharedPreferences.edit().putInt("homeFans", fan_update).apply();
                Toast.makeText(Edit_Profile.this,"Values Changed",Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton imageButton2 = findViewById(R.id.imageButton3);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refri_value = ref.getSelectedItem().toString();
                sharedPreferences.edit().putString("homeRefrigerator",refri_value).apply();
                Toast.makeText(Edit_Profile.this,"Values Changed",Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton imageButton3 = findViewById(R.id.imageButton4);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int light_work = Integer.parseInt(ed_3.getText().toString());
                sharedPreferences.edit().putInt("officeLights",light_work).apply();
                Toast.makeText(Edit_Profile.this,"Values Changed",Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton imageButton4 = findViewById(R.id.imageButton5);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fans_work = Integer.parseInt(ed_4.getText().toString());
                sharedPreferences.edit().putInt("officeFans",fans_work).apply();
                Toast.makeText(Edit_Profile.this,"Values Changed",Toast.LENGTH_SHORT).show();
            }
        });

        Button done_b = findViewById(R.id.done_button);
        done_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Edit_Profile.this,MainActivity.class));
            }
        });
    }
}
