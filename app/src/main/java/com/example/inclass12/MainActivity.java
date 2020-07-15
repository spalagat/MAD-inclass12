package com.example.inclass12;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements initialfragment.OnFragmentInteractionListener, CreateTrip.OnFragmentInteractionListener, choose_places.OnFragmentInteractionListener, Maps_view.OnFragmentInteractionListener
        {

    LatLng location = null;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbref = database.getReference("Trips");

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbref = database.getReference("Trips");
        setContentView(R.layout.activity_main);
        Log.d("status","oncreate");

        ArrayList<FinalData> f=this.call_intital();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.Container, new initialfragment(f), "first").addToBackStack("first")
                .commit();
        setTitle("HOME");

    }

    @Override
    public void callSecondFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Container, new CreateTrip(), "second").addToBackStack("second")
                .commit();
        setTitle("Details");
    }

    @Override
    public void callTripTodisplayMap(ArrayList<ChoosePlaces> cp, String tripname) {
        String flag="FromFragOne";
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Container, new Maps_view(cp, tripname,flag), "fourth").addToBackStack("fourth")
                .commit();
        setTitle(tripname);
    }


    @Override
    public void selectplaces(LatLng loc, String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Container, new choose_places(loc, name), "third").addToBackStack("third")
                .commit();
        setTitle("Choose Places");
    }

    @Override
    public void viewmaps(ArrayList<ChoosePlaces> placesArrayList, String tripname) {
        String flag="FromFragDisplayPlace";
        ArrayList<FinalData> f=this.call_intital();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Container, new initialfragment(f), "first").addToBackStack("first")
                .commit();
        setTitle("HOME");

        setTitle("Trip");
    }

    @Override
    public void sendtofirebase(FinalData fd) {
        Log.d("demo", fd.toString());
        dbref = FirebaseDatabase.getInstance().getReference("Trips");
        dbref.push().setValue(fd);
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<FinalData> finalDataArrayList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    FinalData fd = ds.getValue(FinalData.class);
                    finalDataArrayList.add(fd);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Container, new initialfragment(finalDataArrayList), "first").addToBackStack("first")
                        .commit();
                setTitle("HOME");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onCancelFirstFragment() {
        dbref = FirebaseDatabase.getInstance().getReference("Trips");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<FinalData> finalDataArrayList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    FinalData fd = ds.getValue(FinalData.class);
                    finalDataArrayList.add(fd);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Container, new initialfragment(finalDataArrayList), "first").addToBackStack("first")
                        .commit();
                setTitle("HOME");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public ArrayList<FinalData> call_intital(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbref = database.getReference("Trips");
        setContentView(R.layout.activity_main);
        Log.d("status","oncreate");
        final ArrayList<FinalData> finalDataArrayList=new ArrayList<>();
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("status","onaddvalue");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    FinalData fd = ds.getValue(FinalData.class);
                    finalDataArrayList.add(fd);
                }
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.Container, new initialfragment(finalDataArrayList), "first").addToBackStack("first")
                        .commit();
                setTitle("HOME");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return finalDataArrayList;
    }


}