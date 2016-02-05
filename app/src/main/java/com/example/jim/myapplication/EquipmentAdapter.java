package com.example.jim.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.util.List;

/**
 * Created by Jim on 03/02/2016.
 */
public class EquipmentAdapter extends ArrayAdapter<Equipment>{
    List<Equipment> objects;
    Context context;

    public EquipmentAdapter(Context context, int resource, List<Equipment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }


        setDataFields(position, convertView);
        View.OnClickListener clickListener = new View.OnClickListener() {

            public void onClick(View view){
                Toast.makeText(context,"Row " + position, Toast.LENGTH_SHORT).show();
            }
        };
        convertView.setOnClickListener(clickListener);
        return convertView;


    }

    private void setDataFields(int position, View view) {
        TextView itnoText = (TextView) view.findViewById(R.id.itno);
        itnoText.setText(objects.get(position).getIt_no());

        TextView typeText = (TextView) view.findViewById(R.id.itemType);
        typeText.setText(objects.get(position).getType());

        TextView modelText = (TextView) view.findViewById(R.id.itemModel);
        modelText.setText(objects.get(position).getModel());

        TextView onLoan = (TextView) view.findViewById(R.id.brand);
        onLoan.setText(objects.get(position).getBrand());
    }



}
