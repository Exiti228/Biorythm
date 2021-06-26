package com.example.user.navigationdrawer;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import org.achartengine.ChartFactory;


import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.w3c.dom.Text;

import java.util.Calendar;

public class GraphFragment extends Fragment {
     int gl=0;
    ImageView right_arrow;
    ImageView left_arrow;
     int lor=0;
    LinearLayout mChartGraph;
     int daysAgo;
    TextView setDateGraph;
    DonutProgress donutProgress1;
    DonutProgress donutProgress2;
    DonutProgress donutProgress3;
    boolean accurateAlg;
    static double physRhythm;
    static double emRhythm;
    String monthAr[]={"января","февраля","марта","апреля","мая","июня","июля","августа","сентября","октября","ноября","декабря",};
    Calendar cal=Calendar.getInstance();
    static double intelRhythm;
    private XYSeries seriesPhys;
    private XYSeries seriesEm;
    private  XYSeries seriesIntel;
    private GraphicalView mChart;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeriesRenderer mCurrentRenderer1;
    private XYSeriesRenderer mCurrentRenderer2;
    private XYSeriesRenderer mCurrentRenderer3;
    final double Pi = 3.1415926536;
    static String dateForPrediction="30 мая 2019";
    static int daysForPrediction=0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Биоритмы");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         seriesPhys = new XYSeries("Физический");
         seriesEm=new XYSeries("Эмоциональный");
         seriesIntel=new XYSeries("Интеллектуальный");
        View rootView = inflater.inflate(R.layout.graph_layout, container, false);
         mChartGraph=(LinearLayout)rootView.findViewById(R.id.chart);
         setDateGraph=(TextView)rootView.findViewById(R.id.setDateGraph);
         left_arrow=(ImageView)rootView.findViewById(R.id.left_arrow);
        right_arrow=(ImageView)rootView.findViewById(R.id.right_arrow);
        TextView tv=(TextView)getActivity().findViewById(R.id.setName);
        donutProgress1=(DonutProgress)rootView.findViewById(R.id.donut_progress1);
        donutProgress2=(DonutProgress)rootView.findViewById(R.id.donut_progress2);
        donutProgress3=(DonutProgress)rootView.findViewById(R.id.donut_progress3);
        donutProgress1.setMax(100);
        donutProgress2.setMax(100);
        donutProgress3.setMax(100);
        donutProgress1.setFinishedStrokeColor(Color.RED);
        donutProgress2.setFinishedStrokeColor(Color.GREEN);
        donutProgress3.setFinishedStrokeColor(Color.BLUE);
        donutProgress1.setProgress(40f);
        String str=tv.getText().toString();
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seriesPhys = new XYSeries("Физический");
                seriesEm=new XYSeries("Эмоциональный");
                seriesIntel=new XYSeries("Интеллектуальный");
                mDataset=new XYMultipleSeriesDataset();
                mRenderer=new XYMultipleSeriesRenderer();
                lor=2;
                initChart();
                daysAgo+=15;
                daysForPrediction=daysAgo+7;
                initPhysSeries(seriesPhys);
                initEmSeries(seriesEm);
                initIntelSeries(seriesIntel);
                mChartGraph.removeAllViews();
                mChart = ChartFactory.getCubeLineChartView(getActivity(), mDataset, mRenderer, 0.3f);
                mChartGraph.addView(mChart);
                mRenderer.setClickEnabled(true);
                mRenderer.setSelectableBuffer(40);
                mChart.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();
                                                  if (seriesSelection!=null)
                                                  {
                                                      int ind=(int)seriesSelection.getXValue();
                                                      onClickPoint(ind);
                                                      initDonutProgressPhys(ind);
                                                      initDonutProgressEm(ind);
                                                      initDonutProgressIntel(ind);
                                                     // int seriesIndex = seriesSelection.getSeriesIndex();
                                                      //Toast.makeText(getContext(),Double.toString(seriesSelection.getXValue()),Toast.LENGTH_SHORT).show();
                                                  }
                                              }
                                          }
                );

            }
        });
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 seriesPhys = new XYSeries("Физический");
                seriesEm=new XYSeries("Эмоциональный");
                 seriesIntel=new XYSeries("Интеллектуальный");
                 mDataset=new XYMultipleSeriesDataset();
                 mRenderer=new XYMultipleSeriesRenderer();
                lor=1;
                initChart();
                 daysAgo-=15;
                 daysForPrediction=daysAgo+7;
                initPhysSeries(seriesPhys);
                 initEmSeries(seriesEm);
                 initIntelSeries(seriesIntel);
                 mChartGraph.removeAllViews();
                mChart = ChartFactory.getCubeLineChartView(getActivity(), mDataset, mRenderer, 0.3f);
                mChartGraph.addView(mChart);
                mRenderer.setClickEnabled(true);
                mRenderer.setSelectableBuffer(40);
                mChart.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();
                                                  if (seriesSelection!=null)
                                                  {
                                                      int ind=(int)seriesSelection.getXValue();
                                                      onClickPoint(ind);
                                                      initDonutProgressPhys(ind);
                                                      initDonutProgressEm(ind);
                                                      initDonutProgressIntel(ind);
                                                      //int seriesIndex = seriesSelection.getSeriesIndex();
                                                     // Toast.makeText(getContext(),Double.toString(seriesSelection.getXValue()),Toast.LENGTH_SHORT).show();
                                                  }
                                              }
                                          }
                );

            }
        });

        if (str.equals("Гость"))
        {
            accurateAlg=false;
            daysAgo=1000;

        }
        else
        {

            TextView tv2=(TextView)getActivity().findViewById(R.id.setDays);
            String st=tv2.getText().toString();
            String str1="";
            for (int i=0;i<st.length();++i)
            {
                char c=st.charAt(i);
                if (c>='0' && c<='9')
                    str1+=c;
            }
            daysAgo=Integer.parseInt(str1);
            accurateAlg=MainActivity.accurateAlg;



        }
        daysForPrediction=daysAgo;
        daysAgo-=7;
        if (accurateAlg)
        {
            physRhythm=(double)23.688437;
            emRhythm=(double)28.426125;
            intelRhythm=(double)33.163812;
        }
        else
        {
            physRhythm=(double)23;
            emRhythm=(double)28;
            intelRhythm=(double)33;
        }
        initChart();
        initPhysSeries(seriesPhys);
        initEmSeries(seriesEm);
        initIntelSeries(seriesIntel);


        mChart = ChartFactory.getCubeLineChartView(getActivity(), mDataset, mRenderer, 0.3f);
        initDonutProgressPhys(7);
        initDonutProgressEm(7);
        initDonutProgressIntel(7);
        mChartGraph.removeAllViews();
        mChartGraph.addView(mChart);
        mRenderer.setClickEnabled(true);
        mRenderer.setSelectableBuffer(40);
        mChart.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v)
                                      {
                                          SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();
                                         if (seriesSelection!=null)
                                         {
                                             //int seriesIndex = seriesSelection.getSeriesIndex();
                                             int ind=(int)seriesSelection.getXValue();
                                             onClickPoint(ind);
                                             initDonutProgressPhys(ind);
                                             initDonutProgressEm(ind);
                                             initDonutProgressIntel(ind);
                                             //Toast.makeText(getContext(),Double.toString(seriesSelection.getXValue()),Toast.LENGTH_SHORT).show();
                                         }
                                      }
                                  }
        );

        return rootView;
    }

    private void initChart() {

        mDataset.addSeries(seriesPhys);
        mDataset.addSeries(seriesEm);
        mDataset.addSeries(seriesIntel);
        mCurrentRenderer1 = new XYSeriesRenderer();
        mCurrentRenderer2 = new XYSeriesRenderer();
        mCurrentRenderer3 = new XYSeriesRenderer();
        mCurrentRenderer1.setColor(Color.RED);
        mCurrentRenderer2.setColor(Color.GREEN);
        mCurrentRenderer3.setColor(Color.BLUE);
        mCurrentRenderer1.setLineWidth(5f);
        mCurrentRenderer2.setLineWidth(5f);
        mCurrentRenderer3.setLineWidth(5f);
        mCurrentRenderer1.setPointStyle(PointStyle.CIRCLE);
        mCurrentRenderer2.setPointStyle(PointStyle.CIRCLE);
        mCurrentRenderer3.setPointStyle(PointStyle.CIRCLE);
        mCurrentRenderer1.setPointStrokeWidth(10f);
        mCurrentRenderer2.setPointStrokeWidth(10f);
        mCurrentRenderer3.setPointStrokeWidth(10f);
        mRenderer.setChartTitleTextSize(28);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setZoomEnabled(false,false);
        mRenderer.setShowGridY(true);
       mRenderer.setBackgroundColor(Color.TRANSPARENT);
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(14);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        initText();
        setLabels();
        corLabels();

        mRenderer.setLabelsTextSize(25f);
        mRenderer.addSeriesRenderer(mCurrentRenderer1);
        mRenderer.addSeriesRenderer(mCurrentRenderer2);
        mRenderer.addSeriesRenderer(mCurrentRenderer3);

    }
    private void initPhysSeries(XYSeries series)
    {
        //Calendar cal=Calendar.getInstance();
        for (int i=0;i<18;++i)
        {
            series.add((double)i,Math.sin(2*Pi/physRhythm*(daysAgo+i))*100);
        }
    }
    private void initEmSeries(XYSeries series)
    {
        //Calendar cal=Calendar.getInstance();
        for (int i=0;i<18;++i)
        {
            series.add((double)i,Math.sin(2*Pi/emRhythm*(daysAgo+i))*100);
        }
    }
    private void initIntelSeries(XYSeries series)
    {
        //Calendar cal=Calendar.getInstance();
        for (int i=0;i<18;++i)
        {
            series.add((double)i,Math.sin(2*Pi/intelRhythm*(daysAgo+i))*100);
        }
    }
    private void setLabels()
    {
        for (int i=0;i<15;++i)
        {
            mRenderer.setXLabels(i);
        }
        mRenderer.addYTextLabel(0,"0%");
        mRenderer.addYTextLabel(50,"50%");
        mRenderer.addYTextLabel(95,"100%");
        mRenderer.addYTextLabel(100,"");
        mRenderer.addYTextLabel(-50,"-50%");
        mRenderer.addYTextLabel(-100,"");



    }
    //-15
    private void corLabels()
    {

        cal.add(Calendar.DATE,-7);
        String str=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        mRenderer.clearXTextLabels();
        for (int i=0;i<15;++i)
        {
            mRenderer.addXTextLabel(i,str);
            cal.add(Calendar.DATE,1);
            str=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        }
        mRenderer.setShowCustomTextGridX(true);
        mRenderer.setXLabels(0);


    }
    private void initText()
    {
        if (lor==1)
        cal.add(Calendar.DATE,-23);
        if (lor==2)
            cal.add(Calendar.DATE,7);
        int dayGraph=cal.get(Calendar.DAY_OF_MONTH);
        int monthGraph=cal.get(Calendar.MONTH);
        int yearGraph=cal.get(Calendar.YEAR);
        String setDateStr=Integer.toString(dayGraph)+" "+monthAr[monthGraph]+" "+Integer.toString(yearGraph);
        dateForPrediction=setDateStr;
        setDateGraph.setText(setDateStr);

    }
    private void onClickPoint(int ind)
    {
        cal.add(Calendar.DATE,-15+ind);
        int dayClick=cal.get(Calendar.DAY_OF_MONTH);
        int monthClick=cal.get(Calendar.MONTH);
        int yearClick=cal.get(Calendar.YEAR);
        String setClickDate=Integer.toString(dayClick)+" "+monthAr[monthClick]+" "+Integer.toString(yearClick);
        cal.add(Calendar.DATE,-ind+15);
        dateForPrediction=setClickDate;
        setDateGraph.setText(setClickDate);


    }
    private void initDonutProgressPhys(int ind)
    {
       daysAgo=daysAgo+ind;
       daysForPrediction=daysAgo;
       final float res=(float)Math.sin(2*Pi/physRhythm*daysAgo)*100;


       String s=String.format("%.2f",res)+"%";
       donutProgress1.setText(s);
       donutProgress1.setProgress(Math.abs(res));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator anim = ObjectAnimator.ofFloat(donutProgress1, "progress", 0, Math.abs(res));
                anim.setInterpolator(new DecelerateInterpolator());
                anim.setDuration(2000);
                anim.start();
            }
        });
       daysAgo=daysAgo-ind;
    }
    private void initDonutProgressEm(int ind)
    {
        daysAgo=daysAgo+ind;

        final float res=(float)Math.sin(2*Pi/emRhythm*daysAgo)*100;

        String s=String.format("%.2f",res)+"%";
        donutProgress2.setText(s);
        donutProgress2.setProgress(Math.abs(res));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator anim = ObjectAnimator.ofFloat(donutProgress2, "progress", 0, Math.abs(res));
                anim.setInterpolator(new DecelerateInterpolator());
                anim.setDuration(2000);
                anim.start();
            }
        });
        daysAgo=daysAgo-ind;
    }
    private void initDonutProgressIntel(int ind)
    {
        daysAgo=daysAgo+ind;

        final float res=(float)Math.sin(2*Pi/intelRhythm*daysAgo)*100;
        String s=String.format("%.2f",res)+"%";
        donutProgress3.setText(s);
        donutProgress3.setProgress(Math.abs(res));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator anim = ObjectAnimator.ofFloat(donutProgress3, "progress", 0, Math.abs(res));
                anim.setInterpolator(new DecelerateInterpolator());
                anim.setDuration(2000);
                anim.start();
            }
        });
        daysAgo=daysAgo-ind;
    }



}
