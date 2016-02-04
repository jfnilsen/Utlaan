package com.example.jim.myapplication;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jim on 04.02.16.
 */
public class EquipmentListFragment extends Fragment {

    ArrayList<Equipment> equipmentList = new ArrayList<>();
    String jsonString;
    EquipmentAdapter myAdapterInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.equipments_fragment, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView myListView = (ListView)getActivity().findViewById(R.id.equipmentList);
        //equipmentList = new ArrayList<>();
        int layoutID = R.layout.list_item;
        connectToJSON("?sort_by=it_no");
        myAdapterInstance = new EquipmentAdapter(getActivity(), layoutID, equipmentList);

        myListView.setAdapter(myAdapterInstance);


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
