package com.example.user.navigationdrawer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddDialogFragment extends DialogFragment {

    CheckBox checkBox;
    DatePicker picker;
    EditText nameText;
    static AddDialogFragment newInstance(String name,int day,int month,int year,boolean checked,boolean complete,int position) {
        AddDialogFragment f = new AddDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putInt("day",day);
        args.putInt("month",month);
        args.putInt("year",year);
        args.putBoolean("checked",checked);
        args.putBoolean("complete",complete);
        args.putInt("position",position);
        f.setArguments(args);

        return f;
    }
    String nameUser;
    int dayUser,monthUser,yearUser;
    boolean checkedUser;
    boolean complete;
    int position;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

         nameUser=getArguments().getString("name");
         dayUser=getArguments().getInt("day");
         monthUser=getArguments().getInt("month");
         yearUser=getArguments().getInt("year");
        checkedUser=getArguments().getBoolean("checked");
         complete=getArguments().getBoolean("complete");
         position=getArguments().getInt("position");
        // Inflate and set the layout for the dialog

        builder.setView(inflater.inflate(R.layout.dialog_adduser, null))
                // Add action buttons
                .setPositiveButton(Html.fromHtml("<font color='#00039F'>принять</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {




                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#00039F'>отмена</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            AddDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


    Calendar cl=Calendar.getInstance();
    @Override
    public void onResume()
    {
        nameText=(EditText)getDialog().findViewById(R.id.name);
        picker=(DatePicker)getDialog().findViewById(R.id.datePicker);
        checkBox=(CheckBox)getDialog().findViewById(R.id.checkBox);
        nameText.setText(nameUser);
        picker.init(yearUser,monthUser,dayUser,null);
        checkBox.setChecked(checkedUser);
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);

            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    Boolean corDate=true;
                    Boolean corName=true;
                    int curDay=cl.get(Calendar.DAY_OF_MONTH);
                    int curMonth=cl.get(Calendar.MONTH);
                    int curYear=cl.get(Calendar.YEAR);
                    int dayPicker=picker.getDayOfMonth();
                    int monthPicker=picker.getMonth();
                    int yearPicker=picker.getYear();
                    if (yearPicker>curYear)
                        corDate=false;
                    else if (yearPicker==curYear && curMonth<monthPicker)
                        corDate=false;
                    else if (yearPicker==curYear && curMonth==monthPicker && curDay<dayPicker)
                        corDate=false;
                    String userName=nameText.getText().toString();
                    if (userName.length()==0)
                        corName=false;
                    if (!corDate)
                        Toast.makeText(getContext(),
                                "Дата рождения не может быть меньше текущей даты",
                                Toast.LENGTH_SHORT).show();
                    else if (!corName)
                        Toast.makeText(getContext(),
                                "Имя пользователя должно иметь один или более символов",
                                Toast.LENGTH_SHORT).show();
                    else
                    {

                        ReturnUser returnUser=new ReturnUser(userName,picker,checkBox.isChecked());
                        mListener.onComplete(returnUser,complete,position);
                        d.dismiss();

                    }
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }
    }
    public static interface OnCompleteListener {
        public abstract void onComplete(ReturnUser returnUser,boolean a,int id);
    }

    private OnCompleteListener mListener;

    @Override
    public void onAttach(Activity activity) {


        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }


}
