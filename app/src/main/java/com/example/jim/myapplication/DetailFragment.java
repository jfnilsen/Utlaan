package com.example.jim.myapplication;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Jim on 04/02/2016.
 */
public class DetailFragment extends Fragment {

    Bitmap bitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void showDetails(Equipment equipment) {



        View detailView = getActivity().findViewById(R.id.detail_frame);

        ((TextView)detailView.findViewById(R.id.e_id)).setText(String.valueOf(equipment.getE_id()));
        ((TextView)detailView.findViewById(R.id.type)).setText(equipment.getType());
        ((TextView)detailView.findViewById(R.id.brand)).setText(equipment.getBrand());
        ((TextView)detailView.findViewById(R.id.model)).setText(equipment.getModel());
        ((TextView)detailView.findViewById(R.id.description)).setText(equipment.getDescription());
        ((TextView)detailView.findViewById(R.id.it_no)).setText(equipment.getIt_no());
        ((TextView)detailView.findViewById(R.id.aquired)).setText(equipment.getAquired());

        ImageView image = (ImageView)detailView.findViewById(R.id.image);
        runImageThread(image, equipment.getImage_url());
        if(bitmap != null){
            image.setImageBitmap(bitmap);
        }


    }

    private void runImageThread(final ImageView viewById, final String image_url) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = getBitmap(image_url);

            }
        });

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmap(String url) {
        Bitmap bitmap = null;

        try {
            URL img_Url = new URL(url);
            bitmap = BitmapFactory.decodeStream(img_Url.openConnection().getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
