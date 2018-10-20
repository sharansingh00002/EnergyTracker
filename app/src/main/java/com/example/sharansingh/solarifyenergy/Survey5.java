package com.example.sharansingh.solarifyenergy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class Survey5 extends AppCompatActivity {

    CheckBox acCheckBox,washingMachineCheckBox,geyserCheckBox,liftCheckBox,kettleCheckBox;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedEditor;
    Boolean ac,washingMachine,geyser,lift,kettle;
    Spinner homeSpinner;
    String homeTypeString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey5);
        initCheckBoxes();
        homeSpinner=findViewById(R.id.homeTypeSpinner);
        sharedPref=this.getSharedPreferences("com.example.sharansingh.solarifyenergy",MODE_PRIVATE);
        sharedEditor=sharedPref.edit();

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.homeTypeArray, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        homeSpinner.setAdapter(arrayAdapter);

    }

    private void initCheckBoxes() {
        acCheckBox=findViewById(R.id.acCheckBox);
        washingMachineCheckBox=findViewById(R.id.washingMachineCheckBox);
        geyserCheckBox=findViewById(R.id.geyserCheckBox);
        liftCheckBox=findViewById(R.id.liftCheckBox);
        kettleCheckBox=findViewById(R.id.kettleCheckBox);
    }

    public void finish(View view) {
        ac=acCheckBox.isChecked();
        washingMachine=washingMachineCheckBox.isChecked();
        geyser=geyserCheckBox.isChecked();
        lift=liftCheckBox.isChecked();
        kettle=kettleCheckBox.isChecked();

        homeTypeString=homeSpinner.getSelectedItem().toString();

        sharedEditor.putBoolean("ac",ac);
        sharedEditor.putBoolean("washingMachine",washingMachine);
        sharedEditor.putBoolean("geyser",geyser);
        sharedEditor.putBoolean("lift",lift);
        sharedEditor.putBoolean("kettle",kettle);
        sharedEditor.putString("homeType",homeTypeString);
        sharedEditor.commit();

        Log.i("valuesss",ac+" "+washingMachine+" "+geyser+" "+lift+" "+kettle+" "+homeTypeString);
        startActivity(new Intent(Survey5.this,MainActivity.class));
        finish();

    }
}
