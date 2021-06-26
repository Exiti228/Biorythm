package com.example.user.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerSettingsFragment extends ArrayAdapter<SpinnerDropDownModel> {
    SpinnerDropDownModel[] data;
    Context mContext;
    int layoutId;
    public SpinnerSettingsFragment(Context mContext, int layoutId, SpinnerDropDownModel[] data) {
        super(mContext, layoutId, data);
        this.data=data;
        this.mContext=mContext;
        this.layoutId=layoutId;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutId, parent, false);

        TextView name= (TextView) listItem.findViewById(R.id.nameSpinner);
        TextView date = (TextView) listItem.findViewById(R.id.ageSpinner);

        // получаем данные из переданого массива


        name.setText(data[position].nameUser);
        date.setText(data[position].age);

        return listItem;
    }
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}
