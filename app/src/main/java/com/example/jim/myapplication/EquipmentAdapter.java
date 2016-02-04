package com.example.jim.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jim on 03/02/2016.
 */
public class EquipmentAdapter extends ArrayAdapter<Equipment> {
    List<Equipment> objects;
    Context context;

    public EquipmentAdapter(Context context, int resource, List<Equipment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, parent, false);

        TextView typeText = (TextView) view.findViewById(R.id.itemType);
        typeText.setText(objects.get(position).type);

        TextView modelText = (TextView) view.findViewById(R.id.itemModel);
        modelText.setText(objects.get(position).model);

        TextView onLoan = (TextView) view.findViewById(R.id.itemOnLoan);
        onLoan.setText(objects.get(position).onLoan + "");
        return view;

    }
}
