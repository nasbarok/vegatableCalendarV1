package com.nasbarok.vegatablecalendarv1;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public VegetableCalendarDBHelper vegetableCalendarDB;
    List<VegetableCalendar> vegetableCalendars;
    List<VegetableCalendar> myVegetableGardenCalendars;
    private static final long GAME_LENGTH_MILLISECONDS = 3000;
    //prod ads
    // private static final String AD_UNIT_ID = "ca-app-pub-4063904970721588~3069464357";
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";

    private static final String TAG = "MainActivity";

    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDb();

        //for testings
        List<String> testDeviceIds = Arrays.asList("33BE2250B43518CCDA7DE426D04EE231");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);


        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        loadAd();
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

    public void loadAd() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd minterstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                interstitialAd = minterstitialAd;
                Log.i(TAG, "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                interstitialAd = null;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            loadAd();
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }
}
