package com.example.sharansingh.solarifyenergy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PreSurvey1 extends AppCompatActivity {
    private TextView textView;
    private Button button;
    String text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_between_pages);
        textView=findViewById(R.id.surveyText);
        button=findViewById(R.id.nextPage);
        init();
        textView.setText(text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreSurvey1.this,PreSurvey2.class));
            }
        });
    }

    private void init() {
        text="We use a lot of Energy on a day to day basis." +
                "More Energy = More Carbon footprint." +
                "Saving Energy is Saving Environment. ";
    }
}
