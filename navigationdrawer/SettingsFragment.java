package com.example.user.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SettingsFragment extends Fragment {
    ArrayList<SpinnerDropDownModel> modelList=new ArrayList<>();
    SpinnerDropDownModel model[];
    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date someDate=new Date();
    Date today = Calendar.getInstance().getTime();
    double physRhythm=(double)23.688437;
    double emRhythm=(double)28.426125;
    double intelRhythm=(double)33.163812;
    boolean checkFirstSpinner=false;
    boolean checkSecondSpinner=false;
    double physFirst=(double)100;
    double emFirst=(double)100;
    double intelFirst=(double)100;
    double physSecond=(double)100;
    double emSecond=(double)100;
    double intelSecond=(double)100;
    final double Pi = 3.1415926536;
    RoundedHorizontalProgressBar physBar;
    RoundedHorizontalProgressBar emBar;
    RoundedHorizontalProgressBar intelBar;
    RoundedHorizontalProgressBar allBar;
    TextView physText;
    TextView emText;
    TextView intelText;
    TextView allText;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Совместимость");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        modelList=MainActivity.listSpinner;
        model=modelList.toArray(new SpinnerDropDownModel[modelList.size()]);
        View rootView = inflater.inflate(R.layout.settings, container, false);
        physBar=(RoundedHorizontalProgressBar)rootView.findViewById(R.id.progress_bar_1);
        emBar=(RoundedHorizontalProgressBar)rootView.findViewById(R.id.progress_bar_2);
        intelBar=(RoundedHorizontalProgressBar)rootView.findViewById(R.id.progress_bar_3);
        allBar=(RoundedHorizontalProgressBar)rootView.findViewById(R.id.progress_bar_4);
        physText=(TextView)rootView.findViewById(R.id.physSettings);
        emText=(TextView)rootView.findViewById(R.id.emSettings);
        intelText=(TextView)rootView.findViewById(R.id.intelSettings);
        allText=(TextView)rootView.findViewById(R.id.allSettings);
        final Spinner spinner=(Spinner)rootView.findViewById(R.id.spinnerSettings);
        final Spinner spinner1=(Spinner)rootView.findViewById(R.id.spinnerSettings1);
        SpinnerSettingsFragment adapter=new SpinnerSettingsFragment(this.getActivity(),R.layout.spinner_drop_down,model);
        SpinnerSettingsFragment adapter1=new SpinnerSettingsFragment(this.getActivity(),R.layout.spinner_drop_down,model);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(getContext(),model[position].age,Toast.LENGTH_SHORT).show();
                String allDate=model[position].age;
                String strDaySet=""+allDate.charAt(4)+allDate.charAt(5);
                String strMonthSet=""+allDate.charAt(7)+allDate.charAt(8);
                String  strYearSet=""+allDate.charAt(10)+allDate.charAt(11);
                try     {
                    someDate = formatter.parse(strMonthSet+"/"+strDaySet+"/"+strYearSet);
                }
                catch(ParseException pe)    {
                    System.out.println("Parser Exception");
                }
                checkFirstSpinner=true;
                int daysAgo= Days.daysBetween(new DateTime(someDate), new DateTime(today)).getDays();
                physFirst=Math.sin(2*Pi/physRhythm*daysAgo)*100;
                emFirst=Math.sin(2*Pi/emRhythm*daysAgo)*100;
                intelFirst=Math.sin(2*Pi/intelRhythm*daysAgo)*100;
                if (checkFirstSpinner && checkSecondSpinner)
                    setProgress();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(),model[position].age,Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),model[position].age,Toast.LENGTH_SHORT).show();
                String allDate=model[position].age;
                String strDaySet=""+allDate.charAt(4)+allDate.charAt(5);
                String strMonthSet=""+allDate.charAt(7)+allDate.charAt(8);
                String  strYearSet=""+allDate.charAt(10)+allDate.charAt(11);
                try     {
                    someDate = formatter.parse(strMonthSet+"/"+strDaySet+"/"+strYearSet);
                }
                catch(ParseException pe)    {
                    System.out.println("Parser Exception");
                }
                checkSecondSpinner=true;
                int daysAgo= Days.daysBetween(new DateTime(someDate), new DateTime(today)).getDays();
                physSecond=Math.sin(2*Pi/physRhythm*daysAgo)*100;
                emSecond=Math.sin(2*Pi/emRhythm*daysAgo)*100;
                intelSecond=Math.sin(2*Pi/intelRhythm*daysAgo)*100;
                if (checkFirstSpinner && checkSecondSpinner)
                    setProgress();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }
    private void setProgress()
    {
        int a=(200-(int)Math.round(Math.abs(physFirst-physSecond)))/2;
        int b=(200-(int)Math.round(Math.abs(emFirst-emSecond)))/2;
        int c=(200-(int)Math.round(Math.abs(intelFirst-intelSecond)))/2;
        String aStr="Физическая: "+Integer.toString(a)+"%";
        String bStr="Эмоциональная: "+Integer.toString(b)+"%";
        String cStr="Интеллектуальная: "+Integer.toString(c)+"%";
        String allStr="Общая совместимость: "+Integer.toString((a+b+c)/3)+"%";
        physText.setText(aStr);
        emText.setText(bStr);
        intelText.setText(cStr);
        allText.setText(allStr);
        physBar.animateProgress(2000,0,a);
        emBar.animateProgress(2000,0,b);
        intelBar.animateProgress(2000,0,c);
        allBar.animateProgress(2000,0,(a+b+c)/3);
    }

}
