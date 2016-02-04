package com.example.jim.myapplication;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Jim on 03/02/2016.
 */
public class Equipment {
    String type, manufacturer, model, loaner_Name;
    boolean onLoan;
    Calendar bought;

    public Equipment(String type, String manufacturer, String model, String loaner_Name, boolean onLoan, Calendar bought){
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
        this.loaner_Name = loaner_Name;
        this.onLoan = onLoan;
        this.bought = bought;
    }
}
