package com.app.denis.tutuapp.ui.main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.denis.tutuapp.BuildConfig;
import com.app.denis.tutuapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView tv = (TextView) view.findViewById(R.id.text_view_about);
        // Get version name from gradle build file
        tv.setText(getResources().getString(R.string.text_about, BuildConfig.VERSION_NAME));
        return view;
    }



}
