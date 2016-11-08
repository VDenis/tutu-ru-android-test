package com.app.denis.tutuapp;

import com.app.denis.tutuapp.model.Point;
import com.app.denis.tutuapp.model.Station;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.app.denis.tutuapp.utils.Search.filterByStationTitle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Denis on 08.11.2016.
 */

public class FilterUnitTest {
    @Test
    public void filter_collection_simple_pattern_isContainOnlyStationWithPatternMatching() {
        Station st = new Station(
                "Russia",
                new Point(100.0, 200.0),
                "Khamovniki District",
                1,
                "Moscow",
                "Moscow Oblast",
                2,
                "Ostankino");
        List<Station> stList = new ArrayList<>();
        stList.add(st);
        List<Station> resultList = filterByStationTitle(stList, "Ostankino");
        assertFalse(resultList.isEmpty());
        assertEquals("Ostankino", resultList.get(0).getStationTitle());
    }

    @Test
    public void filter_collection_pattern_not_found_isDontMatchPattern() {
        Station st = new Station(
                "Russia",
                new Point(100.0, 200.0),
                "Khamovniki District",
                1,
                "Moscow",
                "Moscow Oblast",
                2,
                "Ostankino");
        List<Station> stList = new ArrayList<>();
        stList.add(st);
        List<Station> resultList = filterByStationTitle(stList, "Technopark");
        assertTrue(resultList.isEmpty());
    }
}
