package com.example.user.navigationdrawer;

import android.widget.DatePicker;

public class UserModel {
    public String name;
    public int iconDel,iconCor;

    boolean checked;
    int day,month,year;
    UserModel(String name,int iconCor,int iconDel,boolean checked,int day,int month,int year)
    {
        this.name=name;
        this.iconCor=iconCor;
        this.iconDel=iconDel;
        this.checked=checked;
        this.day=day;
        this.month=month;
        this.year=year;
    }
}
