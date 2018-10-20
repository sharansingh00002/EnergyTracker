package com.example.sharansingh.solarifyenergy;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "dailydata")
public class DailyData {

    @PrimaryKey
    private int id;

    private int home,work;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }


}
