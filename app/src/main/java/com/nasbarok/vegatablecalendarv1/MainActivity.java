package com.nasbarok.vegatablecalendarv1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public VegetableCalendarDBHelper vegetableCalendarDB;
    List<VegetableCalendar> vegetableCalendars;
    List<VegetableCalendar> myVegetableGardenCalendars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDb();


        launchHomeFragment();
    }


    public void launchHomeFragment(){
        HomeVegetableCalendarFragment homeFragment = HomeVegetableCalendarFragment.newInstance();
        FragmentManager vegetableCalendarFragmentManager=getSupportFragmentManager();
        FragmentTransaction vegetableCalendarFragmentTransaction=vegetableCalendarFragmentManager.beginTransaction();
        vegetableCalendarFragmentTransaction.replace(R.id.content_main,homeFragment,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();
    }

    public void launchFilteredVegetableCalendarFragment(List<VegetableCalendar> vegetableCalendars){
        VegetableCalendarFragment vegetableCalendarFragmentInstance=VegetableCalendarFragment.newInstance(vegetableCalendars);
        FragmentManager vegetableCalendarFragmentManager=getSupportFragmentManager();
        FragmentTransaction vegetableCalendarFragmentTransaction=vegetableCalendarFragmentManager.beginTransaction();
        vegetableCalendarFragmentTransaction.replace(R.id.content_main,vegetableCalendarFragmentInstance,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();
    }


    public void launchMyPreferences(){
        UserInformationsFragment userInformationsFragmentInstance = UserInformationsFragment.newInstance();
        FragmentManager userInformationsManager=getSupportFragmentManager();
        FragmentTransaction userInformationsFragmentTransaction=userInformationsManager.beginTransaction();
        userInformationsFragmentTransaction.replace(R.id.content_main,userInformationsFragmentInstance,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();

    }

    //launcher
    public void launchVegetableCalendarFragment(){
        FragmentManager vegetableCalendarFragmentManager=getSupportFragmentManager();
        FragmentTransaction vegetableCalendarFragmentTransaction=vegetableCalendarFragmentManager.beginTransaction();
        VegetableCalendarFragment vegetableCalendarFragment = VegetableCalendarFragment.newInstance(vegetableCalendars);
        vegetableCalendarFragmentTransaction.replace(R.id.content_main,vegetableCalendarFragment,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();

    };

    public void launchMyVegetableGardenFragment(){
        myVegetableGardenCalendars = vegetableCalendarDB.fillMyVegetableGarden();
        MyVegetableGardenFragment vegetableCalendarFragmentInstance=MyVegetableGardenFragment.newInstance(myVegetableGardenCalendars);
        FragmentManager myVegetableGardenFragmentManager=getSupportFragmentManager();
        FragmentTransaction myVegetableGardenFragmentTransaction=myVegetableGardenFragmentManager.beginTransaction();
        myVegetableGardenFragmentTransaction.replace(R.id.content_main,vegetableCalendarFragmentInstance,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();
    }


    //uilts

    public void initDb(){
        //initiate db
        InputStream inputStream = getResources().openRawResource(R.raw.vegetable_calendar_db);
        vegetableCalendarDB = new VegetableCalendarDBHelper(this);
        vegetableCalendarDB.createDB(inputStream);
        vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();
    }

}
