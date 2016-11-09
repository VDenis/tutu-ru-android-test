package com.app.denis.tutuapp.ui.selectStation.GroupStationAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.denis.tutuapp.R;
import com.app.denis.tutuapp.model.City;
import com.app.denis.tutuapp.model.Station;
import com.app.denis.tutuapp.ui.selectStation.StationAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.app.denis.tutuapp.utils.Search.filterByStationTitle;

/**
 * Created by Denis on 09.11.2016.
 */

public class GroupStationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Station> mFullDataset;
    // The items to display in your RecyclerView
    private List<Object> items;

    public static final int COUNTRY = 0, CITY = 1, STATION = 3;

    private StationAdapter.OnItemClickListener mListener;

    // Provide a suitable constructor (depends on the kind of dataset)
/*    public GroupStationAdapter(List<Object> items, StationAdapter.OnItemClickListener listener) {
        this.items = items;
        this.mListener = listener;
    }*/

    public GroupStationAdapter(List<City> myDataset, StationAdapter.OnItemClickListener listener) {
        List<Station> temp = new ArrayList<>();
        for (City city : myDataset) {
            for (Station station : city.getStations()) {
                temp.add(station);
            }
        }

        Collections.sort(temp, Station.COMPARE_BY_COUNTRY_CITY_STATION_TITLE);
        mFullDataset = temp;

        items = createGroupHeaders(temp);
        mListener = listener;
    }

    private List<Object> createGroupHeaders(List<Station> stations) {
        List<Object> commonList = new ArrayList<>();

        String currCountry = null;
        String currCity = null;

        for (Station station : stations) {
            if (currCountry == null || !currCountry.equals(station.getCountryTitle())) {
                currCountry = station.getCountryTitle();
                commonList.add(new CountryItem(currCountry));
            }
            if (currCity == null || !currCity.equals(station.getCityTitle())) {
                currCity = station.getCityTitle();
                commonList.add(new CityItem(currCity));
            }
            commonList.add(station);
        }

        return commonList;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof CountryItem) {
            return COUNTRY;
        } else if (items.get(position) instanceof CityItem) {
            return CITY;
        } else if (items.get(position) instanceof Station) {
            return STATION;
        }
        return -1;
    }

    /**
     * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
     *
     * @param parent parent container for the item
     * @param viewType type of view to be inflated
     * @return viewHolder to be inflated
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case COUNTRY:
                View v1 = inflater.inflate(R.layout.layout_viewholder1_country, parent, false);
                viewHolder = new ViewHolder1_Country(v1);
                break;
            case CITY:
                View v2 = inflater.inflate(R.layout.layout_viewholder2_city, parent, false);
                viewHolder = new ViewHolder2_City(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.layout_viewholder3_station, parent, false);
                viewHolder = new ViewHolder3_Station(v3).setListener(mListener);
                break;
        }
        return viewHolder;
    }

    public void performSearch(String performSearch) {
        items = createGroupHeaders(filterByStationTitle(mFullDataset, performSearch));
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case COUNTRY:
                ViewHolder1_Country vh1 = (ViewHolder1_Country) holder;
                configureViewHolder1_Country(vh1, position);
                break;
            case CITY:
                ViewHolder2_City vh2 = (ViewHolder2_City) holder;
                configureViewHolder2_City(vh2, position);
                break;
            default:
                ViewHolder3_Station vh3 = (ViewHolder3_Station) holder;
                configureViewHolder3_Station(vh3, position);
                break;
        }
    }

    private void configureViewHolder1_Country(ViewHolder1_Country vh1, int position) {
        CountryItem country = (CountryItem) items.get(position);
        if (country != null) {
            vh1.getLabel1().setText(country.getCountryTitle());
        }
    }

    private void configureViewHolder2_City(ViewHolder2_City vh2, int position) {
        CityItem city = (CityItem) items.get(position);
        if (city != null) {
            vh2.getLabel1().setText(city.getCityTitle());
        }
    }

    private void configureViewHolder3_Station(ViewHolder3_Station vh3, int position) {
        Station station = (Station) items.get(position);
        if (station != null) {
            vh3.bind(position, station);
            vh3.mTextView.setText(station.getStationTitle());
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
