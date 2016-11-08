package com.app.denis.tutuapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Denis on 06.11.2016.
 */

public class Point implements Parcelable {
    /*
    "point" : { //координаты станции (в общем случае отличаются от координат города)
    "longitude" : 50.64357376098633,
    "latitude" : 55.37233352661133
    },
    */
    private double longitude;
    private double latitude;

    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private Point(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };
}
