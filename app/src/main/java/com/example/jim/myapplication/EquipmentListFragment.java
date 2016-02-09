package com.example.jim.myapplication;

import android.app.Activity;
import android.app.ListFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public  class EquipmentListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    OnArticleSelectedListener mCallback;

    ArrayList<Equipment> equipmentList = new ArrayList<>();
    String params = "?sort_by=it_no";
    String jsonString;
    EquipmentAdapter myAdapterInstance;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("MyTag", "Item " + position);
        mCallback.onArticleSelected(position, equipmentList);
    }


    public interface OnArticleSelectedListener {
        public void onArticleSelected(int position, ArrayList<Equipment> equipments);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            params = bundle.getString("params");
        }
        return inflater.inflate(R.layout.list_fragment, container, false);

    }


    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        try{
            mCallback = (OnArticleSelectedListener) context;
            Log.d("MyTag", mCallback.toString());
        }catch (ClassCastException e){
            Log.d("MyTag", e.getMessage());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        connectToJSON();
        int layoutID = R.layout.list_item;
        myAdapterInstance = new EquipmentAdapter(getActivity(), layoutID, equipmentList);

        setListAdapter(myAdapterInstance);
        getListView().setOnItemClickListener(this);
    }



    private void connectToJSON() {
        Thread thread = new Thread(new Runnable() {
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
        });

        try {
            thread.start();
            thread.join();

        } catch (InterruptedException e) {
            Log.d("MyTag", e.getMessage());
        }

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
        equipmentList = new ArrayList<>();
        Equipment[] downloadedEquipments = gson.fromJson(jsonString, Equipment[].class);
        for (Equipment equipment: downloadedEquipments){
            equipmentList.add(equipment);
        }

    }


}
