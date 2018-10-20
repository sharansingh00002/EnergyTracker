package com.example.sharansingh.solarifyenergy;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;

public class WorkFragment extends AppCompatActivity {
    int i;
    SeekBar pcSeekbar, cofSeekBar, othersSeekBar;
    Button b1, b2, b3;
    TextView t1, t2, t3,t4;
    int work_savings,total=0;
    SharedPreferences sharedPref;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    SharedPreferences.Editor editor;

    //appliances
    RelativeLayout appliancesRelativeLayout;
    EditText elevatorEditText,kettleEditText;
    Button appliancesSubmitButton;
    //new values

    int othersValue=0,compValue=0,cofValue=0;

    float elevatorValue = 0.0f;
    int kettleValue = 0;
    int lightPowerConsumption, fanPowerConsumption, compPowerConsumption = 1000, liftPowerConsumption = 7500, coffeeMakerPowerConsumption = 400, kettlePowerConsumption = 1500, mobilePowerConsumption = 5, laptopPowerConsumption = 20;
    int lightsUsedFor, timesCoffeeMakerUsed = 0, timesKettleUsed, mobileChargedFor=3, laptopChargedFor=3, compUsedFor;
    float timesLiftUsed, liftUsagePrev;
    String maxFanType, maxLightType;
    String typeOfOffice;
    //SharedPreferences.Editor editor;
    int officeTypePower;
    int houseScore;
    boolean flag = false;
    DateFormat sdf;
    int mobileUsagePrev, laptopUsagePrev, coffeeUsagePrev, kettleUsagePrev, compUsagePrev;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_work);
        setTitle("WORK EQUIPMENTS");

        sharedPref = this.getSharedPreferences("com.example.sharansingh.solarifyenergy",MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://solarifyenergy.firebaseio.com/users");
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        editor=sharedPref.edit();

        appliancesRelativeLayout=findViewById(R.id.appliances);
        elevatorEditText=findViewById(R.id.elevatorEditText);
        kettleEditText=findViewById(R.id.kettleEditText);
        appliancesSubmitButton=findViewById(R.id.appliancesSubmitButton);


        work_savings = 0;

        pcSeekbar = findViewById(R.id.pcSeekbar);
        cofSeekBar = findViewById(R.id.coSeekbar);
        othersSeekBar = findViewById(R.id.othersSeekbar);

        b1 = findViewById(R.id.button2);
        b2 = findViewById(R.id.button3);
        b3 = findViewById(R.id.button4);

        t1 = findViewById(R.id.com_users);
        t2 = findViewById(R.id.cof_users);
        t3 = findViewById(R.id.others_users);
        t4 = findViewById(R.id.energy_co);

        pcSeekbar.setMax(24);
        cofSeekBar.setMax(24);
        othersSeekBar.setMax(24);

        check();
        initPrev();


        appliancesSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String elevatorString=elevatorEditText.getText().toString();
                elevatorValue=Float.parseFloat(elevatorString);
                if(elevatorValue>0){
                    elevatorEditText.setText("0");
                    sharedPref.edit().putString("elevatorValue","done").apply();}

                String kettleString=kettleEditText.getText().toString();
                kettleValue=Integer.parseInt(kettleString);
                if(kettleValue>0){
                    kettleEditText.setText("0");
                    sharedPref.edit().putString("kettleValue","done").apply();

                }

                ObjectAnimator.ofFloat(appliancesRelativeLayout,"alpha",0.0f).setDuration(500).start();
                appliancesRelativeLayout.setVisibility(View.GONE);
                check();
                getScore();
            }
        });

        pcSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                t1.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cofSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                t2.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        othersSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                t3.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int comp_hours = Integer.parseInt(t1.getText().toString());
                compValue=comp_hours;
                work_savings += comp_hours;
                Log.i("COMP_HOURS", String.valueOf(comp_hours));
                updateText(work_savings);
                updateTotal(work_savings);
                pcSeekbar.setProgress(0);
                sharedPref.edit().putString("work_comp","done").apply();
                check();
                getScore();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cof_times = Integer.parseInt(t2.getText().toString());
                cofValue=cof_times;
                Log.i("COF_TIMES", String.valueOf(cof_times));
                work_savings += cof_times;
                updateText(work_savings);
                updateTotal(work_savings);
                cofSeekBar.setProgress(0);
                sharedPref.edit().putString("work_cof","done").apply();
                check();
                getScore();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int other_hours = Integer.parseInt(t3.getText().toString());
                othersValue=other_hours;
                Log.i("OTHER_TIMES", String.valueOf(other_hours));
                work_savings += other_hours;
                updateText(work_savings);
                updateTotal(work_savings);
                othersSeekBar.setProgress(0);
                sharedPref.edit().putString("work_others","done").apply();
                check();
                getScore();
            }
        });

    }

    public void getScore()
    {

        double totalCompUsage = ((compValue - compUsagePrev) * compPowerConsumption) / (houseScore * 0.75);
        double totalLiftUsage = ((elevatorValue - liftUsagePrev) * liftPowerConsumption) / (houseScore * 0.5);
        double totalCoffeeUsage = ((cofValue - coffeeUsagePrev) * coffeeMakerPowerConsumption) / (houseScore * 0.75);
        double totalKettleUsage = ((kettleValue - kettleUsagePrev) * kettlePowerConsumption) / (houseScore * 0.6);
        double totalMobileUsage = (mobileChargedFor * mobilePowerConsumption) / (houseScore * 0.9);
        double totalLaptopUsage = (laptopChargedFor * laptopPowerConsumption) / (houseScore * 0.9);

        double trackingconversion = totalCompUsage + totalMobileUsage + totalLaptopUsage + totalLiftUsage + totalCoffeeUsage + totalKettleUsage;
        double finalPoints = trackingconversion * 100;
        finalPoints=houseScore-finalPoints;
        finalPoints=finalPoints/10;
        Log.i("finalPoints",finalPoints+"");
        sharedPref.edit().putInt("fanUsagePrevi", lightsUsedFor).apply();
        sharedPref.edit().putInt("mobileUsagePrevi", mobileChargedFor).apply();
        sharedPref.edit().putInt("laptopUsagePrevi", laptopChargedFor).apply();
        //sharedPref.edit().putDouble("liftUsagePrev", timesLiftUsed).apply();
        sharedPref.edit().putFloat("liftUsagePrevi", timesLiftUsed).apply();
        sharedPref.edit().putInt("coffeeUsagePrevi", timesCoffeeMakerUsed).apply();
        sharedPref.edit().putInt("kettleUsagePrevi", timesKettleUsed).apply();

        float doublePoints=Float.parseFloat(""+finalPoints);
        Log.i("doublepointsvalue",doublePoints+"  "+finalPoints);
        sharedPref.edit().putFloat("workPoints",doublePoints).apply();
    }

    public void initPrev()
    {
        compUsagePrev = sharedPref.getInt("compUsagePrevi", 6);
        mobileUsagePrev = sharedPref.getInt("mobileUsagePrevi", 1);
        laptopUsagePrev = sharedPref.getInt("laptopUsagePrevi", 2);
        liftUsagePrev = sharedPref.getFloat("liftUsagePrevi", 0.6f);
        coffeeUsagePrev = sharedPref.getInt("coffeeUsagePrevi", 10);
        kettleUsagePrev = sharedPref.getInt("kettleUsagePrevi", 9);


        typeOfOffice = sharedPref.getString("typeOfOffice", "Start Up");
        switch (typeOfOffice) {
            case "Co-Working":
                officeTypePower = 1600;
                break;
            case "MNC":
                officeTypePower = 3000;
                break;
            case "WorkFromHome":
                String houseType = sharedPref.getString("homeType", "One_bhk");
                switch (houseType) {
                    case "One_bhk":
                        houseScore = 1400;
                        break;
                    case "Two_bhk":
                        houseScore = 1600;
                        break;
                    case "Three_bhk":
                        houseScore = 1800;
                        break;
                    case "Duplex":
                        houseScore = 2000;
                        break;
                    case "Villa":
                        houseScore = 4000;
                        break;
                }

                officeTypePower = houseScore;
                break;
            case "Shared Office":
                officeTypePower = 1400;
                break;
            default:
                officeTypePower = 1500;

        }
        houseScore=officeTypePower;
    }

    public void updateText(int work_savings){
        t4.setText("Energy Consumed: "+work_savings);
    }
    public void updateTotal(int saving)
    {
        SharedPreferences.Editor sharedEditor=sharedPref.edit();
        sharedEditor.putInt("WorkTotal",saving);
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
        int val=sharedPref.getInt("WorkTotal",0);
        if(val!=0)
        {
            t4.setText(val+"Joules");
        }
        String compvalue=sharedPref.getString("work_comp","not_done");
        if(compvalue.compareTo("done")==0)
        {
            pcSeekbar.setVisibility(View.GONE);
            b1.setVisibility(View.GONE);
            t1.setVisibility(View.GONE);
        }
        String cofvalue=sharedPref.getString("work_cof","not_done");
        if(cofvalue.compareTo("done")==0)
        {
            cofSeekBar.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
        }
        String othersvalue=sharedPref.getString("work_others","not_done");
        if(othersvalue.compareTo("done")==0)
        {
            othersSeekBar.setVisibility(View.GONE);
            b3.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
        }
        String elevatorvalue=sharedPref.getString("elevatorValue","not_done");
        if(elevatorvalue.compareTo("done")==0)
        {
            elevatorEditText.setVisibility(View.GONE);
        }
        String kettlevalue=sharedPref.getString("kettleValue","not_done");
        if(kettlevalue.compareTo("done")==0)
        {
            kettleEditText.setVisibility(View.GONE);
        }

    }

    public void appliancesOpen(View v)
    {
        appliancesRelativeLayout.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(appliancesRelativeLayout,"alpha",1.0f).setDuration(500).start();

    }
}


