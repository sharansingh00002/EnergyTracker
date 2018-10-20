package com.example.sharansingh.solarifyenergy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartChallenge extends AppCompatActivity {
    TextView tview;
    Button accept;
    String challenge_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_challenge);

        tview = findViewById(R.id.challenge_name);
        accept = findViewById(R.id.accept_challenge);

        Intent intent = getIntent();

        challenge_ = intent.getStringExtra("Challenge_name");
        tview.setText(challenge_);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StartChallenge.this, "You'll receive daily notifications regarding the challenge", Toast.LENGTH_SHORT).show();
            }
        });






    }
}
