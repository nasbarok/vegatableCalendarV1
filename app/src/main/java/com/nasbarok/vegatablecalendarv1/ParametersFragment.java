package com.nasbarok.vegatablecalendarv1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsSatellite;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.Classification;
import com.nasbarok.vegatablecalendarv1.model.UserInformations;
import com.nasbarok.vegatablecalendarv1.utils.SunAndMoon;
import com.nasbarok.vegatablecalendarv1.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParametersFragment#newInstance} factory method to
 * create an instance of this fragment.  userInfoLayout
 */
public class ParametersFragment extends Fragment implements LocationListener {

    private static final int TAG_CODE_PERMISSION_LOCATION = 1;
    private VegetableCalendarDBHelper vegetableCalendarDB;
    private Utils utils;
    private UserInformations userInformations;
    private String inputTextCity;
    private String inputTextCountry;
    private GpsSatellite gps;
    private TextView step2AdresseFound1 = null;
    private TextView step2AdresseFound2 = null;
    private TextView step3SelectedClimatName = null;
    private TextView step3SelectedClimatDesc = null;
    private TextView resultMoonSunTest1 = null;
    private TextView resultMoonSunTest2 = null;
    private TextView resultMoonSunTest3 = null;
    private TextView resultMoonSunTest4 = null;
    private double userLongitudeGeo = 0.0;
    private double userLatitudeGeo = 0.0;
    private double userLongitudeCity = 0.0;
    private double userLatitudeCity = 0.0;
    private Spinner spinnerChooseIsoCountry;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private LinearLayout linearLayoutStep1;
    private LinearLayout linearLayoutStep2;
    private LinearLayout linearLayoutStep3;
    private ImageView step2returnStep1Button;
    private Button step2FindClassificationButton;
    private ImageView step3return3tep1Button;
    private Button step3goHomeButton;
    private String currentClassification;
    private Classification currentClassificationKoppen;
    private FusedLocationProviderClient mFusedLocationClient;

    public ParametersFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserInformationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParametersFragment newInstance() {
        ParametersFragment fragment = new ParametersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(((MainActivity) getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_parameters, container, false);

        //init comoponents step 1
        final EditText inputCity = (EditText) v.findViewById(R.id.step1_city_input);
        final EditText inputCountry = (EditText) v.findViewById(R.id.step1_country_input);
        Button valideCityButton = v.findViewById(R.id.step1_valide_city);
        ImageView localiseButton = v.findViewById(R.id.step1_launch_localise);
        linearLayoutStep1 = v.findViewById(R.id.step1_form);

        //init comoponents step 2
        linearLayoutStep2 = v.findViewById(R.id.step2_form);
        step2returnStep1Button = v.findViewById(R.id.step2_return_step1_btn);
        step2FindClassificationButton = v.findViewById(R.id.step2_loading_koppen_btn);
        step2AdresseFound1 = v.findViewById(R.id.step2_adresse_found1);
        step2AdresseFound2 = v.findViewById(R.id.step2_adresse_found2);

        //init comoponents step 3
        linearLayoutStep3 = v.findViewById(R.id.step3_form);
        step3SelectedClimatName = v.findViewById(R.id.step3_selected_climat_name);
        step3SelectedClimatDesc = v.findViewById(R.id.step3_selected_climat_desc);
        step3return3tep1Button = v.findViewById(R.id.step3_return_step2_btn);
        step3goHomeButton = v.findViewById(R.id.step3_launch_home_btn);
        resultMoonSunTest1 = v.findViewById(R.id.resultMoonSunTest1);
        resultMoonSunTest2 = v.findViewById(R.id.resultMoonSunTest2);
        resultMoonSunTest3 = v.findViewById(R.id.resultMoonSunTest3);
        resultMoonSunTest4 = v.findViewById(R.id.resultMoonSunTest4);
        //init Visible View
        linearLayoutStep2.setVisibility(View.GONE);
        linearLayoutStep3.setVisibility(View.GONE);

        //Step 1
            //prepar geoloc
        locationManager = (LocationManager) ((MainActivity) getActivity()).getSystemService(Context.LOCATION_SERVICE);
        localiseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String status = "KO";
                try {
                    status = geolocalisation();
                    if("OK".equals(status)){
                        linearLayoutStep1.setVisibility(View.GONE);
                        linearLayoutStep2.setVisibility(View.VISIBLE);
                    }
                } catch (IOException e) {
                    Log.d("createView",e.getMessage());
                }
            }
        });
        valideCityButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                inputTextCity = inputCity.getText().toString();
                inputTextCountry = inputCountry.getText().toString();

                String status = "OK";
                if (inputTextCountry==null||inputTextCountry.equals("")||inputTextCity==null||inputTextCity.equals("")){
                    status = "KO";
                }else{
                    try {
                        status = locationFinder(inputTextCity,inputTextCountry);
                    } catch (IOException e) {
                        //grpc failed low connection? vpn?
                        status = "KO";
                        Log.d("createView",e.getMessage());
                    }
                }
                if("KO".equals(status)){
                    Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.step1_validate_error), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
                if("OK".equals(status)){
                    linearLayoutStep1.setVisibility(View.GONE);
                    linearLayoutStep2.setVisibility(View.VISIBLE);
                }
            }
        });
        //Step 2
        step2FindClassificationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String cls = loadingForClassification();
                    if(!"".equals(cls)){

                        currentClassificationKoppen = vegetableCalendarDB.getClassificationByName(cls);
                        if(currentClassificationKoppen!=null){
                            step3SelectedClimatName.setText(currentClassificationKoppen.getName() +" - "+currentClassificationKoppen.getNameLong());
                            step3SelectedClimatDesc.setText(currentClassificationKoppen.getDesc());
                            linearLayoutStep2.setVisibility(View.GONE);
                            linearLayoutStep3.setVisibility(View.VISIBLE);
                        }

                    }

                } catch (IOException e) {
                    Log.d("findClassification",e.getMessage());e.printStackTrace();
                }
            }
        });

        step2returnStep1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                linearLayoutStep2.setVisibility(View.GONE);
                linearLayoutStep1.setVisibility(View.VISIBLE);
            }
        });

        //Step 3


        vegetableCalendarDB = new VegetableCalendarDBHelper(getContext());
        userInformations = vegetableCalendarDB.getUserInformations();

        step3return3tep1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                linearLayoutStep3.setVisibility(View.GONE);
                linearLayoutStep2.setVisibility(View.VISIBLE);
            }
        });

        step3goHomeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                //todo enregistrer traces de choix
                //todo charger le calendrier specifique
                //fair 2 acces db pour ne pas réecrire les climats a chaque fois (ou un booleaen ?)
                ZonedDateTime currentDate = ZonedDateTime.now();
                SunAndMoon sunAndMoon = new SunAndMoon(userLatitudeGeo,userLongitudeGeo,currentDate);
                resultMoonSunTest1.setText("");
                resultMoonSunTest2.setText("");
                resultMoonSunTest3.setText("");




                //((MainActivity) getActivity()).launchHomeFragment();
            }
        });


        return v;
    }

    public String testLocation(String City) {
        Location location = new Location(City);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            return latitude + " " + longitude;
        } else {
            return "KO";
        }

    }

    ;

    public String geolocalisation() throws IOException {
        String status = "KO";

        // check permission 6 OS devices
        if ((ContextCompat.checkSelfPermission(((MainActivity) getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(((MainActivity) getActivity()), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)) {
            // already permission granted
            status = "OK";
        } else {
            // reuqest for permission
            ActivityCompat.requestPermissions(((MainActivity) getActivity()), new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    TAG_CODE_PERMISSION_LOCATION);

            Toast.makeText(((MainActivity) getActivity()), R.string.common_signin_button_text, Toast.LENGTH_LONG).show();
            status = "KO";
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(((MainActivity) getActivity()), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    userLatitudeGeo = location.getLatitude();
                    userLongitudeGeo = location.getLongitude();
                    Geocoder geocoder = new Geocoder((MainActivity) getActivity(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(userLatitudeGeo, userLongitudeGeo, 1);
                    } catch (IOException e) {
                        Log.d("geocoder",e.getMessage());
                    }
                    String adresse = addresses.get(0).getAddressLine(0);
                    step2AdresseFound1.setText(adresse);
                    step2AdresseFound2.setText(userLatitudeGeo + " , " +userLongitudeGeo);
                }
            }
        });
        return status;
    }

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(((MainActivity) getActivity()), R.string.common_signin_button_text, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }*/

    @Override
    public void onLocationChanged(Location location) {
/*
        userLatitudeGeo = location.getLatitude();
        userLongitudeGeo = location.getLongitude();
        Geocoder geocoder = new Geocoder((MainActivity) getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(userLatitudeGeo, userLongitudeGeo, 1);
        } catch (IOException e) {
            Log.d("geocoder",e.getMessage());
        }
        String adresse = addresses.get(0).getAddressLine(0);
        result3TextView.setText("Latitude:" + userLatitudeGeo + ", Longitude:" +userLongitudeGeo);
        result4TextView.setText(adresse);*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","status");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String locationFinder(String city, String country) throws IOException {
        Geocoder geocoder = new Geocoder((MainActivity) getActivity(), Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocationName(city+" "+country,4);
        if(addresses!=null&&addresses.size()>0){
            userLatitudeCity =addresses.get(0).getLatitude();
            userLongitudeCity = addresses.get(0).getLongitude();
            step2AdresseFound1.setText(addresses.get(0).getAddressLine(0));
            step2AdresseFound2.setText(userLatitudeCity + " , " +userLongitudeCity);
            return "OK";
        }
       return "KO";
    }

    @Override
    public void onPause() {
        locationManager.removeUpdates(this);
        super.onPause();
    }


    public String loadingForClassification() throws IOException {
        String classification = "";
        InputStream inputStream2 = getResources().openRawResource(R.raw.koeppen_geiger_ascii);
        InputStreamReader streamReader = new InputStreamReader(inputStream2);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line;
        String[] values;
        double marginLat = 0.0;
        double marginLong = 0.0;
        double currentLat = 0.0;
        double currentLong = 0.0;
        double userLat = getSelectedLatitude();
        double userLong = getSelectedLongitude();
        String currentCls = "NONE";
        while ((line = bufferedReader.readLine()) != null) {
            values = line.split(",");
            currentLat = Double.valueOf(values[1]);
            currentLong = Double.valueOf(values[2]);
            currentCls = values[3];
            marginLat = userLat - currentLat;
            marginLong = userLong - currentLong;

            if((marginLat>=-1&&marginLat<=1)&&(marginLong>=-1&&marginLong<=1)){
                currentClassification = currentCls;
                bufferedReader.close();
                break;
            }

            //latitude -89.75 -> 83.75
            //longitude -179 -> 179.75
            //Log.d("load",currentLat+" "+currentLong+" "+marginLat+" "+marginLong );
        }
        bufferedReader.close();
        //result1TextView.setText(currentClassification+" "+currentLat+" , "+currentLong);
        return currentClassification;
    }

    public double getSelectedLatitude(){
        if (inputTextCity != null && !"".equals(inputTextCity) && userLatitudeCity!=0.0) {
             return Double.valueOf(userLatitudeCity);
        }else{
            return Double.valueOf(userLatitudeGeo);
        }
    }

    public double getSelectedLongitude(){
        if (inputTextCity != null && !"".equals(inputTextCity) && userLongitudeCity!=0.0) {
            return Double.valueOf(userLongitudeCity);
        }else{
            return Double.valueOf(userLongitudeGeo);
        }
    }
}
