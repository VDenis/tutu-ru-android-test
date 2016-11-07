package com.app.denis.tutuapp.ui.selectStation;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.app.denis.tutuapp.R;
import com.app.denis.tutuapp.model.Journey;
import com.app.denis.tutuapp.model.Station;
import com.app.denis.tutuapp.model.StorageService;
import com.app.denis.tutuapp.ui.detailStation.DetailStationFragment;

public class SelectStationActivity extends AppCompatActivity implements StationAdapter.OnItemClickListener {

    // input parameters
    public static final String SELECT_STATION_DIRECTION = "SELECT_STATION_DIRECTION";
    public static final String SELECT_STATION_FROM = "SELECT_STATION_FROM";
    public static final String SELECT_STATION_TO = "SELECT_STATION_TO";

    // output parameters
    public static final String SELECT_STATION_RESULT = "SELECT_STATION_RESULT";

    // dialog tag
    private static final String DIALOG_TAG = "DIALOG_TAG_1";

    // Views
    private RecyclerView mStationsRecyclerView;
    private EditText mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);

        // Get data from storage service
        Journey journey = StorageService.getData(this);
        // Setup recycler mView
        mStationsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_stations);
        mStationsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplication()));

        mSearchText = (EditText) findViewById(R.id.edit_text_search);
        /*mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((StationAdapter)mStationsRecyclerView.getAdapter()).performSearch(v.getText().toString());
                    return true;
                }
                return false;
            }
        });*/

        mSearchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((StationAdapter)mStationsRecyclerView.getAdapter()).performSearch(s.toString());
            }
        });

        // Set title to appbar and setup recycler mView adapter
        Intent intent = getIntent();
        String direction = intent.getStringExtra(SELECT_STATION_DIRECTION);
        if (direction != null && direction.equals(SELECT_STATION_FROM)) {
            setTitle(R.string.station_from);
            mStationsRecyclerView.setAdapter(new StationAdapter(journey.getCitiesFrom(), this));
        } else if (direction != null && direction.equals(SELECT_STATION_TO)) {
            setTitle(R.string.station_to);
            mStationsRecyclerView.setAdapter(new StationAdapter(journey.getCitiesTo(), this));
        }
    }

    // when user picked a data return to the Activity has made a request
    private void returnSelectedStation(Station station) {
        Intent intent = new Intent();
        intent.putExtra(SELECT_STATION_RESULT, station);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClickShowDetail(Station station) {
        DialogFragment fragment = DetailStationFragment.newInstance(station);
        fragment.show(getFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void onClickSelect(Station station) {
        returnSelectedStation(station);
    }
}
