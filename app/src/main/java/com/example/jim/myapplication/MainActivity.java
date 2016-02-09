package com.example.jim.myapplication;

import android.app.AlertDialog;
import android.app.FragmentTransaction;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EquipmentListFragment.OnArticleSelectedListener {

    private int sortOption = 0;
    private int statusOption = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            EquipmentListFragment fragment = new EquipmentListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_frame, fragment);
            transaction.commit();
        }

        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                AlertDialog.Builder settingsBuilder = new AlertDialog.Builder(this);
                settingsBuilder.setTitle(R.string.action_settings);

                CharSequence[] options = new CharSequence[] {getString(R.string.sort), getString(R.string.equipment_status)};
                settingsBuilder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                showSortSettings();
                                break;
                            case 1:
                                showStatusSettings();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                settingsBuilder.show();

                return true;

            case R.id.exit_button:
                this.finish();
                return true;

            case R.id.about:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(R.string.about);
                alertDialog.setMessage(getString(R.string.about_message));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showSortSettings() {
        AlertDialog.Builder settingsBuilder = new AlertDialog.Builder(this);
        settingsBuilder.setTitle(R.string.sort_by);

        CharSequence[] options = new CharSequence[] {getString(R.string.itNo), getString(R.string.type), getString(R.string.brand), getString(R.string.model)};
        settingsBuilder.setSingleChoiceItems(options, sortOption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sortOption = which;
                replaceListFragment();
                dialog.dismiss();
            }
        });
        settingsBuilder.show();
    }

    private void showStatusSettings() {
        AlertDialog.Builder settingsBuilder = new AlertDialog.Builder(this);
        settingsBuilder.setTitle(R.string.filter_by);

        CharSequence[] options = new CharSequence[] {getString(R.string.all), getString(R.string.available), getString(R.string.in_use)};
        settingsBuilder.setSingleChoiceItems(options, statusOption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                statusOption = which;
                replaceListFragment();
                dialog.dismiss();
            }
        });
        settingsBuilder.show();
    }

    private void replaceListFragment() {
        EquipmentListFragment fragment = new EquipmentListFragment();
        Bundle bundle = new Bundle();

        switch (statusOption){
            case 0:
                bundle.putString("filter", "&which_equipment=ALL");
                break;
            case 1:
                bundle.putString("filter", "&which_equipment=AVAILABLE");
                break;
            case 2:
                bundle.putString("filter", "&which_equipment=IN_USE");
                break;

        }
        switch (sortOption){
            case 0:
                bundle.putString("sort", "?sort_by=it_no");
                break;
            case 1:
                bundle.putString("sort", "?sort_by=type");
                break;
            case 2:
                bundle.putString("sort", "?sort_by=brand");
                break;
            case 3:
                bundle.putString("sort", "?sort_by=model");
                break;

        }

        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame, fragment);
        transaction.commit();
    }



    @Override
    public void onArticleSelected(int position, ArrayList<Equipment> equipments) {
        DetailFragment details = new DetailFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FrameLayout detailFrame = (FrameLayout)findViewById(R.id.detail_frame);
        detailFrame.setVisibility(View.VISIBLE);

        transaction.replace(R.id.detail_frame, details);
        transaction.commit();
        getFragmentManager().executePendingTransactions();

        details.showDetails(position, equipments);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.details_exit:
                findViewById(R.id.detail_frame).setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("sort", sortOption);
        outState.putInt("filter", statusOption);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sortOption = savedInstanceState.getInt("sort");
        statusOption = savedInstanceState.getInt("filter");
    }
}

