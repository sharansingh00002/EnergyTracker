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


/**
 * A simple {@link Fragment} subclass.
 */
public class OthersFragment extends AppCompatActivity {

    String text,energy_consumed;
    private int cost=0,total=0;
    SharedPreferences sharedPref;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextView energy_consumption,energy_title;
    Button submit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_others);
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();

        sharedPref = getSharedPreferences(getString(R.string.sharedPreferencesValue), Context.MODE_PRIVATE);
        databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://solarifyenergy.firebaseio.com/users");
        Spinner activity_spinner = findViewById(R.id.thingsToDo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.othersarray, R.layout.spinner_items);
        // Specify the layout to use when the list of choices appears
        // adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_spinner.setAdapter(adapter2);

        //final TextView topic = v.findViewById(R.id.heading_text);

        energy_consumption = findViewById(R.id.final_energy_consumed);
        energy_title = findViewById(R.id.textView3);
        //final TextView energy_title = v.findViewById(R.id.energy_title);
        submit = findViewById(R.id.submit_b);

        check();
        activity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = parent.getItemAtPosition(position).toString();
                Log.e("info",text);

                //topic.setText("I "+text);
                // topic.setTextSize(24);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.equals("Watched a movie in a Theatre")){
                    energy_consumed = "10Joules";
                    cost=10;
                }else if (text.equals("Went to a Restaurant")){
                    energy_consumed = "8Joules";
                    cost=8;
                }else {
                    energy_consumed = "7Joules";
                    cost=7;
                }
                updateTotal(cost);

                energy_consumption.setText(energy_consumed);

                energy_title.setVisibility(View.VISIBLE);
                energy_consumption.setVisibility(View.VISIBLE);
            }
        });
    }


    public void updateTotal(int saving)
    {
        SharedPreferences.Editor sharedEditor=sharedPref.edit();
        sharedEditor.putInt("OthersTotal",saving);
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
        int val=sharedPref.getInt("OthersTotal",0);
        if(val!=0)
        {
            energy_consumption.setText(val+"Joules");

            energy_title.setVisibility(View.VISIBLE);
            energy_consumption.setVisibility(View.VISIBLE);
        }
    }
}
