package com.app.denis.tutuapp.ui.selectStation.GroupStationAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.denis.tutuapp.R;
import com.app.denis.tutuapp.model.Station;
import com.app.denis.tutuapp.ui.selectStation.StationAdapter;

/**
 * Created by Denis on 09.11.2016.
 */

public class ViewHolder3_Station extends RecyclerView.ViewHolder {
    private static final String LOG_TAG = ViewHolder3_Station.class.getSimpleName();

    final TextView mTextView;
    final View mView;
    final ImageButton mImageButton;

    Station station;

    public ViewHolder3_Station(View v) {
        super(v);
        mView = v;
        mTextView = (TextView) v.findViewById(android.R.id.text1);
        mImageButton = (ImageButton) v.findViewById(android.R.id.button1);
    }

    void bind(int position, Station station) {
        this.station = station;
    }

    ViewHolder3_Station setListener(final StationAdapter.OnItemClickListener listener) {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Log.d(LOG_TAG, "onClick Select: position=" + position + ", st title=" + station.getStationTitle());
                listener.onClickSelect(station);
            }
        });
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Log.d(LOG_TAG, "onClick Detail: position=" + position + ", st title=" + station.getStationTitle());
                listener.onClickShowDetail(station);
            }
        });
        return this;
    }
}
