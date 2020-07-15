package com.example.inclass12;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class choose_places extends Fragment {

    OnFragmentInteractionListener mListener;
    Double lat,longitude;
    myadapter adapter;
    LatLng loctn;
    ListView listView;
    String tripname;
    ArrayList<ChoosePlaces> placesArrayList=null;
    ArrayList<ChoosePlaces> pArrayList=null;

    @SuppressLint("ValidFragment")
    public choose_places(LatLng loctn, String tripname) {
        this.loctn = loctn;
        this.tripname = tripname;

    }

    //OnFragmentInteractionListener mListener;
    @SuppressLint("ValidFragment")





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_select_places, container, false);
        Spinner spinner=view.findViewById(R.id.spinner);
        view.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinalData fd=new FinalData();
                fd.placelist=myadapter.arrayList;
                fd.tripname=tripname;
                mListener.sendtofirebase(fd);
                mListener.viewmaps(pArrayList,tripname);
            }
        });
         listView=view.findViewById(R.id.listview);
         listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        placesArrayList=new ArrayList<>();
        adapter= new myadapter(getContext(),R.layout.listview_layout,placesArrayList);
        listView.setAdapter(adapter);

      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String place=(String) parent.getItemAtPosition(position);
               Log.d("demo",place);
               Log.d("demo",loctn.toString());
               new places().execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+loctn.latitude+","+loctn.longitude+"&radius=1500&type="+place+"&key=AIzaSyDP4syC444UVhx6D5FWuXUBbhVmidh80sc");
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       mListener = (choose_places.OnFragmentInteractionListener) context;
    }

    public interface OnFragmentInteractionListener
    {
        public void viewmaps(ArrayList<ChoosePlaces> placesArrayList, String tripname);
        void sendtofirebase(FinalData fd);
    }

    public class places extends AsyncTask<String,Integer, ArrayList>
    {

        @Override
        protected ArrayList doInBackground(String... strings) {

            HttpURLConnection connection = null;
            LatLng loc=null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                placesArrayList.clear();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json= IOUtils.toString(connection.getInputStream(),"UTF-8");
                    JSONObject root=new JSONObject(json);
                    JSONArray jarray=root.getJSONArray("results");
                    for(int i=1;i<jarray.length();i++)
                    {
                        JSONObject root1=jarray.getJSONObject(i);
                        ChoosePlaces cp=new ChoosePlaces();
                        JSONObject root2=root1.getJSONObject("geometry");
                        JSONObject root3=root2.getJSONObject("location");
                        String lat=root3.getString("lat");
                        String longitude=root3.getString("lng");
                        String placename=root1.getString("name");
                        loc=new LatLng(Double.parseDouble(lat),Double.parseDouble(longitude));
                        cp.latitude=lat;
                        cp.longitude=longitude;
                        cp.placename=placename;
                        placesArrayList.add(cp);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return placesArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList placesArrayList) {
            super.onPostExecute(placesArrayList);
            Log.d("demo",placesArrayList.toString());

            // pArrayList=new ArrayList<>();
            pArrayList= myadapter.arrayList;
            adapter.notifyDataSetChanged();
           // mListener.viewmaps(pArrayList);
        }
    }

}
