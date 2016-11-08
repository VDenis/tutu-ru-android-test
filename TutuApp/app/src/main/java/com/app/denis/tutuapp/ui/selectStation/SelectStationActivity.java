package com.app.denis.tutuapp.ui.selectStation;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.app.denis.tutuapp.R;
import com.app.denis.tutuapp.model.Journey;
import com.app.denis.tutuapp.model.Station;
import com.app.denis.tutuapp.model.StorageService;
import com.app.denis.tutuapp.ui.detailStation.DetailStationFragment;
import com.app.denis.tutuapp.utils.SimpleDividerItemDecoration;

import java.lang.ref.WeakReference;

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

    // Handler
    Handler mHandler;

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
                // use handler to post delayed messages
                //((StationAdapter)mStationsRecyclerView.getAdapter()).performSearch(s.toString());
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString(MyHandler.HANDLER_SEARCH_STRING, s.toString());
                msg.setData(bundle);
                mHandler.sendMessageDelayed(msg, 500);
            }
        });

        mSearchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    EditText et = (EditText)v;
                    if(event.getRawX() >= (et.getRight() - et.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        et.setText("");
                        return true;
                    }
                }
                return false;
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

        // Init handler
        mHandler = new MyHandler(this);
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
    protected void onDestroy() {
        // clean handler
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    @Override
    public void onClickSelect(Station station) {
        returnSelectedStation(station);
    }

    static class MyHandler extends Handler {
        static final String HANDLER_SEARCH_STRING = "HANDLER_SEARCH_STRING";
        WeakReference<SelectStationActivity> wrActivity;

        public MyHandler(SelectStationActivity activity) {
            wrActivity = new WeakReference<SelectStationActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SelectStationActivity activity = wrActivity.get();
            if (activity != null) {
                String text = msg.getData().getString(HANDLER_SEARCH_STRING, "");
                ((StationAdapter)activity.mStationsRecyclerView.getAdapter()).performSearch(text);
            }
        }
    }
}
