package com.example.sharansingh.solarifyenergy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ImageView edit;
    AlertDialog levelDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        setTitle("My Profile");

        edit = findViewById(R.id.edit_option);

        sharedPreferences = this.getSharedPreferences("com.example.sharansingh.solarifyenergy", MODE_PRIVATE);

        String username = sharedPreferences.getString("user_name", "null");

        int home_Lights = sharedPreferences.getInt("homeLights", 0);
        int home_Fans = sharedPreferences.getInt("homeFans", 0);
        int office_Lights = sharedPreferences.getInt("officeLights", 0);
        int  office_Fans = sharedPreferences.getInt("officeFans", 0);

        String refrigerator = sharedPreferences.getString("homeRefrigerator", "null");

        TextView homelight = findViewById(R.id.homeLights);
        TextView homeFans = findViewById(R.id.homeFans);
        TextView homeOthers = findViewById(R.id.homeOthers);
        /*
        Log.i("Profile-1", home_Lights);
        Log.i("Profile-2", home_Fans);
        Log.i("Profile-3", office_Lights);
        Log.i("Profile-4", office_Fans); */

        homelight.setText(String.valueOf(home_Lights));
        homeFans.setText(String.valueOf(home_Fans));
        homeOthers.setText(String.valueOf(refrigerator));

        TextView officeLight = findViewById(R.id.officeLights);
        TextView officeFans = findViewById(R.id.officeFans);

        officeLight.setText(String.valueOf(office_Lights));
        officeFans.setText(String.valueOf(office_Fans));

        TextView name_t = findViewById(R.id.name_text);
        name_t.setText(username + "'s profile");

       /* final LinearLayout edit_layout = new LinearLayout(this);
        edit_layout.setOrientation(LinearLayout.VERTICAL);
        final RadioGroup rg = new RadioGroup(this);

        RadioButton rb1 = new RadioButton(this);
        RadioButton rb2 = new RadioButton(this);
        rb1.setText(R.string.radio_one);
        rb2.setText(R.string.radio_two);
        edit_layout.addView(rg); */
        final String[] items = {"HOME", "OFFICE"};


        final LinearLayout appliances = new LinearLayout(this);
        appliances.setOrientation(LinearLayout.VERTICAL);

        TextView t1 = new TextView(this);
        t1.setText("NO. OF LIGHTS");
        t1.setTextSize(12);

        EditText ed1 = new EditText(this);
        ed1.setHint("No.of lights");

        TextView t2 = new TextView(this);
        t2.setText("NO. OF FANS");
        t2.setTextSize(12);

        EditText ed2 = new EditText(this);
        ed2.setHint("No.of fans");

        appliances.addView(t1);
        appliances.addView(ed1);
        appliances.addView(t2);
        appliances.addView(ed2);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,Edit_Profile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
