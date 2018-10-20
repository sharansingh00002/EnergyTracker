package com.example.sharansingh.solarifyenergy;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TravelFragment extends AppCompatActivity {

    private Spinner spinner;

    private int[] cost;
    private int totalCost;
    private int distance;
    private int count=0,total=0;
    private TextView travelSavings;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    public TravelFragment() {
        // Required empty public constructor
    }
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_travel);

        FirebaseApp.initializeApp(this);
        databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://solarifyenergy.firebaseio.com/users");
        firebaseAuth=FirebaseAuth.getInstance();


        sharedPref = getSharedPreferences(getString(R.string.sharedPreferencesValue), Context.MODE_PRIVATE);
        cost=new int[10];
        totalCost=0;

        travelSavings=(TextView)findViewById(R.id.travelSavings);
        spinner=(Spinner)findViewById(R.id.travelSpinner);

        init();
        check();

        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this,R.array.travel_values,R.layout.spinner_items);
        //   arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(count>0) {

                    totalCost += cost[position] * distance;
                    updateTotal(totalCost);
                   // travelSavings.setText("Travel Savings  :" + totalCost);
                    calculate();
                    spinner.setVisibility(View.GONE);
                }
                count++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {
        distance=sharedPref.getInt("distance",30);
        for(int i=0;i<10;i++)
            cost[i]=1;
        cost[0]=7;cost[1]=10;cost[2]=9;cost[3]=8;
    }

    public void updateTotal(int saving)
    {
        SharedPreferences.Editor sharedEditor=sharedPref.edit();
        sharedEditor.putInt("TravelTotal",saving);
        sharedEditor.commit();
        String name=sharedPref.getString("user_name","SHARAN");
        total=sharedPref.getInt("HomeTotal",0)+sharedPref.getInt("OthersTotal",0)+sharedPref.getInt("WorkTotal",0)+sharedPref.getInt("TravelTotal",0);
        Log.i("SHARAN",""+total);
        String uid=firebaseAuth.getUid().toString();
        databaseReference.child(uid).child("total").setValue(total);
        databaseReference.child(uid).child("name").setValue(name);
    }

    public void check()
    {
        int val=sharedPref.getInt("TravelTotal",0);
        if(val!=0)
        {
            spinner.setVisibility(View.GONE);
            travelSavings.setText("Travel Savings  :" + val);
        }
    }
    public void calculate() {
        int previousTravelCost = sharedPref.getInt("previousDayDistance", 300);
        previousTravelCost=300;
        int presentDistanceTravelled = totalCost;

        float tracking_score = (presentDistanceTravelled - previousTravelCost);
        float final_points = tracking_score * -100;
        String final_point = String.format(Locale.getDefault(),"%.2f",final_points);
        sharedPref.edit().putFloat("TravelPoints", final_points).apply();

        sharedPref.edit().putInt("previousDayDistance", presentDistanceTravelled).apply();
        tracking_score=tracking_score*(-1) ;
        travelSavings.setText("Travel Savings  :" + tracking_score);
        sharedPref.edit().putFloat("travelPoints",tracking_score/2).apply();
        Log.i("finalpoints",final_points+"  "+presentDistanceTravelled+"  "+tracking_score+"  "+previousTravelCost);
    }

}
