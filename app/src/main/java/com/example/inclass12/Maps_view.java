package com.example.inclass12;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Maps_view extends Fragment {
    ArrayList <ChoosePlaces> arrlist=new ArrayList<>();
    MapView mapView;
    private GoogleMap googleMap;
    String tripname;
    OnFragmentInteractionListener mListener;
String flag;
    @SuppressLint("ValidFragment")
    public Maps_view(ArrayList<ChoosePlaces> arrlist, String tripname,String flag) {
        this.arrlist = arrlist;
        this.tripname=tripname;
        this.flag=flag;
    }

    public interface OnFragmentInteractionListener
    {
        //         void getCityCenter(String city);
        void sendtofirebase(FinalData fd);
        void onCancelFirstFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Maps_view.OnFragmentInteractionListener) context;
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_maps_view, container, false);
        Button addtripButton=view.findViewById(R.id.addtrips);
        Button canceButton=view.findViewById(R.id.Cancel);
        if(flag=="FromFragOne")
        {
            addtripButton.setVisibility(View.INVISIBLE);
        }
        else if(flag=="FromFragDisplayPlace")
        {
            addtripButton.setVisibility(View.VISIBLE);
        }
        addtripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinalData fd=new FinalData();
                fd.placelist=arrlist;
                fd.tripname=tripname;
                mListener.sendtofirebase(fd);
            }
        });

        canceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinalData fd=new FinalData();
                if(flag=="FromFragOne")
                {
                    mListener.onCancelFirstFragment();
                }
                else if(flag=="FromFragDisplayPlace")
                {

                }

            }
        });
        mapView= view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                LatLngBounds.Builder builder=new LatLngBounds.Builder();
                PolylineOptions polylineOptions=new PolylineOptions();
                polylineOptions.width(10).color(Color.RED);

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
                // For dropping a marker at a point on the Map
                for(int i=0;i<arrlist.size();i++){
                    LatLng location=new LatLng(Double.parseDouble(arrlist.get(i).latitude),Double.parseDouble(arrlist.get(i).longitude));
                    builder.include(location);
                    googleMap.addMarker(new MarkerOptions().position(location).title(arrlist.get(i).placename));
                    polylineOptions.add(location);

                }
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                LatLngBounds bounds=builder.build();
                CameraUpdate cu= CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                googleMap.animateCamera(cu);

            }
        });
        return view;
    }


}
