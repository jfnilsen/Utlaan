package com.example.jim.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    ArrayList<Equipment> equipmentList;
    EquipmentAdapter myAdapterInstance;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        ListView myListView = (ListView)findViewById(R.id.equipmentList);
        equipmentList = new ArrayList<>();
        int layoutID = android.R.layout.simple_list_item_1;
        myAdapterInstance = new EquipmentAdapter(this, layoutID, equipmentList);

        myListView.setAdapter(myAdapterInstance);
    }

    public void addItem(View view) {
        equipmentList.add(new Equipment("Type" + ++i,"Manifact","Model", "Loanname",true, new GregorianCalendar()));
        myAdapterInstance.notifyDataSetChanged();
    }
}
