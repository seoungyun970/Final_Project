package com.example.myapplication.Child;

public class MapPoint {
    private String Mapname;
    private double latitude;
    private double longitude;

    public MapPoint(){
        super();
    }

    public MapPoint(String mapname, double latitude, double longitude) {
        Mapname = mapname;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getMapname() {
        return Mapname;
    }

    public void setMapname(String mapname) {
        Mapname = mapname;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
