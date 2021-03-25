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

import java.io.IOException;
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
    private double userLongitude = 0.0;
    private double userLatitude = 0.0;
    private Spinner spinnerChooseIsoCountry;
    private GoogleMap googleMap;

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


        Button localiseButton = v.findViewById(R.id.localise);
        localiseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    geolocalisation();
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
        LocationManager locationManager = (LocationManager) ((MainActivity) getActivity()).getSystemService(Context.LOCATION_SERVICE);
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
        LocationManager locationManager = (LocationManager) ((MainActivity) getActivity()).getSystemService(Context.LOCATION_SERVICE);

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
        result1TextView.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        userLatitude = location.getLatitude();
        userLongitude = location.getLongitude();
        Geocoder geocoder = new Geocoder((MainActivity) getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(userLatitude, userLongitude, 1);
        } catch (IOException e) {
            Log.d("geocoder",e.getMessage());
        }
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);
        result2TextView.setText(cityName+" "+stateName+" "+countryName);
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
            for (Address resulteAdress :addresses) {
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
                result4TextView.setText(result4TextView.getText()+" "+cityName+" "+stateName+" "+countryName+" "+addresses.get(0).getLatitude());
            }
            return "OK";
        }
       return "KO";
    }
}
