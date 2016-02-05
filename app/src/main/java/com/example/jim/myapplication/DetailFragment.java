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

        Equipment equipment = objects.get(position);
        View detailView = getActivity().findViewById(R.id.detail_fragment);

        ((TextView)detailView.findViewById(R.id.e_id)).setText(String.valueOf(equipment.getE_id()));
        ((TextView)detailView.findViewById(R.id.type)).setText(equipment.getType());
        ((TextView)detailView.findViewById(R.id.brand)).setText(equipment.getBrand());
        ((TextView)detailView.findViewById(R.id.model)).setText(equipment.getModel());
        ((TextView)detailView.findViewById(R.id.description)).setText(equipment.getDescription());
        ((TextView)detailView.findViewById(R.id.it_no)).setText(equipment.getIt_no());
        ((TextView)detailView.findViewById(R.id.aquired)).setText(equipment.getAquired());
        ((TextView)detailView.findViewById(R.id.imageurl)).setText(equipment.getImage_url());

        //TODO: Display the image in the fragment

    }
}
