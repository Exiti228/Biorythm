package com.example.user.navigationdrawer;



import android.app.Fragment;

import android.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements AddDialogFragment.OnCompleteListener {
    String[] data = {"one", "two", "three", "four", "five"};
    UserModel[] userItems={};
    ArrayList<UserModel> list=new ArrayList<>();
    static int curAd=1;
    static boolean accurateAlg=false;
    private String[] mItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private ListView mDrawerListView1;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    static boolean firstDownload=true;
    TextView setNameView;
    TextView setDateView;
    TextView setDaysView;
    TextView setAgesView;
    DrawerAdapter adapter1;
    DrawerAdapter adapter;
    DrawerUser adapter2;
    DrawerAdapter adapter3;
    static boolean s=true;
    SharedPreferences namePreferences;
    SharedPreferences.Editor nameEditor;
    static ArrayList<SpinnerDropDownModel> listSpinner=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setNameView=(TextView)findViewById(R.id.setName);
        setDateView=(TextView)findViewById(R.id.setDate);
        setDaysView=(TextView)findViewById(R.id.setDays);
        setAgesView=(TextView)findViewById(R.id.setAges);
        mTitle = getTitle();
        mItemTitles = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        mDrawerListView1=(ListView) findViewById(R.id.right_drawer);
        setupToolbar();

        ItemModel[] dItems1 = new ItemModel[]{
                new ItemModel(R.drawable.ic_action_plus, "Добавить пользователя"),
                new ItemModel(R.drawable.ic_action_people, "Список пользователей"),
        };
        ItemModel[] dItems3 = new ItemModel[]{
                new ItemModel(R.drawable.ic_action_plus, "Добавить пользователя"),
                new ItemModel(R.drawable.ic_action_left, "Назад в Меню"),
        };
        adapter3=new DrawerAdapter(this,R.layout.item_row,dItems3);
         adapter = new DrawerAdapter(this, R.layout.item_row, dItems1);
        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setOnItemClickListener(new ItemClickListener());
        mDrawerListView1.setOnItemClickListener(new UserClickListener());


        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();


        ItemModel[] dItems2=new ItemModel[]{
                new ItemModel(R.drawable.ic_action_home, "Биоритмы"),
                new ItemModel(R.drawable.ic_action_cloud, "Прогноз биоритмов"),
                new ItemModel(R.drawable.ic_action_sov, "Совместимость"),
                new ItemModel(R.drawable.ic_action_help, "Помощь"),
                new ItemModel(R.drawable.ic_action_about, "О программе"),
        };

        namePreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        nameEditor=namePreferences.edit();

        adapter2=new DrawerUser(this,R.layout.user_row,userItems);
        adapter1=new DrawerAdapter(this, R.layout.item_row, dItems2);
        mDrawerListView1.setAdapter(adapter1);

        setDate();
        downloadUser();
        android.support.v4.app.Fragment fragment1 = null;
        if (firstDownload)
        {
            fragment1=new GraphFragment();
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            fragmentManager1.beginTransaction().replace(R.id.content_frame, fragment1).commit();
            mDrawerLayout.closeDrawers();
            firstDownload=false;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int color = ContextCompat.getColor(this, R.color.white);
        toolbar.getNavigationIcon().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);







    }
    public void saveDate(String name,String date,String daysAgo,String old)
    {

        nameEditor.remove("nameUser");
        nameEditor.remove("dateUser");
        nameEditor.remove("daysAgoUser");
        nameEditor.remove("oldUser");

        nameEditor.putString("nameUser",name);
        nameEditor.putString("dateUser",date);
        nameEditor.putString("daysAgoUser",daysAgo);
        nameEditor.putString("oldUser",old);
        nameEditor.apply();
        String sq=namePreferences.getString("nameUser","s");

    }
    String nameLol,dateLol,daysAgoLol,oldLol;
    public void setDate()
    {

        String name=namePreferences.getString("nameUser","Гость");
        String date=namePreferences.getString("dateUser","Дата");
        String daysAgo=namePreferences.getString("daysAgoUser","Дней прошло: ");
        String old=namePreferences.getString("oldUser","Возраст: ");
        nameLol=name;
        dateLol=date;
        daysAgoLol=daysAgo;
        oldLol=old;
        setNameView.setText(name);
        setDateView.setText(date);
        setDaysView.setText(daysAgo);
        setAgesView.setText(old);


    }




    @Override
    public void onComplete(ReturnUser returnUser,boolean a,int id) {
        //Toast.makeText(this,returnUser.name,Toast.LENGTH_SHORT).show();
        if (!a)
        {
            DatePicker xl=returnUser.date;
            int sDay=xl.getDayOfMonth();
            int sMonth=xl.getMonth();
            int sYear=xl.getYear();
            list.add(new UserModel(returnUser.name,R.drawable.ic_action_correct,R.drawable.ic_action_delete,returnUser.checked,sDay,sMonth,sYear));

        }
        else
        {
            list.get(id).name=returnUser.name;
            list.get(id).day=returnUser.date.getDayOfMonth();
            list.get(id).month=returnUser.date.getMonth();
            list.get(id).year=returnUser.date.getYear();
            list.get(id).checked=returnUser.checked;
        }
        updateModels(list);

    }
    public void updateModels(ArrayList<UserModel> list)
    {

        UserModel bol[]=list.toArray(new UserModel[list.size()]);
        userItems=Arrays.copyOf(bol,bol.length);
        listSpinner.clear();
        for (int i=0;i<userItems.length;++i)
        {
            String a=userItems[i].name;
            String d=Integer.toString(userItems[i].day);
            String m=Integer.toString(userItems[i].month+1);
            String y=Integer.toString(userItems[i].year);
            if (m.length()==1)
                m="0"+m;
            if (d.length()==1)
                d="0"+d;


            String b="ДР: "+d+"."+m+"."+y;
            listSpinner.add(new SpinnerDropDownModel(a,b));
        }

        adapter2=new DrawerUser(this,R.layout.user_row,userItems);
        putUser();
        if (curAd==2)
        {
            mDrawerListView1.setAdapter(adapter2);
        }

    }
    private void putUser()
    {
        nameEditor.clear();
        for (int i=1;i<=userItems.length;++i)
        {
            String n="name"+Integer.toString(i);
            nameEditor.putString(n+"str",userItems[i-1].name);
            nameEditor.putBoolean(n+"bool",userItems[i-1].checked);
            nameEditor.putInt("day"+Integer.toString(i),userItems[i-1].day);
            nameEditor.putInt("month"+Integer.toString(i),userItems[i-1].month);
            nameEditor.putInt("year"+Integer.toString(i),userItems[i-1].year);
        }
        nameEditor.putString("nameUser",nameLol);
        nameEditor.putString("dateUser",dateLol);
        nameEditor.putString("daysAgoUser",daysAgoLol);
        nameEditor.putString("oldUser",oldLol);
        nameEditor.apply();
    }
    private void downloadUser()
    {

        for (int i=1;i<=100;++i)
        {
            String n="name"+Integer.toString(i);
            String name=namePreferences.getString(n+"str","---__---");
            Boolean isChecked=namePreferences.getBoolean(n+"bool",false);
            int day=namePreferences.getInt("day"+Integer.toString(i),-1);
            int month=namePreferences.getInt("month"+Integer.toString(i),-1);
            int year=namePreferences.getInt("year"+Integer.toString(i),-1);
            if (day==-1 && month==-1 && year==-1)
                break;
            else
            {
                list.add(new UserModel(name,R.drawable.ic_action_correct,R.drawable.ic_action_delete,isChecked,day,month,year));

            }
       }
        updateModels(list);
    }
    public void OnClickDel(View view)
    {
        int position = (Integer)view.getTag();
       // Toast.makeText(this,Integer.toString(position),Toast.LENGTH_SHORT).show();
        list.remove(position);
        updateModels(list);
    }
    //Cor
    public void OnClickCor(View view)
    {


        int position=(Integer)view.getTag();
        //DatePicker refact=list.get(position).picker;
        int day=list.get(position).day;
        int month=list.get(position).month;
        int year=list.get(position).year;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = AddDialogFragment.newInstance(list.get(position).name,day,month,year,list.get(position).checked,true,position);
        FragmentManager manager=getSupportFragmentManager();
        newFragment.show(manager,"dialog");



    }



    //ListView
    private class ItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            // на основании выбранного элемента меню
            // вызываем соответственный ему фрагмент
            switch (position) {
                case 0:
                    FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft1.remove(prev);
                    }
                    ft1.addToBackStack(null);
                    Calendar c=Calendar.getInstance();
                    int dayUser=c.get(Calendar.DAY_OF_MONTH);
                    int monthUser=c.get(Calendar.MONTH);
                    int yearUser=c.get(Calendar.YEAR);
                    DialogFragment newFragment=AddDialogFragment.newInstance("",dayUser,monthUser,yearUser,false,false,0);
                    FragmentManager manager=getSupportFragmentManager();
                    newFragment.show(manager,"dialog");

                    break;
                case 1:
                        if (curAd==1)
                        {
                            curAd=2;
                            mDrawerListView1.setAdapter(adapter2);
                            mDrawerListView.setAdapter(adapter3);
                        }

                        else if (curAd==2)
                        {
                        curAd=1;
                        mDrawerListView.setAdapter(adapter);
                        mDrawerListView1.setAdapter(adapter1);

                         }



            }

        }
    }

    //ListView1
    private class UserClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            android.support.v4.app.Fragment fragment1 = null;

            // на основании выбранного элемента меню
            // вызываем соответственный ему фрагмент
            if (curAd==2)
            {
                String umName=userItems[position].name;
                accurateAlg=userItems[position].checked;
                //DatePicker umDate=userItems[position].picker;

                setNameView.setText(umName);
                int daySet=userItems[position].day;
                int monthSet=userItems[position].month+1;
                int yearSet=userItems[position].year;
                String strDaySet=Integer.toString(daySet);
                String strMonthSet=Integer.toString(monthSet);
                if (daySet>=1 && daySet<=9)
                    strDaySet="0"+strDaySet;
                if (monthSet>=1 && monthSet<=9)
                    strMonthSet="0"+strMonthSet;
                String setStrView=strDaySet+"."+strMonthSet+"."+Integer.toString(yearSet);
                setDateView.setText(setStrView);

                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                Date someDate=new Date();
                Date today = Calendar.getInstance().getTime();

                try     {
                    someDate = formatter.parse(strMonthSet+"/"+strDaySet+"/"+Integer.toString(yearSet));
                }
                catch(ParseException pe)    {
                    System.out.println("Parser Exception");
                }
                int daysBetween=Days.daysBetween(new DateTime(someDate), new DateTime(today)).getDays();
                String strDaysBetween = "Дней прошло: "+Integer.toString(daysBetween);
                setDaysView.setText(strDaysBetween);
                float sq=(float)daysBetween/365;
                String strSetAge="Возраст: "+String.format("%.2f",sq);
                setAgesView.setText(strSetAge);
                saveDate(umName,setStrView,strDaysBetween,strSetAge);
                fragment1=new GraphFragment();
                if (fragment1!=null)
                {

                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.content_frame, fragment1).commit();
                    mDrawerLayout.closeDrawers();
                }
            }
            else
            {

                mDrawerLayout.closeDrawers();
                switch (position)
                {
                    case 0:
                        fragment1=new GraphFragment();

                        break;
                    case 1:
                        fragment1=new PredictionFragment();
                        break;
                    case 2:
                        fragment1=new SettingsFragment();

                        break;
                    case 3:
                        fragment1=new HelpFragment();
                        break;
                    case 4:
                        fragment1=new AboutFragment();
                        break;



                }
                if (fragment1!=null)
                {

                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.content_frame, fragment1).commit();
                    mDrawerLayout.closeDrawers();
                }

            }


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        // Это необходимо для изменения иконки на основании текущего состояния
        mDrawerToggle.syncState();
    }


}
