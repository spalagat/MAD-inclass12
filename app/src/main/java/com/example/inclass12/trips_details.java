package com.example.inclass12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class trips_details extends ArrayAdapter <FinalData>{


    public trips_details(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final FinalData finalData = (FinalData) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.showtrip, parent, false);
        }

        TextView tv1;
        tv1 = convertView.findViewById(R.id.tripname);
        tv1.setText(finalData.tripname);
        return convertView;

    }


}
