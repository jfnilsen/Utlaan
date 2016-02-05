package com.example.jim.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jim on 04/02/2016.
 */
public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void showDetails(int position, ArrayList<Equipment> objects) {

        //TODO: Fill out the rest of the detail objects
        Equipment equipment = objects.get(position);
        View detailView = getActivity().findViewById(R.id.detail_fragment);
        TextView e_idText = (TextView)detailView.findViewById(R.id.brand);
        e_idText.setText(equipment.getBrand());
    }
}
