package com.app.denis.tutuapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;

import java.util.Comparator;

/**
 * Created by Denis on 05.11.2016.
 */

public class Station implements Parcelable {
    /*
    {
    "countryTitle" : "Россия", //название страны (денормализация данных, дубль из города)
    "point" : { //координаты станции (в общем случае отличаются от координат города)
        "longitude" : 50.64357376098633,
        "latitude" : 55.37233352661133
    },
    "districtTitle" : "Чистопольский район", //название района
    "cityId" : 4454, //идентификатор города
    "cityTitle" : "город Чистополь", //название города
    "regionTitle" : "Республика Татарстан", //название региона
    "stationId" : 9362, //идентификатор станции
    "stationTitle" : "Чистополь" //полное название станции
    }
    */
    private String countryTitle;
    private Point point;
    private String districtTitle;
    private int cityId;
    private String cityTitle;
    private String regionTitle;
    private int stationId;
    private String stationTitle;

    // Constructor for parcel
    private Station(Parcel in) {
        countryTitle = in.readString();
        point = in.<Point>readTypedObject(Point.CREATOR);
        districtTitle = in.readString();
        cityId = in.readInt();
        cityTitle = in.readString();
        regionTitle = in.readString();
        stationId = in.readInt();
        stationTitle = in.readString();
    }

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

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationTitle() {
        return stationTitle;
    }

    public void setStationTitle(String stationTitle) {
        this.stationTitle = stationTitle;
    }

    // Using for sorting
    public static Comparator<Station> COMPARE_BY_COUNTRY_CITY_STATION_TITLE = new Comparator<Station>() {
        @Override
        public int compare(Station one, Station other) {
            int result = one.getCountryTitle().compareToIgnoreCase(other.getCountryTitle());
            if (result == 0) {
                result = one.getCityTitle().compareToIgnoreCase(other.getCityTitle());
                if (result == 0) {
                    return one.getStationTitle().compareToIgnoreCase(other.getStationTitle());
                }
            }
            return result;
        }
    };

    // Parcelable interface
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryTitle);
        dest.<Point>writeTypedObject(point, flags);
        dest.writeString(districtTitle);
        dest.writeInt(cityId);
        dest.writeString(cityTitle);
        dest.writeString(regionTitle);
        dest.writeInt(stationId);
        dest.writeString(stationTitle);
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };
}
