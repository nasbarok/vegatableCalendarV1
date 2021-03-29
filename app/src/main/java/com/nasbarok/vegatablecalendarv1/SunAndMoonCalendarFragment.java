package com.nasbarok.vegatablecalendarv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.DayStats;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SunAndMoonCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SunAndMoonCalendarFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SUN_AND_MOON_365_LIST = "sun_and_moon_list";
    private static String seasonResult;

    private List<DayStats> dayStats;
    private TableLayout mTableLayout;
    private AlertDialog.Builder builder;
    private TextView seasonResultTv = null;

    public SunAndMoonCalendarFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dayStatsList list of entity SunAndMoonCalendarFragment.
     * @return A new instance of fragment VegetableCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SunAndMoonCalendarFragment newInstance(List<DayStats> dayStatsList,String seasons) {
        SunAndMoonCalendarFragment fragment = new SunAndMoonCalendarFragment();
        Bundle args = new Bundle();
        args.putSerializable(SUN_AND_MOON_365_LIST, (Serializable) dayStatsList);
        seasonResult = seasons;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayStats = (List<DayStats>) getArguments().getSerializable(SUN_AND_MOON_365_LIST);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_sun_and_moon_calendar, container, false);

        //Table

        seasonResultTv = v.findViewById(R.id.season_result);
        seasonResultTv.setText(seasonResult);
       /* mProgressBar = new ProgressDialog(v.getContext());*/
        mTableLayout = (TableLayout) v.findViewById(R.id.tableSunAndMoon365);
        mTableLayout.setStretchAllColumns(true);

        builder = new AlertDialog.Builder(v.getContext());
/*
        startLoadData();*/
        loadData();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu mOptionsMenu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, mOptionsMenu);

        MenuItem homeBtn = (MenuItem) mOptionsMenu.findItem(R.id.app_bar_home);
        homeBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((MainActivity) getActivity()).launchHomeFragment();
                return false;
            }
        });

        MenuItem myVegetableGardenBtn = (MenuItem) mOptionsMenu.findItem(R.id.app_bar_my_vegetable_garden);
        myVegetableGardenBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((MainActivity) getActivity()).launchMyVegetableGardenFragment();
                return false;
            }
        });

        MenuItem myPreferences = (MenuItem) mOptionsMenu.findItem(R.id.app_bar_myparapeters);
        myPreferences.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((MainActivity) getActivity()).launchMyPreferences();
                return false;
            }
        });
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Moon and sun analys");
        super.onCreateOptionsMenu(mOptionsMenu, inflater);
    }

/*    public void startLoadData() {
        mProgressBar.setCancelable(false);
        mProgressBar.setMessage(getResources().getString(R.string.loading_msg));
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
        new LoadDataTask().execute(0);
    }*/
    public void loadData() {
        //List<VegetableCalendar> vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();
        // getSupportActionBar().setTitle(getResources().getString(R.string.app_name) + "( " + String.valueOf(rows) + " )");
        int leftRowMargin = 0;
        int topRowMargin = 0;
        int rightRowMargin = 0;
        int bottomRowMargin = 0;
        int textSize = 0, smallTextSize = 0, mediumTextSize = 0;
        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);
        mediumTextSize = (int)
                getResources().getDimension(R.dimen.font_size_medium);

        //get dimensions in pixels
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthTotalDisplay = size.x - 40;
        int widthColumn = widthTotalDisplay/14;
        int widthColumnVegetables = widthColumn + widthColumn/2;

        DayStats[] data = new DayStats[dayStats.size()];
        data = dayStats.toArray(data);

        int rows = data.length;
        TextView textSpacer = null;
        mTableLayout.removeAllViews();
        // -1 means heading row
        for (int i = -1; i < rows; i++) {
            DayStats row = null;
            if (i > -1)
                row = data[i];
            else {
                textSpacer = new TextView(getContext());
                textSpacer.setText("");
            }
            // data columns
            //COLUMN1_ID_DAY
            final TextView tvIdDays = new TextView(getContext());
            tvIdDays.setLayoutParams(new
                    TableRow.LayoutParams(widthColumnVegetables,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvIdDays.setGravity(Gravity.LEFT);
            tvIdDays.setPadding(5, 15, 0, 15);


            if (i == -1) {
                tvIdDays.setText("id");
                tvIdDays.setBackgroundColor(getResources().getColor(R.color.table_color_vegetable_head));
            } else {
                tvIdDays.setBackgroundColor(getResources().getColor(R.color.table_color_vegetable_column));
                tvIdDays.setText(Integer.toString(row.getDayNumber()));
                setOnClickNumberId(tvIdDays,row);
            }
            //COLUMN2_currentDay
            LinearLayout layCurrentDay = new LinearLayout(getContext());
            layCurrentDay.setOrientation(LinearLayout.VERTICAL);
            layCurrentDay.setGravity(Gravity.LEFT);
            layCurrentDay.setPadding(0, 0, 0, 10);
            layCurrentDay.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layCurrentDay.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            final TextView tvCurrentDay = new TextView(getContext());
            tvCurrentDay.setGravity(Gravity.LEFT);
            tvCurrentDay.setPadding(5, 15, 0, 15);

            if (i == -1) {
                tvCurrentDay.setText("CurrentDay");
                tvCurrentDay.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
                tvCurrentDay.setSingleLine(true);
                layCurrentDay.addView(tvCurrentDay);
            } else {
                layCurrentDay = fillDateCalendar(row.getCurrentDay(),layCurrentDay);
            }

            //COLUMN3_sunRise
            LinearLayout laySunRise = new LinearLayout(getContext());
            laySunRise.setOrientation(LinearLayout.VERTICAL);
            laySunRise.setGravity(Gravity.LEFT);
            laySunRise.setPadding(0, 0, 0, 10);
            laySunRise.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            laySunRise.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));

            final TextView tvSunRise = new TextView(getContext());
            tvSunRise.setGravity(Gravity.LEFT);
            tvSunRise.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));
            tvSunRise.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvSunRise.setText("SunRise");
                tvSunRise.setSingleLine(true);
                laySunRise.addView(tvSunRise);
            } else {
                laySunRise = fillDateZonedDateTime(row.getSunRise(),laySunRise);
            }

            //COLUMN4_sunSet
            LinearLayout laySunSet = new LinearLayout(getContext());
            laySunSet.setOrientation(LinearLayout.VERTICAL);
            laySunSet.setGravity(Gravity.LEFT);
            laySunSet.setPadding(0, 0, 0, 10);
            laySunSet.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            laySunSet.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvSunSet = new TextView(getContext());
            tvSunSet.setGravity(Gravity.LEFT);
            tvSunSet.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvSunSet.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvSunSet.setText("SunSet");
                tvSunSet.setSingleLine(true);
                laySunSet.addView(tvSunSet);
            } else {
                laySunSet = fillDateZonedDateTime(row.getSunSet(),laySunSet);
            }

            //COLUMN5_sunNadir
            LinearLayout laySunNadir = new LinearLayout(getContext());
            laySunNadir.setOrientation(LinearLayout.VERTICAL);
            laySunNadir.setGravity(Gravity.LEFT);
            laySunNadir.setPadding(0, 0, 0, 10);
            laySunNadir.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            laySunNadir.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvSunNadir = new TextView(getContext());
            tvSunNadir.setGravity(Gravity.LEFT);
            tvSunNadir.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvSunNadir.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvSunNadir.setText("SunNadir");
                tvSunNadir.setSingleLine(true);
                laySunNadir.addView(tvSunNadir);
            } else {
                laySunNadir = fillDateZonedDateTime(row.getSunNadir(),laySunNadir);
            }

            //COLUMN6_sunNoon
            LinearLayout laySunNoon = new LinearLayout(getContext());
            laySunNoon.setOrientation(LinearLayout.VERTICAL);
            laySunNoon.setGravity(Gravity.LEFT);
            laySunNoon.setPadding(0, 0, 0, 10);
            laySunNoon.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            laySunNoon.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvSunNoon = new TextView(getContext());
            tvSunNoon.setGravity(Gravity.LEFT);
            tvSunNoon.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvSunNoon.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvSunNoon.setText("SunNoon");
                tvSunNoon.setSingleLine(true);
                laySunNoon.addView(tvSunNoon);
            } else {
                laySunNoon = fillDateZonedDateTime(row.getSunNoon(),laySunNoon);
            }

            //COLUMN7_sunAltitude
            LinearLayout laySunAltitude = new LinearLayout(getContext());
            laySunAltitude.setOrientation(LinearLayout.VERTICAL);
            laySunAltitude.setGravity(Gravity.LEFT);
            laySunAltitude.setPadding(0, 0, 0, 10);
            laySunAltitude.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            laySunAltitude.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvSunAltitude = new TextView(getContext());
            tvSunAltitude.setGravity(Gravity.LEFT);
            tvSunAltitude.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvSunAltitude.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvSunAltitude.setText("SunAltitude");
                tvSunAltitude.setSingleLine(true);
            } else {
                laySunAltitude = fillDoubleValue(row.getSunAltitude(),laySunAltitude);
            }
            laySunAltitude.addView(tvSunAltitude);

            //COLUMN8_sunAzimuth
            LinearLayout laySunAzimuth = new LinearLayout(getContext());
            laySunAzimuth.setOrientation(LinearLayout.VERTICAL);
            laySunAzimuth.setGravity(Gravity.LEFT);
            laySunAzimuth.setPadding(0, 0, 0, 10);
            laySunAzimuth.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            laySunAzimuth.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvSunAzimuth = new TextView(getContext());
            tvSunAzimuth.setGravity(Gravity.LEFT);
            tvSunAzimuth.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvSunAzimuth.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvSunAzimuth.setText("SunAzimuth");
            } else {
                laySunAzimuth = fillDoubleValue(row.getSunAzimuth(),laySunAzimuth);
            }
            tvSunAzimuth.setSingleLine(true);
            laySunAzimuth.addView(tvSunAzimuth);

            //COLUMN9_sunDistance
            LinearLayout laySunDistance = new LinearLayout(getContext());
            laySunDistance.setOrientation(LinearLayout.VERTICAL);
            laySunDistance.setGravity(Gravity.LEFT);
            laySunDistance.setPadding(0, 0, 0, 10);
            laySunDistance.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            laySunDistance.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvSunDistance = new TextView(getContext());
            tvSunDistance.setGravity(Gravity.LEFT);
            tvSunDistance.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvSunDistance.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvSunDistance.setText("SunDistance");
            } else {
                laySunDistance = fillDoubleValue(row.getSunDistance(),laySunDistance);
                tvSunDistance.setText(Double.toString(row.getSunDistance()));;
            }
            laySunDistance.addView(tvSunDistance);

            //COLUMN10_sunParallacticAngle
            LinearLayout laySunParallacticAngle = new LinearLayout(getContext());
            laySunParallacticAngle.setOrientation(LinearLayout.VERTICAL);
            laySunParallacticAngle.setGravity(Gravity.LEFT);
            laySunParallacticAngle.setPadding(0, 0, 0, 10);
            laySunParallacticAngle.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            laySunParallacticAngle.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvSunParallacticAngle = new TextView(getContext());
            tvSunParallacticAngle.setGravity(Gravity.LEFT);
            tvSunParallacticAngle.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvSunParallacticAngle.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvSunParallacticAngle.setText("SunParallacticAngle");
            } else {
                laySunParallacticAngle = fillDoubleValue(row.getSunParallacticAngle(),laySunParallacticAngle);
            }
            laySunParallacticAngle.addView(tvSunParallacticAngle);

            //COLUMN11_moonAltitude
            LinearLayout layMoonAltitude = new LinearLayout(getContext());
            layMoonAltitude.setOrientation(LinearLayout.VERTICAL);
            layMoonAltitude.setGravity(Gravity.LEFT);
            layMoonAltitude.setPadding(0, 0, 0, 10);
            layMoonAltitude.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonAltitude.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonAltitude = new TextView(getContext());
            tvMoonAltitude.setGravity(Gravity.LEFT);
            tvMoonAltitude.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonAltitude.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvMoonAltitude.setText("MoonAltitude");
            } else {
                layMoonAltitude = fillDoubleValue(row.getMoonAltitude(),layMoonAltitude);
            }
            tvMoonAltitude.setSingleLine(true);
            layMoonAltitude.addView(tvMoonAltitude);

            //COLUMN12_moonAzimuth
            LinearLayout layMoonAzimuth = new LinearLayout(getContext());
            layMoonAzimuth.setOrientation(LinearLayout.VERTICAL);
            layMoonAzimuth.setGravity(Gravity.LEFT);
            layMoonAzimuth.setPadding(0, 0, 0, 10);
            layMoonAzimuth.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonAzimuth.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonAzimuth = new TextView(getContext());
            tvMoonAzimuth.setGravity(Gravity.LEFT);
            tvMoonAzimuth.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonAzimuth.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvMoonAzimuth.setText("MoonAzimuth");
            } else {
                layMoonAzimuth = fillDoubleValue(row.getMoonAzimuth(),layMoonAzimuth);
            }
            tvMoonAzimuth.setSingleLine(true);
            layMoonAzimuth.addView(tvMoonAzimuth);

            //COLUMN13_moonDistance
            LinearLayout layMoonDistance = new LinearLayout(getContext());
            layMoonDistance.setOrientation(LinearLayout.VERTICAL);
            layMoonDistance.setGravity(Gravity.LEFT);
            layMoonDistance.setPadding(0, 0, 0, 10);
            layMoonDistance.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonDistance.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonDistance = new TextView(getContext());
            tvMoonDistance.setGravity(Gravity.LEFT);
            tvMoonDistance.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonDistance.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvMoonDistance.setText("MoonDistance");
            } else {
                layMoonDistance = fillDoubleValue(row.getMoonDistance(),layMoonDistance);
            }
            tvMoonDistance.setSingleLine(true);
            layMoonDistance.addView(tvMoonDistance);

            //COLUMN14_moonParallacticAngle
            LinearLayout layMoonParallacticAngle = new LinearLayout(getContext());
            layMoonParallacticAngle.setOrientation(LinearLayout.VERTICAL);
            layMoonParallacticAngle.setGravity(Gravity.LEFT);
            layMoonParallacticAngle.setPadding(0, 0, 0, 10);
            layMoonParallacticAngle.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonParallacticAngle.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonParallacticAngle = new TextView(getContext());
            tvMoonParallacticAngle.setGravity(Gravity.LEFT);
            tvMoonParallacticAngle.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonParallacticAngle.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvMoonParallacticAngle.setText("MoonParallacticAngle");
            } else {
                layMoonParallacticAngle = fillDoubleValue(row.getMoonParallacticAngle(),layMoonParallacticAngle);
            }
            tvMoonParallacticAngle.setSingleLine(true);
            layMoonParallacticAngle.addView(tvMoonParallacticAngle);

            //COLUMN15_moonIlluminationAngle
            LinearLayout layMoonIlluminationAngle = new LinearLayout(getContext());
            layMoonIlluminationAngle.setOrientation(LinearLayout.VERTICAL);
            layMoonIlluminationAngle.setGravity(Gravity.LEFT);
            layMoonIlluminationAngle.setPadding(0, 0, 0, 10);
            layMoonIlluminationAngle.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonIlluminationAngle.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonIlluminationAngle = new TextView(getContext());
            tvMoonIlluminationAngle.setGravity(Gravity.LEFT);
            tvMoonIlluminationAngle.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonIlluminationAngle.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvMoonIlluminationAngle.setText("MoonIlluminationAngle");
            } else {
                layMoonIlluminationAngle = fillDoubleValue(row.getMoonIlluminationAngle(),layMoonIlluminationAngle);
            }
            tvMoonIlluminationAngle.setSingleLine(true);
            layMoonIlluminationAngle.addView(tvMoonIlluminationAngle);

            //COLUMN16_moonIlluminationFraction
            LinearLayout layMoonIlluminationFraction = new LinearLayout(getContext());
            layMoonIlluminationFraction.setOrientation(LinearLayout.VERTICAL);
            layMoonIlluminationFraction.setGravity(Gravity.LEFT);
            layMoonIlluminationFraction.setPadding(0, 0, 0, 10);
            layMoonIlluminationFraction.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonIlluminationFraction.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonIlluminationFraction = new TextView(getContext());
            tvMoonIlluminationFraction.setGravity(Gravity.LEFT);
            tvMoonIlluminationFraction.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonIlluminationFraction.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvMoonIlluminationFraction.setText("MoonIlluminationAngle");
            } else {
                layMoonIlluminationFraction = fillDoubleValue(row.getMoonIlluminationFraction(),layMoonIlluminationFraction);
            }
            tvMoonIlluminationFraction.setSingleLine(true);
            layMoonIlluminationFraction.addView(tvMoonIlluminationFraction);

            //COLUMN17_moonIlluminationPhase
            LinearLayout layMoonIlluminationPhase = new LinearLayout(getContext());
            layMoonIlluminationPhase.setOrientation(LinearLayout.VERTICAL);
            layMoonIlluminationPhase.setGravity(Gravity.LEFT);
            layMoonIlluminationPhase.setPadding(0, 0, 0, 10);
            layMoonIlluminationPhase.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonIlluminationPhase.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonIlluminationPhase = new TextView(getContext());
            tvMoonIlluminationPhase.setGravity(Gravity.LEFT);
            tvMoonIlluminationPhase.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonIlluminationPhase.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvMoonIlluminationPhase.setText("MoonIlluminationPhase");
            } else {
                layMoonIlluminationPhase = fillDoubleValue(row.getMoonIlluminationPhase(),layMoonIlluminationPhase);
            }
            layMoonIlluminationPhase.addView(tvMoonIlluminationPhase);

            //COLUMN18_moonPhaseDistance
            LinearLayout layMoonPhaseDistance = new LinearLayout(getContext());
            layMoonPhaseDistance.setOrientation(LinearLayout.VERTICAL);
            layMoonPhaseDistance.setGravity(Gravity.LEFT);
            layMoonPhaseDistance.setPadding(0, 0, 0, 10);
            layMoonPhaseDistance.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonPhaseDistance.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonPhaseDistance = new TextView(getContext());
            tvMoonPhaseDistance.setGravity(Gravity.LEFT);
            tvMoonPhaseDistance.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonPhaseDistance.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvMoonPhaseDistance.setText("MoonPhaseDistance");
            } else {
                layMoonPhaseDistance = fillDoubleValue(row.getMoonPhaseDistance(),layMoonPhaseDistance);
            }
            layMoonPhaseDistance.addView(tvMoonPhaseDistance);

            //COLUMN19_moonPhaseTime
            LinearLayout layMoonPhaseTime = new LinearLayout(getContext());
            layMoonPhaseTime.setOrientation(LinearLayout.VERTICAL);
            layMoonPhaseTime.setGravity(Gravity.LEFT);
            layMoonPhaseTime.setPadding(0, 0, 0, 10);
            layMoonPhaseTime.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonPhaseTime.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvyMoonPhaseTime = new TextView(getContext());
            tvyMoonPhaseTime.setGravity(Gravity.LEFT);
            tvyMoonPhaseTime.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvyMoonPhaseTime.setPadding(5, 15, 0, 15);
            tvyMoonPhaseTime.setSingleLine(true);
            if (i == -1) {
                tvyMoonPhaseTime.setText("MoonPhaseDistance");
                layMoonPhaseTime.addView(tvyMoonPhaseTime);
            } else {
                layMoonPhaseTime = fillDateZonedDateTime(row.getMoonPhaseTime(),layMoonPhaseTime);
            }


            //COLUMN20_moonPhaseIsMicroMoon
            LinearLayout layMoonPhaseIsMicroMoon = new LinearLayout(getContext());
            layMoonPhaseIsMicroMoon.setOrientation(LinearLayout.VERTICAL);
            layMoonPhaseIsMicroMoon.setGravity(Gravity.LEFT);
            layMoonPhaseIsMicroMoon.setPadding(0, 0, 0, 10);
            layMoonPhaseIsMicroMoon.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonPhaseIsMicroMoon.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonPhaseIsMicroMoon = new TextView(getContext());
            tvMoonPhaseIsMicroMoon.setGravity(Gravity.LEFT);
            tvMoonPhaseIsMicroMoon.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonPhaseIsMicroMoon.setPadding(5, 15, 0, 15);
            tvMoonPhaseIsMicroMoon.setSingleLine(true);
            if (i == -1) {
                tvMoonPhaseIsMicroMoon.setText("IsMicroMoon");
            } else {
                tvMoonPhaseIsMicroMoon.setText(getStringForBoolean(row.isMoonPhaseIsMicroMoon()));
            }
            layMoonPhaseIsMicroMoon.addView(tvMoonPhaseIsMicroMoon);


            //COLUMN21_moonPhaseIsSuperMoon
            LinearLayout layMoonPhaseIsSuperMoon = new LinearLayout(getContext());
            layMoonPhaseIsSuperMoon.setOrientation(LinearLayout.VERTICAL);
            layMoonPhaseIsSuperMoon.setGravity(Gravity.LEFT);
            layMoonPhaseIsSuperMoon.setPadding(0, 0, 0, 10);
            layMoonPhaseIsSuperMoon.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMoonPhaseIsSuperMoon.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMoonPhaseIsSuperMoon = new TextView(getContext());
            tvMoonPhaseIsSuperMoon.setGravity(Gravity.LEFT);
            tvMoonPhaseIsSuperMoon.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMoonPhaseIsSuperMoon.setPadding(5, 15, 0, 15);
            tvMoonPhaseIsSuperMoon.setSingleLine(true);
            if (i == -1) {
                tvMoonPhaseIsSuperMoon.setText("MoonPhaseDistance");
            } else {
                tvMoonPhaseIsSuperMoon.setText(getStringForBoolean(row.isMoonPhaseIsMicroMoon()));
            }
            layMoonPhaseIsSuperMoon.addView(tvMoonPhaseIsSuperMoon);
            // add table row
            final TableRow tr = new TableRow(getContext());
            tr.setId(i + 1);
            TableLayout.LayoutParams trParams = new
                    TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin,
                    bottomRowMargin);
            tr.setPadding(0, 0, 0, 0);
            tr.setLayoutParams(trParams);
            tr.addView(tvIdDays);
            tr.addView(layCurrentDay);
            tr.addView(laySunRise);
            tr.addView(laySunSet);
            tr.addView(laySunNadir);
            tr.addView(laySunNoon);
            tr.addView(laySunAltitude);
            tr.addView(laySunAzimuth);
            tr.addView(laySunDistance);
            tr.addView(laySunParallacticAngle);
            tr.addView(layMoonAltitude);
            tr.addView(layMoonAzimuth);
            tr.addView(layMoonDistance);
            tr.addView(layMoonParallacticAngle);
            tr.addView(layMoonIlluminationAngle);
            tr.addView(layMoonIlluminationFraction);
            tr.addView(layMoonIlluminationPhase);
            tr.addView(layMoonPhaseDistance);
            tr.addView(layMoonPhaseTime);
            tr.addView(layMoonPhaseIsMicroMoon);
            tr.addView(layMoonPhaseIsSuperMoon);
            if (i > -1) {
                tr.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        TableRow tr = (TableRow) v;
                    }
                });
            }
            mTableLayout.addView(tr, trParams);
            if (i > -1) {
                // add separator row
                final TableRow trSep = new TableRow(getContext());
                TableLayout.LayoutParams trParamsSep = new
                        TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParamsSep.setMargins(leftRowMargin, topRowMargin,
                        rightRowMargin, bottomRowMargin);
                trSep.setLayoutParams(trParamsSep);
                TextView tvSep = new TextView(getContext());
                TableRow.LayoutParams tvSepLay = new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tvSepLay.span = 13;
                tvSep.setLayoutParams(tvSepLay);
                tvSep.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark2));
                tvSep.setHeight(1);
                trSep.addView(tvSep);
                mTableLayout.addView(trSep, trParamsSep);
            }
        }
    }

    public LinearLayout fillDoubleValue(double doubleValue, LinearLayout linearLayout){
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        final TextView tv = new TextView(getContext());
        tv.setText(" "+Double.toString(doubleValue));
        linearLayout.addView(tv);
        return linearLayout;
    }

    public LinearLayout fillDateZonedDateTime(ZonedDateTime zonedDateTime, LinearLayout linearLayout){
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.RIGHT);
        linearLayout.setPadding(0, 5, 0, 5);
        linearLayout.setWeightSum(1f);
        linearLayout.setLayoutParams(new
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        final TextView tvCalendar = new TextView(getContext());
        tvCalendar.setLayoutParams(new
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,1.0f)
        );
        tvCalendar.setPadding(5, 0, 1, 5);
        tvCalendar.setGravity(Gravity.RIGHT);


        Format f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if(zonedDateTime!=null){
            Date date = Date.from(zonedDateTime.toInstant());
            String formatedCalendar = f.format(date);
            tvCalendar.setText(formatedCalendar);
        }else{
            tvCalendar.setText("none");
        }

        linearLayout.addView(tvCalendar);
        return linearLayout;
    }
    public LinearLayout fillDateCalendar(Date date, LinearLayout linearLayout){
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.RIGHT);
        linearLayout.setPadding(0, 5, 0, 5);
        linearLayout.setWeightSum(1f);
        linearLayout.setLayoutParams(new
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        final TextView tvCalendar = new TextView(getContext());
        tvCalendar.setLayoutParams(new
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,1.0f)
        );
        tvCalendar.setPadding(5, 0, 1, 5);
        tvCalendar.setGravity(Gravity.RIGHT);

        Format f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if(date!=null){
            String formatedCalendar = f.format(date);
            tvCalendar.setText(formatedCalendar);
        }else{
            tvCalendar.setText("none");
        }

        linearLayout.addView(tvCalendar);
        return linearLayout;
    }

    public String getStringForBoolean(boolean bool){
        if(bool){
            return "1";
        }else{
            return "0";
        }
    }

    private void setOnClickNumberId(final TextView view, final DayStats dayStats){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle(dayStats.getDayNumber()+" "+dayStats.getCurrentDay());
                builder.setMessage(Double.toString(dayStats.getMoonAltitude()));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                       /* Toast toast = Toast.makeText(getContext(), " "+ vegetableCalendar.getVegetableCalendarName()+ " "+ getResources().getString(R.string.added_success) , Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();*/
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
