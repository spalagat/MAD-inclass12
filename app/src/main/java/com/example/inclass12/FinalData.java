package com.example.inclass12;

import java.util.ArrayList;

public class FinalData {

    public ArrayList<ChoosePlaces> placelist;
    public String tripname;

    @Override
    public String toString() {
        return "FinalData{" +
                "placelist=" + placelist +
                ", tripname='" + tripname + '\'' +
                '}';
    }
    public FinalData(){
    }

    public FinalData(ArrayList<ChoosePlaces> placelist, String tripname) {
        this.placelist = placelist;
        this.tripname = tripname;
    }

    public ArrayList<ChoosePlaces> getPlacelist() {
        return placelist;
    }

    public void setPlacelist(ArrayList<ChoosePlaces> placelist) {
        this.placelist = placelist;
    }

    public String getTripname() {
        return tripname;
    }

    public void setTripname(String tripname) {
        this.tripname = tripname;
    }
}
