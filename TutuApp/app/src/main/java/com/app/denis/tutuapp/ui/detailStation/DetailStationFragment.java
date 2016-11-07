package com.app.denis.tutuapp.ui.detailStation;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.denis.tutuapp.R;
import com.app.denis.tutuapp.model.Station;

/**
 * Created by Denis on 07.11.2016.
 */

public class DetailStationFragment extends DialogFragment {
    private static final String ARGUMENT_STATION = "ARGUMENT_STATION";

    private Station mStation;

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static DetailStationFragment newInstance(Station station) {
        DetailStationFragment f = new DetailStationFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_STATION, station);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStation = getArguments().getParcelable(ARGUMENT_STATION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_station, container, false);
        TextView tv1 = (TextView) view.findViewById(R.id.textView1);
        TextView tv2 = (TextView) view.findViewById(R.id.textView2);
        TextView tv3 = (TextView) view.findViewById(R.id.textView3);
        TextView tv4 = (TextView) view.findViewById(R.id.textView4);
        TextView tv5 = (TextView) view.findViewById(R.id.textView5);
        TextView tv6 = (TextView)view.findViewById(R.id.textView6);

        // Check if data is empty
        if (mStation.getCountryTitle().isEmpty()) {
            tv1.setVisibility(View.GONE);
        } else {
            tv1.setText(getString(R.string.dlg_country_title, mStation.getCountryTitle()));
        }

        if (mStation.getDistrictTitle().isEmpty()) {
            tv2.setVisibility(View.GONE);
        } else {
            tv2.setText(getString(R.string.dlg_district_title, mStation.getDistrictTitle()));
        }

        if (mStation.getCityTitle().isEmpty()) {
            tv3.setVisibility(View.GONE);
        } else {
            tv3.setText(getString(R.string.dlg_city_title, mStation.getCityTitle()));
        }

        if (mStation.getRegionTitle().isEmpty()) {
            tv4.setVisibility(View.GONE);
        } else {
            tv4.setText(getString(R.string.dlg_region_title, mStation.getRegionTitle()));
        }

        if (mStation.getStationTitle().isEmpty()) {
            tv5.setVisibility(View.GONE);
        } else {
            tv5.setText(getString(R.string.dlg_station_title, mStation.getStationTitle()));
        }

        return view;
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }
}
