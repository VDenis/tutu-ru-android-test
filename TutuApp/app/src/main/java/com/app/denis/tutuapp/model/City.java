package com.app.denis.tutuapp.model;

import java.util.List;

/**
 * Created by Denis on 05.11.2016.
 */

public class City {
    /*
    {
    "countryTitle" : "Россия", //название страны
    "point" : { //координаты города
        "longitude" : 50.64357376098633,
        "latitude" : 55.37233352661133
    },
    "districtTitle" : "Чистопольский район", //название района
    "cityId" : 4454, //идентификатор города
    "cityTitle" : "Чистополь", //название города
    "regionTitle" : "Республика Татарстан", //название региона
    "stations" : [...] //массив станций
    }
    */
    private String countryTitle;
    private Point point;
    private String districtTitle;
    private int cityId;
    private String cityTitle;
    private String regionTitle;
    private List<Station> stations;
    //private Station[] stations;


    public String getCountryTitle() {
        return countryTitle;
    }

    public void setCountryTitle(String countryTitle) {
        this.countryTitle = countryTitle;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public void setDistrictTitle(String districtTitle) {
        this.districtTitle = districtTitle;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public String getRegionTitle() {
        return regionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        this.regionTitle = regionTitle;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
