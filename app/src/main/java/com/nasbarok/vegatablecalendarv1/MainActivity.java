package com.nasbarok.vegatablecalendarv1;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public VegetableCalendarDBHelper vegetableCalendarDB;
    List<VegetableCalendar> vegetableCalendars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initiate db
        InputStream inputStream = getResources().openRawResource(R.raw.vegetable_calendar_db);
        vegetableCalendarDB = new VegetableCalendarDBHelper(this);
        vegetableCalendarDB.createDB(inputStream);
        vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();
        launchVegetableCalendarFragment(vegetableCalendars);

    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List <VegetableCalendar> vegetableCalendarList;
                vegetableCalendarList = vegetableCalendarDB.findVegetableCalendar(query);
                if(vegetableCalendarList.size()>=1){
                    vegetableCalendars = vegetableCalendarList;
                    launchVegetableCalendarFragment(vegetableCalendars,menu);
                }
                if(vegetableCalendarList.size()<1){
                    vegetableCalendars = new ArrayList<>();
                    launchVegetableCalendarFragment(vegetableCalendars,menu);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem renewBtn = (MenuItem) menu.findItem(R.id.app_bar_renew_search);
        renewBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();
                launchVegetableCalendarFragment(vegetableCalendars,menu);
                return false;
            }
        });

        MenuItem myVegetableGardenBtn = (MenuItem) menu.findItem(R.id.app_bar_my_vegetable_garden);
        myVegetableGardenBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                vegetableCalendars = vegetableCalendarDB.fillMyVegetableGarden();
                launchMyVegetableGardenFragment(vegetableCalendars,menu);
                return false;
            }
        });

        MenuItem myPreferences = (MenuItem) menu.findItem(R.id.app_bar_myparapeters);
        myPreferences.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                launchMyPreferences();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void launchVegetableCalendarFragment(List<VegetableCalendar> vegetableCalendars){
        VegetableCalendarFragment vegetableCalendarFragmentInstance=VegetableCalendarFragment.newInstance(vegetableCalendars);
        FragmentManager vegetableCalendarFragmentManager=getSupportFragmentManager();
        FragmentTransaction vegetableCalendarFragmentTransaction=vegetableCalendarFragmentManager.beginTransaction();
        vegetableCalendarFragmentTransaction.replace(R.id.content_main,vegetableCalendarFragmentInstance,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();
    }

    public void launchVegetableCalendarFragment(List<VegetableCalendar> vegetableCalendars,Menu menu){
        MenuItem renewBtn = (MenuItem) menu.findItem(R.id.app_bar_renew_search);
        renewBtn.setIcon(R.drawable.ic_autorenew_black_24dp);
        VegetableCalendarFragment vegetableCalendarFragmentInstance=VegetableCalendarFragment.newInstance(vegetableCalendars);
        FragmentManager vegetableCalendarFragmentManager=getSupportFragmentManager();
        FragmentTransaction vegetableCalendarFragmentTransaction=vegetableCalendarFragmentManager.beginTransaction();
        vegetableCalendarFragmentTransaction.replace(R.id.content_main,vegetableCalendarFragmentInstance,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();
    }

    public void launchMyVegetableGardenFragment(List<VegetableCalendar> vegetableCalendars,Menu menu){
        MenuItem renewBtn = (MenuItem) menu.findItem(R.id.app_bar_renew_search);
        renewBtn.setIcon(R.drawable.ic_calendar_icon);
        MyVegetableGardenFragment vegetableCalendarFragmentInstance=MyVegetableGardenFragment.newInstance(vegetableCalendars);
        FragmentManager myVegetableGardenFragmentManager=getSupportFragmentManager();
        FragmentTransaction myVegetableGardenFragmentTransaction=myVegetableGardenFragmentManager.beginTransaction();
        myVegetableGardenFragmentTransaction.replace(R.id.content_main,vegetableCalendarFragmentInstance,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();
    }

    public void launchMyPreferences(){
        UserInformationsFragment userInformationsFragmentInstance = UserInformationsFragment.newInstance();
        FragmentManager userInformationsManager=getSupportFragmentManager();
        FragmentTransaction userInformationsFragmentTransaction=userInformationsManager.beginTransaction();
        userInformationsFragmentTransaction.replace(R.id.content_main,userInformationsFragmentInstance,"vegetable_calendar_fragment_tag").addToBackStack(null).commit();

    }


}
