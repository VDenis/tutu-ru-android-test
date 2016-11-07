package com.app.denis.tutuapp.ui.main;

import com.app.denis.tutuapp.model.Station;

interface UpdateFragmentFromActivity {
    void setStationFrom(Station station);
    void setStationTo(Station station);
    void setDate(int year, int monthOfYear, int dayOfMonth);
}
