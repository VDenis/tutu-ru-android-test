package com.app.denis.tutuapp.ui.selectStation.GroupStationAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.denis.tutuapp.R;

/**
 * Created by Denis on 09.11.2016.
 */

public class ViewHolder1_Country extends RecyclerView.ViewHolder {
    private TextView label1;

    public ViewHolder1_Country(View v) {
        super(v);
        label1 = (TextView) v.findViewById(R.id.text1);
    }

    public TextView getLabel1() {
        return label1;
    }

    public void setLabel1(TextView label1) {
        this.label1 = label1;
    }
}
