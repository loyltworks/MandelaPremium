package com.loyltworks.mandelapremium.ui.help.adapter;

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
import com.loyltworks.mandelapremium.model.ObjHelpTopicList;

import java.util.ArrayList;

public class HelptopicsAdapter extends ArrayAdapter<ObjHelpTopicList> {
    private Context context;
    private ArrayList<ObjHelpTopicList> helpTopicsList;


    public HelptopicsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ObjHelpTopicList> helpTopicsList) {
        super(context, resource, helpTopicsList);
        this.context = context;
        this.helpTopicsList = helpTopicsList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public ObjHelpTopicList getItem(int position) {
        return helpTopicsList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.spinner_row, null);
        }

        TextView category = convertView.findViewById(R.id.item_spinner);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)category.getLayoutParams();
        layoutParams.setMargins(8, 0, 0, 0);
        category.setLayoutParams(layoutParams);
        category.setText(this.getItem(position).getHelpTopicName());

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
        TextView category = convertView.findViewById(R.id.item_spinner);
        category.setText(this.getItem(position).getHelpTopicName());
        //AppController.hideProgressDialog(context);

        //Log.d("======DATA=======",this.getItem(position).getName());
        return convertView;
    }
}
