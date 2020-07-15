package com.example.inclass12;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class myadapter extends ArrayAdapter<ChoosePlaces> {


    static ArrayList<ChoosePlaces> arrayList = new ArrayList<>();

    public myadapter(Context context, int resource, List<ChoosePlaces> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ChoosePlaces choosePlaces = (ChoosePlaces) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }

        TextView tv1;
        tv1 = convertView.findViewById(R.id.textView3);
        tv1.setText(choosePlaces.placename);

        final CheckBox cb = convertView.findViewById(R.id.checkBox);
        cb.setChecked(false);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  Log.d("layout","ahfjkahf");
                if (isChecked) {
                    cb.setChecked(true);

                    if(arrayList.size()<16)
                    {

                        arrayList.add(choosePlaces);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "only 15 places are allowed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "please check a box", Toast.LENGTH_SHORT).show();

                    arrayList.remove(choosePlaces);

                }
            }
        });





        return convertView;

    }

}
