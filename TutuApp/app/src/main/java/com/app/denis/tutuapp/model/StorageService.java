package com.app.denis.tutuapp.model;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Denis on 05.11.2016.
 */

public class StorageService {

    private static volatile Journey journey;

    public synchronized static Journey getData(Context context) {
        if (journey == null) {
            String jsonString = loadJSONFromAsset(context);
            Gson gson = new Gson();
            journey = gson.fromJson(jsonString, Journey.class);
        }
        return journey;
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("allStations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
