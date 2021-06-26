package com.example.user.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DrawerUser extends ArrayAdapter<UserModel> {
    Context mContext;
    int layoutId;
    UserModel data[] = null;
    boolean checked;
    ListView mDrawerListView1;
    public DrawerUser(Context mContext, int layoutId, UserModel[] data) {
        super(mContext, layoutId, data);
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutId, parent, false);
        View rowView = inflater.inflate(R.layout.user_row, parent, false);
        mDrawerListView1=(ListView)rowView.findViewById(R.id.right_drawer);
        ImageView imageCor = (ImageView) listItem.findViewById(R.id.corUser1);
        ImageView imageDel=(ImageView)listItem.findViewById(R.id.delUser1) ;
        TextView mName = (TextView) listItem.findViewById(R.id.textUser1);
        TextView year=(TextView) listItem.findViewById(R.id.bornDay1);
        // получаем данные из переданого массива
        UserModel model = data[position];
        int dayPicker=model.day;
        int monthPicker=model.month+1;
        int yearPicker=model.year;
        imageCor.setImageResource(model.iconCor);
        imageDel.setImageResource(model.iconDel);
        mName.setText(model.name);
        String monSet=Integer.toString(monthPicker);
        String daySet=Integer.toString(dayPicker);
        if (dayPicker>=1 && dayPicker<=9)
        {
            daySet="0"+daySet;
        }
        if (monthPicker>=1 && monthPicker<=9)
        {
            monSet="0"+monSet;
        }
        String date="ДР: "+daySet+"."+monSet+"."+Integer.toString(yearPicker);
        year.setText(date);
       // String str=Integer.toString(position);
        imageCor.setTag(position);
        imageDel.setTag(position);


        return listItem;
    }



}