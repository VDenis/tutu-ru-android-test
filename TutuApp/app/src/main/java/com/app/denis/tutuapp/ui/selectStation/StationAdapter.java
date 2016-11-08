package com.app.denis.tutuapp.ui.selectStation;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.denis.tutuapp.R;
import com.app.denis.tutuapp.model.City;
import com.app.denis.tutuapp.model.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.app.denis.tutuapp.utils.Search.filter;

/**
 * Created by Denis on 06.11.2016.
 */

class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private static final String LOG_TAG = StationAdapter.class.getSimpleName();
    private Station[] mDataset = new Station[]{};
    List<Station> mFullDataset;
    private OnItemClickListener mListener;

    StationAdapter(List<City> myDataset, OnItemClickListener listener) {
        List<Station> temp = new ArrayList<>();
        for (City city : myDataset) {
            for (Station station : city.getStations()) {
                temp.add(station);
            }
        }

        Collections.sort(temp, Station.COMPARE_BY_COUNTRY_CITY_STATION_TITLE);
        mFullDataset = temp;

        mDataset = temp.toArray(mDataset);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(parent.getContext());
        View v = vi.inflate(R.layout.station_list_item, parent, false);
        TextView tv = (TextView) v.findViewById(android.R.id.text1);
        ImageButton ib = (ImageButton) v.findViewById(android.R.id.button1);
        ViewHolder vh = new ViewHolder(v, tv, ib);
        vh.setListener(mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String country = "", city = "", station = "";
        if (mDataset != null && mDataset[position] != null) {
            if (mDataset[position].getCountryTitle() != null) {
                country = mDataset[position].getCountryTitle();
            }
            if (mDataset[position].getCityTitle() != null) {
                city = mDataset[position].getCityTitle();
            }
            if (mDataset[position].getStationTitle() != null) {
                station = mDataset[position].getStationTitle();
            }

            holder.bind(position, mDataset[position]);
        }

        Log.d(LOG_TAG, "onBindViewHolder: element=" + position + ", " +
                country + ", " +
                city + ", " +
                station
        );
        holder.mTextView.setText(station);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void performSearch(String performSearch) {
        mDataset = filter(mFullDataset, performSearch).toArray(new Station[]{});
        notifyDataSetChanged();
    }

    /**
     * Interface for receiving click events from cells.
     */
    interface OnItemClickListener {
        public void onClickShowDetail(Station station);
        public void onClickSelect(Station station);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mTextView;
        final View mView;
        final ImageButton mImageButton;

        Station station;

        ViewHolder(View v, TextView tv, ImageButton ib) {
            super(v);
            mView = v;
            mTextView = tv;
            mImageButton = ib;
        }

        void bind(int position, Station station) {
            this.station = station;
        }

        void setListener(final OnItemClickListener listener) {
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
        }
    }
}
