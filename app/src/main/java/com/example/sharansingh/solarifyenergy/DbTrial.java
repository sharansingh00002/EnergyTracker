package com.example.sharansingh.solarifyenergy;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

public class DbTrial extends AppCompatActivity {

    myAppDatabase myDB;
    List<DailyData> dailyData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_trial);

        myDB= Room.databaseBuilder(this,myAppDatabase.class,"DailyData").allowMainThreadQueries().build();

    }

    public void insertVal(View view)
    {
        int id=0,home=1,work=2;
        DailyData dd=new DailyData();
        dd.setId(id);
        dd.setHome(home);
        dd.setWork(work);

        myDB.myDao().add(dd);


    }

    public void viewVal(View view)
    {

        dailyData=myDB.myDao().getAll();
        String check="";

        for(DailyData ddvalue:dailyData)
        {
            int work=ddvalue.getWork();
            int home=ddvalue.getHome();
            int id=ddvalue.getId();
            check=""+work+" "+home+" "+id;
        }

        Log.i("dailyDAtaa",check+"");
    }
}
