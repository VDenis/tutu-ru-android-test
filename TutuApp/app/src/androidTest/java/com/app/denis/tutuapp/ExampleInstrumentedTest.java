package com.app.denis.tutuapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.app.denis.tutuapp.model.Journey;
import com.app.denis.tutuapp.model.StorageService;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.app.denis.tutuapp", appContext.getPackageName());
    }

    @Test
    public void readAssertFile() {
        Context appContext = InstrumentationRegistry.getContext();
        assertTrue(StorageService.loadJSONFromAsset(appContext) != null
                && StorageService.loadJSONFromAsset(appContext).length() > 2);
    }

    @Test
    public void convertJsonToObject() {
        Context appContext = InstrumentationRegistry.getContext();
        Journey journey = StorageService.getData(appContext);
        assertNotNull(journey);
        assertNotNull(journey.getCitiesFrom());
        assertNotNull(journey.getCitiesTo());

    }
}
