package com.app.denis.tutuapp.ui.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.app.denis.tutuapp.R;
import com.app.denis.tutuapp.model.Station;
import com.app.denis.tutuapp.model.StorageService;
import com.app.denis.tutuapp.ui.selectStation.SelectStationActivity;

import static com.app.denis.tutuapp.ui.selectStation.SelectStationActivity.SELECT_STATION_DIRECTION;
import static com.app.denis.tutuapp.ui.selectStation.SelectStationActivity.SELECT_STATION_FROM;
import static com.app.denis.tutuapp.ui.selectStation.SelectStationActivity.SELECT_STATION_RESULT;
import static com.app.denis.tutuapp.ui.selectStation.SelectStationActivity.SELECT_STATION_TO;

public class MainActivity extends AppCompatActivity implements TitleAdapter.OnItemClickListener, ScheduleFragment.OnFragmentInteractionListener, DatePickerDialog.OnDateSetListener {

    // code for activity for result
    private static final int REQUEST_CODE_STATION_FROM = 1;
    private static final int REQUEST_CODE_STATION_TO = 2;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    //private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerTitles;

    // drawer check prev position
    private int oldPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "onCreate: ");

        //mTitle = mDrawerTitle = getTitle();
        mTitle = getTitle();
        mDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);

        // improve performance by indicating the list if fixed size.
        mDrawerList.setHasFixedSize(true);
        mDrawerList.setAdapter(new TitleAdapter(mDrawerTitles, this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                setTitle(mTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);

            // TODO Debug prefetch data
            new Thread(new Runnable() {
                @Override
                public void run() {
                    StorageService.getData(MainActivity.this);
                }
            }).start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private void selectItem(int position) {
        setTitle(mDrawerTitles[position]);

        // update the main content by replacing fragments
        if (oldPosition != position) {
            if (position == 1) {
                // show about page
                Fragment fragment = new AboutFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            } else if (position == 0) {
                // show schedule page
                Fragment fragment = ScheduleFragment.newInstance();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
            oldPosition = position;
        }
        mDrawerLayout.closeDrawer(mDrawerList);

        // Check drawer selection
        //Toast.makeText(this, "Select: \"" + mDrawerTitles[position] + "\"", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        // if currently navigation drawer is open - close navigation drawer
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
            return;
        } else if (getFragmentManager().findFragmentById(R.id.content_frame) instanceof AboutFragment) {
            // if currently about page, navigate to the schedule page
            selectItem(0);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view, int position) {
        selectItem(position);
    }


    // Get Selected Station result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Station station = data.getParcelableExtra(SELECT_STATION_RESULT);
            UpdateFragmentFromActivity update = (UpdateFragmentFromActivity) getFragmentManager()
                    .findFragmentById(R.id.content_frame);
            switch (requestCode) {
                case REQUEST_CODE_STATION_FROM: {
                    update.setStationFrom(station);
                    break;
                }
                case REQUEST_CODE_STATION_TO: {
                    update.setStationTo(station);
                    break;
                }
            }
        }
    }

    @Override
    public void onFragmentSelectStationInteraction(String direction) {
        Intent intent = new Intent(this, SelectStationActivity.class);
        if (direction.equals(SELECT_STATION_FROM)) {
            intent.putExtra(SELECT_STATION_DIRECTION, direction);
            startActivityForResult(intent, REQUEST_CODE_STATION_FROM);
        } else if (direction.equals(SELECT_STATION_TO)) {
            intent.putExtra(SELECT_STATION_DIRECTION, direction);
            startActivityForResult(intent, REQUEST_CODE_STATION_TO);
        }
    }

    @Override
    public void onFragmentRequestDatePickerInteraction(int startYear, int startMonthOfYear, int startDayOfMonth) {
        new DatePickerDialog(this, this, startYear, startMonthOfYear, startDayOfMonth).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return super.onCreateDialog(id);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart: ");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        UpdateFragmentFromActivity update = (UpdateFragmentFromActivity) getFragmentManager()
                .findFragmentById(R.id.content_frame);
        update.setDate(year, month, dayOfMonth);
    }
}


