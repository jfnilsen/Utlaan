package com.example.jim.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    ArrayList<Equipment> equipmentList;
    String jsonString;
    EquipmentAdapter myAdapterInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        connectToJSON("?sort_by=it_no");
        ListView myListView = (ListView)findViewById(R.id.equipmentList);
        equipmentList = new ArrayList<>();
        int layoutID = R.layout.list_item;
        myAdapterInstance = new EquipmentAdapter(this, layoutID, equipmentList);

        myListView.setAdapter(myAdapterInstance);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("json", jsonString);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        jsonString = savedInstanceState.getString("json");
        createArrayList(jsonString);
    }

    private void connectToJSON(final String params) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    String jsonURL =  "http://kark.hin.no:8088/d3330log_backend/getTestEquipment" + params;
                    HttpURLConnection connection = null;
                    URL url = null;
                    try {
                        url = new URL(jsonURL);

                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestProperty("Content-Type" , "text/plain; charset-utf-8");
                        int responseCode = connection.getResponseCode();

                        if(responseCode == HttpURLConnection.HTTP_OK) {
                            readServerResponse(connection.getInputStream());
                        }else {
                            Log.d("MyTag", "HTTP error code: " + responseCode);
                        }
                    } catch (MalformedURLException e) {
                        Log.d("MyTag", e.getMessage());
                    } catch (IOException e) {
                        Log.d("MyTag", e.getMessage());
                    }

                }

            }
        }).start();



    }
    private void readServerResponse(InputStream inputStream){

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String input = "";

        try {
            while((input = bufferedReader.readLine()) != null){
                builder.append(input);
            }

            jsonString = builder.toString();
            createArrayList(jsonString);

        } catch (IOException e) {
            Log.d("MyTag", e.getMessage());
        }


    }

    private void createArrayList(String jsonString) {
        Gson gson = new Gson();
        Equipment[] downloadedEquipments = gson.fromJson(jsonString, Equipment[].class);

        for (Equipment equipment: downloadedEquipments){
            equipmentList.add(equipment);
        }

    }

}
