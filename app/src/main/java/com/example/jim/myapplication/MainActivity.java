package com.example.jim.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EquipmentListFragment.OnArticleSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

 /*       FragmentTransaction transaction = getFragmentManager().beginTransaction();
        DetailFragment fragment = new DetailFragment();
        transaction.add(R.id.detail_container,fragment);
        transaction.commit();*/

        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

    }
    @Override
    public void onArticleSelected(int position, ArrayList<Equipment> equipments) {
        DetailFragment details = (DetailFragment)getFragmentManager().findFragmentById(R.id.detail_fragment);
        if(details != null) {
            details.showDetails(position, equipments);
        }


    }
}

