package com.example.user.navigationdrawer;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PredictionFragment extends Fragment {
    TextView textName;
    TextView setName;
    int curDay;
    int physDay;
    int emDay;
    int intelDay;
    double emRhythm;
    double intelRhythm;
    double physRhythm;
    final double Pi = 3.1415926536;
    TextView physFirst,physSecond,emFirst,emSecond,intelFirst,intelSecond,sost,physPrediction,emPrediction,intelPrediction;
    double resPhys;
    double resEm;
    double resIntel;
    ImageView physImage,emImage,intelImage;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Прогноз биоритмов");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.prediction, container, false);
        textName=(TextView)getActivity().findViewById(R.id.setName);
        setName=(TextView)rootview.findViewById(R.id.namePrediction);
        physFirst=(TextView)rootview.findViewById(R.id.physFirst);
        physSecond=(TextView)rootview.findViewById(R.id.physSecond);
        emFirst=(TextView)rootview.findViewById(R.id.emFirst);
        emSecond=(TextView)rootview.findViewById(R.id.emSecond);
        intelFirst=(TextView)rootview.findViewById(R.id.intelFirst);
        intelSecond=(TextView)rootview.findViewById(R.id.intelSecond);
        sost=(TextView)rootview.findViewById(R.id.sost);
        physImage=(ImageView)rootview.findViewById(R.id.physImage);
        physPrediction=(TextView)rootview.findViewById(R.id.physPrediction);
        emPrediction=(TextView)rootview.findViewById(R.id.emPrediction);
        emImage=(ImageView)rootview.findViewById(R.id.emImage);
        intelPrediction=(TextView)rootview.findViewById(R.id.intelPrediction);
        intelImage=(ImageView)rootview.findViewById(R.id.intelImage);
        String nameGet=textName.getText().toString();
        String curDate=GraphFragment.dateForPrediction;
        String setSecond=nameGet+" на "+curDate;
        setName.setText(setSecond);
        curDay=GraphFragment.daysForPrediction;
        physDay=curDay;
        emDay=curDay;
        intelDay=curDay;

        emRhythm=GraphFragment.emRhythm;
        intelRhythm=GraphFragment.intelRhythm;
        physRhythm=GraphFragment.physRhythm;
        resPhys=Math.sin(2*Pi/physRhythm*(physDay))*100;
        resEm=Math.sin(2*Pi/emRhythm*(emDay))*100;
        resIntel=Math.sin(2*Pi/intelRhythm*(intelDay))*100;

        if (resPhys>0)
            physFirst.setText("Положительный ");
        else
            physFirst.setText("Отрицательный ");
        for (int i=1;i<=40;++i)
        {
            String s="lol";
            if (resPhys>0 && calcPhys(i))
            {
                s="период: "+Integer.toString(i)+" день цикла";
                physSecond.setText(s);
                break;
            }
            if (resPhys<=0 && !calcPhys(i))
            {
                s="период: "+Integer.toString(i)+" день цикла";
                physSecond.setText(s);
                break;
            }

        }

        if (resEm>0)
            emFirst.setText("Положительный ");
        else
            emFirst.setText("Отрицательный ");
        for (int i=1;i<=40;++i)
        {
            String s="lol";
            if (resEm>0 && calcEm(i))
            {
                s="период: "+Integer.toString(i)+" день цикла";
                emSecond.setText(s);
                break;
            }
            if (resEm<=0 && !calcEm(i))
            {
                s="период: "+Integer.toString(i)+" день цикла";
                emSecond.setText(s);
                break;
            }

        }

        if (resIntel>0)
            intelFirst.setText("Положительный ");
        else
            intelFirst.setText("Отрицательный ");
        for (int i=1;i<=40;++i)
        {
            String s="lol";
            if (resIntel>0 && calcIntel(i))
            {
                s="период: "+Integer.toString(i)+" день цикла";
                intelSecond.setText(s);
                break;
            }
            if (resIntel<=0 && !calcIntel(i))
            {
                s="период: "+Integer.toString(i)+" день цикла";
                intelSecond.setText(s);
                break;
            }

        }
        String setS=String.format("%.2f",((resEm+resIntel+resPhys)/3+100)/2);
        String setSost=Integer.toString(curDay)+" дней прошло, Общее состояние: "+setS+"%";
        sost.setText(setSost);
        if (resPhys<0)
        {
            physImage.setImageResource(R.drawable.red_triangle);
            physPrediction.setText("Физическое состояние характеризутеся снижением физической и сексуальной активности. Возрастает усталость и вероятность заболеть. В это время необходио уменьшить или (если возможно) исключить физические аспекты работы. Это не самое благоприятное время для употребления алкоголя.");

        }
        else
        {
            physImage.setImageResource(R.drawable.green_triangle);
            physPrediction.setText("Физическое состояние характеризуется периодом повышенной физической активности. Организм полон энергии, высока выносливость, высока также сексуальная активность. Тяжелый физический труд осуществляется с легкостью.Это хорошее время для занятий спортом.");
        }
        if (resEm<0)
        {
            emImage.setImageResource(R.drawable.red_triangle);
            emPrediction.setText("Эмоциональное состояние характеризуется снижением интереса к жизни и склонности к депрессиям, апатии и раздражительности (как правило, женщины сильнее реагируют). Это сложный период для творческих людей, в связи с чувством апатии. По той же причине, этот период не способствует любви.");
        }
        else
        {
            emImage.setImageResource(R.drawable.green_triangle);
            emPrediction.setText("Эмоциональное состояние характеризуется  ощущением полноты мотивации,восторга и желания творить и любить. Тем не менее, в течение этого периода, ситуации дисгармонии или отчуждение может привести к острым реакциям в связи с повышенной чувствительностью.");
        }
        if (resIntel<0)
        {
            intelImage.setImageResource(R.drawable.red_triangle);
            intelPrediction.setText("Интеллектуальное состояние характеризуется снижением умственной деятельности, ухужшается мыслительная активность и память (как правило, на мужчин это влияет сильнее). Это не очень хорошее время, чтобы вести переговоры, заключать контракты, принимать важные решения, и реализовывать новые идеи. В это время лучше выполнять простые механические задачи.");
        }
        else
        {
            intelImage.setImageResource(R.drawable.green_triangle);
            intelPrediction.setText("Интеллектуальное состояние характеризуется увеличением твореского потенциала. В этот период наиболее сложные проблемы могут быть решены с легкостью. Это хорошее время для переговоров, подписания контрактов, а также имеет большое значение для принятия решений.");
        }
        return rootview;
    }
    private boolean calcPhys(int i)
    {
        return Math.sin(2*Pi/physRhythm*(physDay-i))*100<0;
    }
    private boolean calcEm(int i)
    {
        return Math.sin(2*Pi/emRhythm*(emDay-i))*100<0;
    }
    private boolean calcIntel(int i)
    {
        return Math.sin(2*Pi/intelRhythm*(intelDay-i))*100<0;
    }
    }
