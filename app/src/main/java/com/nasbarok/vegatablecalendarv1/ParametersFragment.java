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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.UserInformations;
import com.nasbarok.vegatablecalendarv1.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


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
    private TextView result1TextView = null;
    private TextView result2TextView = null;
    private TextView result3TextView = null;
    private TextView result4TextView = null;
    private double userLongitudeGeo = 0.0;
    private double userLatitudeGeo = 0.0;
    private double userLongitudeCity = 0.0;
    private double userLatitudeCity = 0.0;
    private Spinner spinnerChooseIsoCountry;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private LinearLayout linearLayoutStartLocation;
    private LinearLayout linearLayoutGenerationCalendar;
    private Button backToLocationButton;
    private Button findClassificationButton;
    private String currentClassification;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_parameters, container, false);

        vegetableCalendarDB = new VegetableCalendarDBHelper(getContext());
        userInformations = vegetableCalendarDB.getUserInformations();

        final EditText inputCity = (EditText) v.findViewById(R.id.input_city);
        final EditText inputCountry = (EditText) v.findViewById(R.id.input_country);
        Button valideCityButton = v.findViewById(R.id.valide_city);
        backToLocationButton = v.findViewById(R.id.back_to_loc);
        linearLayoutStartLocation = v.findViewById(R.id.find_location_form);
        linearLayoutGenerationCalendar = v.findViewById(R.id.form_generate_calendar);
        findClassificationButton = v.findViewById(R.id.find_cls);

        linearLayoutGenerationCalendar.setVisibility(View.GONE);

        findClassificationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    loadingForClassification();
                } catch (IOException e) {
                    Log.d("findClassification",e.getMessage());e.printStackTrace();
                }
            }
        });

        backToLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                linearLayoutGenerationCalendar.setVisibility(View.GONE);
                linearLayoutStartLocation.setVisibility(View.VISIBLE);
            }
        });

        result1TextView = v.findViewById(R.id.result1);
        result2TextView = v.findViewById(R.id.result2);
        result3TextView = v.findViewById(R.id.result3);
        result4TextView = v.findViewById(R.id.result4);

        ;
/*        spinnerChooseIsoCountry =(Spinner)  v.findViewById(R.id.iso_country_spinner);
        List<Locale> locals = Arrays.asList(Locale.getAvailableLocales());
        List<String> listIsoCountry = new ArrayList<String>();
        for(Locale loc : locals){
            listIsoCountry.add(loc.getDisplayLanguage()+" "+loc.getDisplayName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>((MainActivity) getActivity(),android.R.layout.simple_spinner_item,listIsoCountry);
        spinnerChooseIsoCountry.setAdapter(spinnerAdapter);
        spinnerChooseIsoCountry.setSelected(false);
        spinnerChooseIsoCountry.setSelection(0,true);*/

        //prepar geoloc
        locationManager = (LocationManager) ((MainActivity) getActivity()).getSystemService(Context.LOCATION_SERVICE);

        Button localiseButton = v.findViewById(R.id.localise);
        localiseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    geolocalisation();
                    linearLayoutStartLocation.setVisibility(View.GONE);
                    linearLayoutGenerationCalendar.setVisibility(View.VISIBLE);
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

                //stop localisation if worked
                //locationManager.removeUpdates(this);
                // aller chercher une ville existante google? et sa localisation
                UserInformations userInformations = vegetableCalendarDB.getUserInformations();
                userInformations.setCity(inputTextCity);
                vegetableCalendarDB.saveUserInformations(userInformations);

                if (inputTextCountry==null||inputTextCountry.equals("")||inputTextCity==null||inputTextCity.equals("")){
                    Toast toast = Toast.makeText(getContext(), "Entrez une ville et selectionnez votre pays", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
                try {
                    locationFinder(inputTextCity,inputTextCountry);
                } catch (IOException e) {
                    Log.d("createView",e.getMessage());
                }
                String result = testLocation(inputTextCity);

                Toast toast = Toast.makeText(getContext(), " " + inputTextCity + " saved.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
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

        if ((ContextCompat.checkSelfPermission(((MainActivity) getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(((MainActivity) getActivity()), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)) {
/*            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);*/
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            ActivityCompat.requestPermissions(((MainActivity) getActivity()), new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    TAG_CODE_PERMISSION_LOCATION);

            Toast.makeText(((MainActivity) getActivity()), R.string.common_signin_button_text, Toast.LENGTH_LONG).show();
        }

        return "KO";
    }

    @Override
    public void onLocationChanged(Location location) {

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
        result4TextView.setText(adresse);
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
        result4TextView.setText(city+" "+country);
        Geocoder geocoder = new Geocoder((MainActivity) getActivity(), Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocationName(city+" "+country,4);
        result4TextView.setText("");
        if(addresses!=null&&addresses.size()>0){
            userLatitudeCity =addresses.get(0).getLatitude();
            userLongitudeCity = addresses.get(0).getLongitude();
            linearLayoutStartLocation.setVisibility(View.GONE);
            linearLayoutGenerationCalendar.setVisibility(View.VISIBLE);
            result3TextView.setText("Latitude:" + userLatitudeCity + ", Longitude:" +userLongitudeCity);
            result4TextView.setText(addresses.get(0).getAddressLine(0)+" "+ addresses.get(0).getPostalCode());
            return "OK";
        }
       return "KO";
    }

    @Override
    public void onPause() {
        locationManager.removeUpdates(this);
        super.onPause();
    }


    public void loadingForClassification() throws IOException {

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
                break;
            }

            //latitude -89.75 -> 83.75
            //longitude -179 -> 179.75
            Log.d("load",currentLat+" "+currentLong+" "+marginLat+" "+marginLong );
        }
        currentClassification = currentCls;
        result1TextView.setText(currentClassification+" "+currentLat+" , "+currentLong);
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
