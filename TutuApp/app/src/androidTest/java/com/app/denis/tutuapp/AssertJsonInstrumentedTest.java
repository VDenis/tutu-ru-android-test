package com.app.denis.tutuapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.app.denis.tutuapp.model.Journey;
import com.app.denis.tutuapp.model.StorageService;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Denis on 08.11.2016.
 */

@RunWith(AndroidJUnit4.class)
public class AssertJsonInstrumentedTest {
    @Test
    public void readAssertFile() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertTrue(StorageService.loadJSONFromAsset(appContext) != null
                && StorageService.loadJSONFromAsset(appContext).length() > 2);
    }

    @Test
    public void convertJsonToObject() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Journey journey = StorageService.getData(appContext);
        assertNotNull(journey);
        assertNotNull(journey.getCitiesFrom());
        assertNotNull(journey.getCitiesTo());
    }
}
