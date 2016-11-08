package com.app.denis.tutuapp.ui.main;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.denis.tutuapp.R;
import com.app.denis.tutuapp.model.Station;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.app.denis.tutuapp.ui.selectStation.SelectStationActivity.SELECT_STATION_DIRECTION;
import static com.app.denis.tutuapp.ui.selectStation.SelectStationActivity.SELECT_STATION_FROM;
import static com.app.denis.tutuapp.ui.selectStation.SelectStationActivity.SELECT_STATION_TO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener, UpdateFragmentFromActivity {

    private OnFragmentInteractionListener mListener;

    private static final String SAVE_INSTANCE_STATE_STATION_FROM = "SAVE_INSTANCE_STATE_STATION_FROM";
    private static final String SAVE_INSTANCE_STATE_STATION_TO = "SAVE_INSTANCE_STATE_STATION_TO";
    private static final String SAVE_INSTANCE_STATE_DATE_YEAR = "SAVE_INSTANCE_STATE_DATE_YEAR";
    private static final String SAVE_INSTANCE_STATE_DATE_MONTH = "SAVE_INSTANCE_STATE_DATE_MONTH";
    private static final String SAVE_INSTANCE_STATE_DATE_DAY = "SAVE_INSTANCE_STATE_DATE_DAY";

    private Station mStationFrom;
    private Station mStationTo;

    // date
    int myYear = -1;
    int myMonth = -1;
    int myDay = -1;

    // btn
    private Button btnSelectStationFrom;
    private Button btnSelectStationTo;
    private Button btnSelectDate;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     *
     * @return A new instance of fragment ScheduleFragment.
     */
    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // restore stations after rotation
        if (savedInstanceState != null) {
            mStationFrom = savedInstanceState.getParcelable(SAVE_INSTANCE_STATE_STATION_FROM);
            mStationTo = savedInstanceState.getParcelable(SAVE_INSTANCE_STATE_STATION_TO);
            myYear = savedInstanceState.getInt(SAVE_INSTANCE_STATE_DATE_YEAR);
            myMonth = savedInstanceState.getInt(SAVE_INSTANCE_STATE_DATE_MONTH);
            myDay = savedInstanceState.getInt(SAVE_INSTANCE_STATE_DATE_DAY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        btnSelectStationFrom = (Button) view.findViewById(R.id.btnSelectStationFrom);
        btnSelectStationTo = (Button) view.findViewById(R.id.btnSelectStationTo);
        btnSelectDate = (Button) view.findViewById(R.id.btnSelectDate);

        // set text to button btnSelectStationFrom after rotation
        if (mStationFrom != null) {
            btnSelectStationFrom.setText(mStationFrom.getStationTitle());
        }

        // set text to button btnSelectStationTo after rotation
        if (mStationTo != null) {
            btnSelectStationTo.setText(mStationTo.getStationTitle());
        }

        // date
        if (myDay == -1 || myMonth == -1 || myYear == -1) {
            Calendar c = Calendar.getInstance();
            myYear = c.get(Calendar.YEAR);
            myMonth = c.get(Calendar.MONTH);
            myDay = c.get(Calendar.DAY_OF_MONTH);
        }
        setDateToBtnSelectDate();

        btnSelectStationFrom.setOnClickListener(this);
        btnSelectStationTo.setOnClickListener(this);
        btnSelectDate.setOnClickListener(this);
        return view;
    }

    void setDateToBtnSelectDate() {
        btnSelectDate.setText(myDay + "/" + myMonth + "/" + myYear);
    }

    public void onButtonSelectStationPressed(String direction) {
        if (mListener != null) {
            mListener.onFragmentSelectStationInteraction(direction);
            //mListener.onFragmentSelectStationInteraction(SelectStationActivity.SELECT_STATION_TO);
        }
    }

    public void onButtonSelectDatePressed() {
        if (mListener != null) {
            mListener.onFragmentRequestDatePickerInteraction(myYear, myMonth, myDay);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnSelectStationFrom) {
            // request station from
            onButtonSelectStationPressed(SELECT_STATION_FROM);
        } else if (id == R.id.btnSelectStationTo) {
            // request station to
            onButtonSelectStationPressed(SELECT_STATION_TO);
        } else if (id == R.id.btnSelectDate) {
            // request mDate
            onButtonSelectDatePressed();
        }
    }

    @Override
    public void setStationFrom(Station station) {
        mStationFrom = station;
        btnSelectStationFrom.setText(mStationFrom.getStationTitle());
    }

    @Override
    public void setStationTo(Station station) {
        mStationTo = station;
        btnSelectStationTo.setText(mStationTo.getStationTitle());
    }

    @Override
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        myYear = year;
        myMonth = monthOfYear;
        myDay = dayOfMonth;
        setDateToBtnSelectDate();
    }

    // save stations before rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVE_INSTANCE_STATE_STATION_FROM, mStationFrom);
        outState.putParcelable(SAVE_INSTANCE_STATE_STATION_TO, mStationTo);

        outState.putInt(SAVE_INSTANCE_STATE_DATE_YEAR, myYear);
        outState.putInt(SAVE_INSTANCE_STATE_DATE_MONTH, myMonth);
        outState.putInt(SAVE_INSTANCE_STATE_DATE_DAY, myDay);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentSelectStationInteraction(String direction);

        void onFragmentRequestDatePickerInteraction(int startYear, int startMonthOfYear, int startDayOfMonth);
    }
}

