package com.nasbarok.vegatablecalendarv1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.DayStats;
import com.nasbarok.vegatablecalendarv1.model.UserInformations;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.InputStream;
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
        //test and dial if is the first acces


        if(!climateChosen()){
            launch1stPageFragment();
        }else{
            launchHomeFragment();
        }
    }

    public void launchSunAndMoonFragment(List<DayStats> dayStats,String seasonResult){
        SunAndMoonCalendarFragment sunAndMoonFragment = SunAndMoonCalendarFragment.newInstance(dayStats,seasonResult);
        FragmentManager sunAndMoonFragmentManager=getSupportFragmentManager();
        FragmentTransaction sunAndMoonFragmentTransaction=sunAndMoonFragmentManager.beginTransaction();
        sunAndMoonFragmentTransaction.replace(R.id.content_main,sunAndMoonFragment,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();
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

    public void launch1stPageFragment(){
        ParametersFragment parametersFragment = ParametersFragment.newInstance();
        FragmentManager parametersFragmentManager=getSupportFragmentManager();
        FragmentTransaction vegetableCalendarFragmentTransaction = parametersFragmentManager.beginTransaction();
        vegetableCalendarFragmentTransaction.replace(R.id.content_main,parametersFragment,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();
    }

    //utils
    public void initDb(){
        //initiate db
        InputStream inputStream = getResources().openRawResource(R.raw.vegetable_calendar_db);
        InputStream inputStreamDataKoppen = getResources().openRawResource(R.raw.vegetable_calendar_cls_details);
        vegetableCalendarDB = new VegetableCalendarDBHelper(this);
        vegetableCalendarDB.createDB(inputStreamDataKoppen,inputStream);
        vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();
    }

    public boolean climateChosen(){
        UserInformations userInformations = vegetableCalendarDB.getUserInformations();
        if(userInformations.getClimate().equals("")||userInformations.getClimate()==null){
            return false;
        }
        return true;
    }
}
