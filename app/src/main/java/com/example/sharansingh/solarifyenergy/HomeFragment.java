package com.example.sharansingh.solarifyenergy;

import android.animation.ObjectAnimator;
import android.arch.persistence.room.Room;
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


public class HomeFragment extends AppCompatActivity {
    private SeekBar fanSeekbar, lightSeekbar, othersSeekbar;
    private Button fanButton, lightButton, othersButton;
    private TextView fanCount, lightCount, othersCount, fanName, lightName, othersName, savingsTextView;
    private int savings;
    private int fanMax, lightMax, othersMax, currentFan, currentLight, currentOthers, fanPrice, lightPrice, othersPrice;//max mans how many of thwm are there,current is exsisting one and as for price let it be 1 for now
    private int FAN = 0, LIGHT = 1, OTHERS = 2,total=0;
    SharedPreferences sharedPref;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    SharedPreferences.Editor editor;

    myAppDatabase myDB;
    //Date currentTime;

    //Appliances
    RelativeLayout appliancesRelativeLayout;
    EditText acEditText,geyserEditText,washingMachineEditText;
    Button appliancesSubmitButton;
    int acValue=0,geyserValue=0,washingMachineValue=0;


    //previous values
    int lightUsagePrevious,fanUsagePrevious,acUsagePrevious,geyUsagePrevious,washingMachineUsagePrevious;
    double score=0;
    String houseType="";
    int houseScore=0;
    int lightHours=0,fanHours=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        setTitle("HOME VALUES");

        sharedPref = this.getSharedPreferences("com.example.sharansingh.solarifyenergy",MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://solarifyenergy.firebaseio.com/users");
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        editor=sharedPref.edit();

        appliancesRelativeLayout=findViewById(R.id.appliances);
        acEditText=findViewById(R.id.acEditText);
        geyserEditText=findViewById(R.id.geyserEditText);
        washingMachineEditText=findViewById(R.id.washingMachineEditText);
        appliancesSubmitButton=findViewById(R.id.appliancesSubmitButton);

       // myDB= Room.databaseBuilder(getApplicationContext(),myAppDatabase.class,"dailydata").build();

        appliancesSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acString=acEditText.getText().toString();
                acValue=Integer.parseInt(acString);
                String geyserString=acEditText.getText().toString();
                geyserValue=Integer.parseInt(acString);
                String wmString=acEditText.getText().toString();
                washingMachineValue=Integer.parseInt(acString);
                calcPoints();
                ObjectAnimator.ofFloat(appliancesRelativeLayout,"alpha",0.0f).setDuration(500).start();
                appliancesRelativeLayout.setVisibility(View.GONE);
            }
        });
        /*currentTime = Calendar.getInstance().getTime();
        String time = "" + currentTime;
        String currentTimeVal = time.substring(11, 13);
        if (currentTimeVal.compareTo("12") < 0)
            view.setBackgroundColor(getResources().getColor(R.color.colorDay));
        else view.setBackgroundColor(getResources().getColor(R.color.colorEvening)); */

        //Initializing everything
        fanSeekbar = findViewById(R.id.fanSeekbar);
        lightSeekbar = findViewById(R.id.lightSeekbar);
        othersSeekbar = findViewById(R.id.othersSeekbar);

        fanCount = findViewById(R.id.fan_usage);
        lightCount = findViewById(R.id.light_usage);
        othersCount = findViewById(R.id.other_usage);

        fanButton = findViewById(R.id.fanButton);
        lightButton = findViewById(R.id.lightButton);
        othersButton = findViewById(R.id.othersButton);

        fanName = findViewById(R.id.fanName);
        lightName =findViewById(R.id.lightName);
        othersName = findViewById(R.id.othersName);

        savingsTextView = findViewById(R.id.homeSavings);
        init();
        check();

        initPrevious();
        fanSeekbar.setMax(24);
        lightSeekbar.setMax(24);
        othersSeekbar.setMax(24);

        fanSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fanCount.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lightSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lightCount.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        othersSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                othersCount.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFan < fanMax) {
                    savings += fanSeekbar.getProgress();
                    fanHours += fanSeekbar.getProgress();
                    updateTotal(savings);
                    currentFan=currentFan+1;
                    Log.i("currentfan",currentFan+"");
                    if (currentFan < fanMax)
                    fanName.setText(""+(currentFan+1));
                    editor.putInt("current_fan",currentFan);
                    editor.commit();
                    sharedPref.edit().putInt("fanHours", fanHours).apply();
                    fanSeekbar.setProgress(0);
                    //fanCount.setText("0");
                    //fanName.setText("FAN" + (currentFan + 1));
                    calculate(FAN);
                    calcPoints();
                    Log.i("countVal", currentFan + "");
                    if (currentFan == fanMax) {
                        fanButton.setVisibility(View.INVISIBLE);
                        fanCount.setVisibility(View.INVISIBLE);
                        fanSeekbar.setVisibility(View.INVISIBLE);
                        //fanName.setVisibility(View.GONE);*/
                        Log.i("Info","Fans");
                    }
                } else {
                    fanButton.setVisibility(View.INVISIBLE);
                    fanCount.setVisibility(View.INVISIBLE);
                    fanSeekbar.setVisibility(View.INVISIBLE);
                    // fanName.setVisibility(View.GONE);*/
                    Log.i("Info","Fans_2");
                }
            }
        });


        othersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOthers < othersMax) {
                    savings += othersSeekbar.getProgress();
                    updateTotal(savings);
                    //for now
                    lightHours += othersSeekbar.getProgress();
                    currentOthers=currentOthers+1;
                    if(currentOthers<othersMax)
                    othersName.setText(""+(currentOthers+1));
                    editor.putInt("current_others",currentOthers);
                    editor.commit();

                    othersSeekbar.setProgress(0);
                    //othersCount.setText("0");
                    //othersName.setText("OTHER" + (currentOthers + 1));
                    calculate(OTHERS);
                    Log.i("countVal", currentOthers + "");
                    if (currentOthers == othersMax) {
                        othersButton.setVisibility(View.INVISIBLE);
                        othersCount.setVisibility(View.INVISIBLE);
                        othersSeekbar.setVisibility(View.INVISIBLE);
                        // othersName.setVisibility(View.GONE); */
                    }
                } else {
                    othersButton.setVisibility(View.INVISIBLE);
                    othersCount.setVisibility(View.INVISIBLE);
                    othersSeekbar.setVisibility(View.INVISIBLE);
                    //othersName.setVisibility(View.GONE); */
                }
            }
        });

        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLight < lightMax) {
                    savings += lightSeekbar.getProgress();
                    lightHours += lightSeekbar.getProgress();
                    updateTotal(savings);
                    currentLight=currentLight+1;
                    if (currentLight < lightMax)
                    lightName.setText(""+(currentLight+1));
                    editor.putInt("current_light",currentLight);
                    editor.commit();
                    sharedPref.edit().putInt("lightHours", lightHours).apply();
                    lightSeekbar.setProgress(0);
                    //lightCount.setText("0");
                    //lightName.setText("LIGHT" + (currentLight + 1));
                    calculate(LIGHT);
                    calcPoints();
                    if (currentLight == lightMax) {
                        lightButton.setVisibility(View.INVISIBLE);
                        lightCount.setVisibility(View.INVISIBLE);
                        lightSeekbar.setVisibility(View.INVISIBLE);
                        //lightName.setVisibility(View.GONE);*/
                    }
                } else {
                    lightButton.setVisibility(View.INVISIBLE);
                    lightCount.setVisibility(View.INVISIBLE);
                    lightSeekbar.setVisibility(View.INVISIBLE);
                    //lightName.setVisibility(View.GONE); */
                }
            }
        });
    }

    public void initPrevious() {

         lightUsagePrevious = sharedPref.getInt("lightUsagePrevi", 0);
         fanUsagePrevious = sharedPref.getInt("fanUsagePrevi", 0);
         acUsagePrevious = sharedPref.getInt("acUsagePrevi", 0);
         geyUsagePrevious = sharedPref.getInt("geyserUsagePrevi", 0);
         washingMachineUsagePrevious = sharedPref.getInt("washingMachineUsagePrevi", 0);
         lightHours=sharedPref.getInt("lightHours",0);
         fanHours=sharedPref.getInt("fanHours",0);
        houseType = sharedPref.getString("homeType", "One_bhk");
        houseScore = 0;
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

         //score = calculate(houseType, lightUsagePrevious, fanUsagePrevious, acUsagePrevious, geyUsagePrevious, washingMachineUsagePrevious);

    }

    private void init() {
        fanMax=sharedPref.getInt("homeFans",5);
        lightMax=sharedPref.getInt("homeLights",5);
        othersMax=sharedPref.getInt("others_count",5);

        currentFan = sharedPref.getInt("current_fan",0);
        savings = 0;
        currentLight =sharedPref.getInt("current_light",0);
        currentOthers=sharedPref.getInt("current_others",0);
        fanPrice = 1;
        lightPrice = 1;
        othersPrice = 1;

        savings=sharedPref.getInt("HomeTotal",0)+sharedPref.getInt("OthersTotal",0)+sharedPref.getInt("WorkTotal",0)+sharedPref.getInt("TravelTotal",0);
        savingsTextView.setText("Power consumption in Home: "+ savings);
    }

    public void calculate(int tag) {
        switch (tag) {
            case 0:
                savings = savings + (fanSeekbar.getProgress() * fanPrice);
                savingsTextView.setText("Power consumption in Home: "+ savings);
                break;
            case 1:
                savings = savings + (lightSeekbar.getProgress() * lightPrice);
                savingsTextView.setText("Power consumption in Home: " + savings);
                break;
            case 2:
                savings = savings + (othersSeekbar.getProgress() * othersPrice);
                savingsTextView.setText("Power consumption in Home: "+ savings);
                break;
        }
    }

    public void calcPoints()
    {
        double lightconsumption = ((lightHours - lightUsagePrevious) * 40) / (houseScore * 0.75);
        double fanconsumption = ((fanHours - fanUsagePrevious) * 75) / (houseScore * 0.75);
        double acconsumption = ((acValue - acUsagePrevious) * 150) / (houseScore * 0.5);
        double geyserconsumption = ((geyserValue - geyUsagePrevious) * 2000) / (houseScore * 0.1);
        double wmconsumption = ((washingMachineValue - washingMachineUsagePrevious) * 750) / (houseScore * 0.3);
        Log.i("consumptionvalues",lightconsumption+"  "+fanconsumption+"  "+acconsumption+"  "+geyserconsumption+"  "+wmconsumption);
        double tracking_points = lightconsumption + fanconsumption + acconsumption + wmconsumption + geyserconsumption;
        double final_score = tracking_points * 100;
        final_score=houseScore-final_score;
        final_score=final_score/10;
        float val=Float.parseFloat(""+final_score);
        sharedPref.edit().putInt("lightUsagePrev", lightHours).apply();
        sharedPref.edit().putInt("fanUsagePrev", fanHours).apply();
        sharedPref.edit().putInt("acUsagePrev", acValue).apply();
        sharedPref.edit().putInt("geyserUsagePrev", geyserValue).apply();
        sharedPref.edit().putInt("washingMachineUsed", washingMachineValue).apply();
        sharedPref.edit().putFloat("housePoints",val).apply();
        Log.i("totalpoints",""+val);
    }


    /*public void setColor() {
        currentTime = Calendar.getInstance().getTime();
        String time = "" + currentTime;
        String currentTimeVal = time.substring(11, 13);
        if (currentTimeVal.compareTo("12") < 0)
            fragment.getView().setBackgroundColor(getResources().getColor(R.color.colorDay));
        else fragment.getView().setBackgroundColor(getResources().getColor(R.color.colorEvening));
    }*/

    public void updateTotal(int saving)
    {
        SharedPreferences.Editor sharedEditor=sharedPref.edit();
        sharedEditor.putInt("HomeTotal",saving);
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

        lightName.setText(""+(currentLight+1));

        fanName.setText(""+(currentFan+1));
        othersName.setText(""+(currentOthers+1));

        if (currentLight == lightMax || currentLight>lightMax) {
            // Log.i("current Light",currentLight+"");
            lightButton.setVisibility(View.INVISIBLE);
            lightCount.setVisibility(View.INVISIBLE);
            lightSeekbar.setVisibility(View.INVISIBLE);

        }
        if (currentFan == fanMax || currentFan>fanMax) {
            fanButton.setVisibility(View.INVISIBLE);
            fanCount.setVisibility(View.INVISIBLE);
            fanSeekbar.setVisibility(View.INVISIBLE);

        }
        if (currentOthers == othersMax || currentOthers>othersMax) {
            othersButton.setVisibility(View.INVISIBLE);
            othersCount.setVisibility(View.INVISIBLE);
            othersSeekbar.setVisibility(View.INVISIBLE);

        }
    }

    public void appliancesOpen(View v)
    {
        appliancesRelativeLayout.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(appliancesRelativeLayout,"alpha",1.0f).setDuration(500).start();

    }
}
