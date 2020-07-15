package com.example.inclass12;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTrip extends Fragment {
    String tripname;
    OnFragmentInteractionListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (CreateTrip.OnFragmentInteractionListener) context;
    }

    public CreateTrip() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_create_trip, container, false);
        final TextView tv1=view.findViewById(R.id.tripname);
        final TextView tv2=view.findViewById(R.id.cityname);

        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tripname=tv1.getText().toString();
                 String city=tv2.getText().toString();

                if (city.length()==0 || tripname.length()==0)
                {
                    Toast.makeText(getContext(),"Please provide trip and city name",Toast.LENGTH_SHORT).show();
                }else {
                    new citycenter().execute("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="+city+"&fields=name,geometry&key=AIzaSyDP4syC444UVhx6D5FWuXUBbhVmidh80sc&type=city_hall&inputtype=textquery");

                }
            }
        });

        return view;

    }

    public interface OnFragmentInteractionListener
    {
//         void getCityCenter(String city);
         void selectplaces(LatLng loc, String name);
    }

    public class citycenter extends AsyncTask<String,Integer, LatLng>
    {

        @Override
        protected LatLng doInBackground(String... strings) {
            Log.d("demo","inside async");
            HttpURLConnection connection = null;
            LatLng loc=null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                Log.d("demo","try");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json= IOUtils.toString(connection.getInputStream(),"UTF-8");
                    JSONObject root=new JSONObject(json);
                    Log.d("demo",json);
                    JSONArray jarray=root.getJSONArray("candidates");
                    for(int i=0;i<jarray.length();i++)
                    {
                        JSONObject root1=jarray.getJSONObject(i);

                        JSONObject root2=root1.getJSONObject("geometry");
                        JSONObject root3=root2.getJSONObject("location");
                        String lat=root3.getString("lat");
                        String longitude=root3.getString("lng");
                        String cityname=root1.getString("name");
                        Log.d("demo",cityname);
                        loc=new LatLng(Double.parseDouble(lat),Double.parseDouble(longitude));
                        Log.d("demo",loc.toString());
                    }
                }
                else{
                    Log.d("demo","else part");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return loc;
        }

        @Override
        protected void onPostExecute(LatLng loc) {
            mListener.selectplaces(loc,tripname);
        }
    }
}
