package com.example.user.navigationdrawer;

import android.widget.DatePicker;

public class ReturnUser {
    String name;
    DatePicker date;
    boolean checked;
    ReturnUser(String name,DatePicker date,boolean checked)
    {
        this.name=name;
        this.date=date;
        this.checked=checked;
    }
}
