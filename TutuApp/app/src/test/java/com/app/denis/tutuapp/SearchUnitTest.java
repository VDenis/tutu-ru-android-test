package com.app.denis.tutuapp;

import org.junit.Test;

import static org.junit.Assert.*;

import static com.app.denis.tutuapp.utils.Search.containsIgnoreCase;

/**
 * Created by Denis on 08.11.2016.
 */

public class SearchUnitTest {
    @Test
    public void search_simple_text_isContain() {
        assertTrue(containsIgnoreCase("The Espresso Test Recorder tool lets you " +
                "create UI tests for your app without writing any test code.", "app"));
    }

    @Test
    public void search_different_case_isContain() {
        assertTrue(containsIgnoreCase("The Espresso Test Recorder tool lets you " +
                "create UI tests for your app without writing any test code.", "FoR YouR"));
    }

    @Test
    public void search_wrong_text_isNoContain() {
        assertFalse(containsIgnoreCase("The Espresso Test Recorder tool lets you " +
                "create UI tests for your app without writing any test code.", "coffee"));
    }
}
