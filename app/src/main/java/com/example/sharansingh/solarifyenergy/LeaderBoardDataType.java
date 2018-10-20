package com.example.sharansingh.solarifyenergy;

import android.support.annotation.NonNull;

public class LeaderBoardDataType implements Comparable {
    String name,value;

    public LeaderBoardDataType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    @Override
    public int compareTo(@NonNull Object o) {
        int value=Integer.parseInt(((LeaderBoardDataType)o).getValue());
        int temp=Integer.parseInt(this.value);
        return value-temp;
    }
}
