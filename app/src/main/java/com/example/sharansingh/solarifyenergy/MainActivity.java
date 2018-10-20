package com.example.sharansingh.solarifyenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {



/*
* sqft of forest saved ,kg of 02,kms of car travel avoided
* */
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    //private FragmentAdapter mPagerAdapter;
    SharedPreferences sharedPref;
    TextView userName;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle mToggleButton;

    private TextView homeText,workText,travelText,othersText,scoreValue,forestSaved,co2Saved,kmSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://solarifyenergy.firebaseio.com/users");

        homeText=findViewById(R.id.homeValue);
        workText=findViewById(R.id.workValue);
        travelText=findViewById(R.id.travelValue);
        othersText=findViewById(R.id.othersValue);
        scoreValue=findViewById(R.id.scoreValue);
        forestSaved=findViewById(R.id.forestSaved);
        co2Saved=findViewById(R.id.co2Saved);
        kmSaved=findViewById(R.id.kmTraveled);

        sharedPref = this.getSharedPreferences("com.example.sharansingh.solarifyenergy",MODE_PRIVATE);

        init();
/*
        mViewPager = (ViewPager) findViewById(R.id.main_viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);

        mPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager); */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Date todayDate;
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //do something
                Log.i("dateAndPos",date+"");
            }
        });
        navInit();

        if(mAuth.getCurrentUser()!=null)
        {
            Log.i("logindetails","loggedin");
        }
        else
        {
          Intent intent=new Intent(MainActivity.this,Activity_First.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(intent);
          finish();
        }

    }

    private void initializeTextViews() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggleButton.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.menu_logout:
                mAuth.signOut();
                resetValues();
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Activity_First.class));
                finish();
                break;
            case R.id.menu_profile:
                startActivity(new Intent(MainActivity.this, Profile.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void resetValues() {
    SharedPreferences.Editor editor=sharedPref.edit();
    editor.putInt("current_fan",0);
    editor.putInt("current_light",0);
    editor.putInt("current_others",0);
    editor.putString("work_comp","not_done");
    editor.putString("work_others","not_done");
    editor.putString("work_cof","not_done");
    editor.putInt("HomeTotal",0);editor.putInt("OthersTotal",0);editor.putInt("WorkTotal",0);editor.putInt("TravelTotal",0);
    editor.putFloat("housePoints",0);
    editor.putFloat("workPoints",0);
    editor.commit();
    }


    public void navInit() {
        navigationView = findViewById(R.id.navigation);
        View v = navigationView.getHeaderView(0);
        userName=(TextView)v.findViewById(R.id.userName);

        userName.setText(sharedPref.getString("user_name",""));
        drawerLayout = findViewById(R.id.drawer_layout);

        mToggleButton = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.closed);
        drawerLayout.addDrawerListener(mToggleButton);
        mToggleButton.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(MainActivity.this,HomeFragment.class));
                        break;
                    case R.id.work:
                        startActivity(new Intent(MainActivity.this,WorkFragment.class));
                        break;
                    case R.id.travel:
                        startActivity(new Intent(MainActivity.this,TravelFragment.class));
                        break;
                    case R.id.other:
                        startActivity(new Intent(MainActivity.this,OthersFragment.class));
                        break;
                    case R.id.challenges:
                        startActivity(new Intent(MainActivity.this,Challenges.class));break;
                    case R.id.Settings:
                        startActivity(new Intent(MainActivity.this,Profile.class));break;
                    case R.id.leaderboard:
                        startActivity(new Intent(MainActivity.this,Leaderboard.class));
                }
                return false;
            }
        });


    }
    public void init()
    {
        int value=sharedPref.getInt("HomeTotal",0);
        homeText.setText("Home Consumption : "+value);
        workText.setText("Work Consumption   : "+sharedPref.getInt("WorkTotal",0));
        travelText.setText("Travel Consumption : "+sharedPref.getInt("TravelTotal",0));
        othersText.setText("Others Consumption: "+sharedPref.getInt("OthersTotal",0));
        int total=sharedPref.getInt("HomeTotal",0)+sharedPref.getInt("OthersTotal",0)+sharedPref.getInt("WorkTotal",0)+sharedPref.getInt("TravelTotal",0);
      //  totalText.setText(""+total);
    }

    @Override
    protected void onResume() {
        super.onResume();
        float homeValue=sharedPref.getFloat("housePoints",0);
        String formatted = String.format(Locale.getDefault(),"%.2f",homeValue);
        homeText.setText("Home Points : "+formatted);

        float travelValue=sharedPref.getFloat("travelPoints",0);
        formatted = String.format(Locale.getDefault(),"%.2f",travelValue);
        travelText.setText("Travel Points : "+formatted);

       // int workValue=sharedPref.getInt("WorkTotal",0);
        float workValue=sharedPref.getFloat("workPoints",0);
         formatted = String.format(Locale.getDefault(),"%.2f",workValue);
        workText.setText("Work Points   : "+formatted);

        int othersValue=sharedPref.getInt("OthersTotal",0);
        float othersValues=Float.parseFloat(othersValue+".00");
        formatted = String.format(Locale.getDefault(),"%.2f",othersValues);
        othersText.setText("Others Points: "+formatted);

        float totValue=homeValue+travelValue+workValue+othersValue;
        formatted = String.format(Locale.getDefault(),"%.2f",totValue);

        scoreValue.setText(""+formatted);
        updateTotal(formatted);
        double forestValue=totValue*0.000026;
        double co2Value=totValue*0.000036;
        double kmValue=totValue*0.00293;

        forestSaved.setText(""+forestValue);
        co2Saved.setText(""+co2Value);
        kmSaved.setText(""+kmValue);
    }

    private void updateTotal(String formatted) {
        float total=Float.parseFloat(formatted);
        int tot=(int)total;
        String name=sharedPref.getString("user_name","SHARAN");
        Log.i("SHARAN",""+total);
        String uid=mAuth.getUid().toString();
        databaseReference.child(uid).child("total").setValue(tot);
        databaseReference.child(uid).child("name").setValue(name);
    }


}
