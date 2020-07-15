package com.example.inclass12;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class initialfragment extends Fragment {

    OnFragmentInteractionListener mListener;
    ArrayList<FinalData> finaldatalist=new ArrayList<>();
trips_details adapter;
    ListView listView;
   public initialfragment(ArrayList<FinalData> finaldatalist) {
        this.finaldatalist = finaldatalist;
        Log.d("demo gives u data",finaldatalist.toString());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("called","first");
        View view=inflater.inflate(R.layout.fragment_firstfragment, container, false);
        listView=view.findViewById(R.id.tripsview);
        adapter= new trips_details(getContext(),R.layout.showtrip,finaldatalist);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.callTripTodisplayMap(finaldatalist.get(position).placelist,finaldatalist.get(position).tripname);
            }
        });

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myadapter.arrayList.clear();
                mListener.callSecondFragment();
            }
        });

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) context;
    }

    public interface OnFragmentInteractionListener
    {
        public void callSecondFragment();
        public void callTripTodisplayMap(ArrayList<ChoosePlaces> cp, String tripname);
    }


}
