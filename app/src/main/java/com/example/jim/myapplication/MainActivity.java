package com.example.jim.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EquipmentListFragment.OnArticleSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

    }
    @Override
    public void onArticleSelected(int position, ArrayList<Equipment> equipments) {
        DetailFragment details = (DetailFragment)getFragmentManager().findFragmentById(R.id.detail_fragment);
        details.showDetails(position, equipments);
    }
}

