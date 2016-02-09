package com.example.jim.myapplication;

import android.app.Activity;
import android.app.ListFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jim on 04.02.16.
 */
public  class EquipmentListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    OnArticleSelectedListener mCallback;

    ArrayList<Equipment> equipmentList = new ArrayList<>();
    String sort = "?sort_by=it_no";
    String whichEquipment = "&which_equipment=ALL";
    EquipmentAdapter myAdapterInstance;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("MyTag", "Item " + position);
        mCallback.onArticleSelected(position, equipmentList);
    }


    public interface OnArticleSelectedListener {
        void onArticleSelected(int position, ArrayList<Equipment> equipments);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            sort = bundle.getString("sort");
            whichEquipment = bundle.getString("filter");
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
        getEquipmentFromJson();
        myAdapterInstance = new EquipmentAdapter(getActivity(), R.layout.list_item, equipmentList);

        setListAdapter(myAdapterInstance);
        getListView().setOnItemClickListener(this);
    }



    private void getEquipmentFromJson() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    String jsonURL =  "http://kark.hin.no:8088/d3330log_backend/getTestEquipment" + sort + whichEquipment;
                    HttpURLConnection connection;
                    URL url;
                    try {
                        url = new URL(jsonURL);

                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestProperty("Content-Type" , "text/plain; charset-utf-8");
                        int responseCode = connection.getResponseCode();

                        if(responseCode == HttpURLConnection.HTTP_OK) {
                            Gson gson = new Gson();
                            equipmentList = gson.fromJson(new InputStreamReader(connection.getInputStream()), new TypeToken<ArrayList<Equipment>>() {}.getType());
                        }else {
                            Log.d("MyTag", "HTTP error code: " + responseCode);
                        }
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


}
