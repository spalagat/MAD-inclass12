package com.example.inclass12;

public class ChoosePlaces {
    public String latitude;
    public String longitude;
    public String placename;

    @Override
    public String toString() {
        return "ChoosePlaces{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", placename='" + placename + '\'' +
                '}';
    }

    public ChoosePlaces() {
    }

    public ChoosePlaces(String latitude, String longitude, String placename) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.placename = placename;
    }
}
