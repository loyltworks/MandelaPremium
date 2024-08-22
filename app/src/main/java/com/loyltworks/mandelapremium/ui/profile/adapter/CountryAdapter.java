package com.loyltworks.mandelapremium.ui.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.loyltworks.mandelapremium.R;
import com.loyltworks.mandelapremium.model.LstCountryDetail;
import com.loyltworks.mandelapremium.model.LstCountryDetail;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<LstCountryDetail> {
    private Context context;
    private ArrayList<LstCountryDetail> locationStateCities;


    public CountryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<LstCountryDetail> location) {
        super(context, resource, location);
        this.context = context;
        this.locationStateCities = location;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public LstCountryDetail getItem(int position) {
        return locationStateCities.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.spinner_row, null);
        }

        TextView category = (TextView) convertView.findViewById(R.id.item_spinner);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)category.getLayoutParams();
        layoutParams.setMargins(8, 0, 0, 0);
        category.setLayoutParams(layoutParams);
        category.setText(this.getItem(position).getCountryName());

        //AppController.hideProgressDialog(context);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.spinner_popup_row, null);
        }
        TextView category = (TextView) convertView.findViewById(R.id.item_spinner);
        category.setText(this.getItem(position).getCountryName());
        //AppController.hideProgressDialog(context);

        //Log.d("======DATA=======",this.getItem(position).getName());
        return convertView;
    }
}
