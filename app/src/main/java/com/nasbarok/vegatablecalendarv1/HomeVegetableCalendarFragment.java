package com.nasbarok.vegatablecalendarv1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeVegetableCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeVegetableCalendarFragment extends Fragment {

    private RelativeLayout goToVegetableGardenCalendarLayout;
    private RelativeLayout goToMyVegetableGardenLayout;
    private ImageView addVegetableToMyVegetableGardenButton;
    public VegetableCalendarDBHelper vegetableCalendarDB;
    private ProgressDialog mProgressBar;
    private AlertDialog.Builder builder;


    public HomeVegetableCalendarFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VegetableCalendarFragment.
     */

    public static HomeVegetableCalendarFragment newInstance() {
        HomeVegetableCalendarFragment fragment = new HomeVegetableCalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        //access db
        vegetableCalendarDB = new VegetableCalendarDBHelper(getContext());

        goToVegetableGardenCalendarLayout = v.findViewById(R.id.homeGoToCalendarVegetableGarden);
        goToVegetableGardenCalendarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar = new ProgressDialog(getActivity());
                mProgressBar.setCancelable(false);
                mProgressBar.setMessage(getResources().getString(R.string.loading_msg));
                mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressBar.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            goCalendarPlantation();
                            mProgressBar.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });



        goToMyVegetableGardenLayout = v.findViewById(R.id.homeGoToMyVegetableGarden);
        goToMyVegetableGardenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMyVegetableGarden();
            }
        });

        addVegetableToMyVegetableGardenButton =  v.findViewById(R.id.addVegetableToMyVegetableGardenButton);
        addVegetableToMyVegetableGardenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogAdd();
            }
        });

        //init home dialog
        List<String> listToDoHarvest = new ArrayList<String>();
        List<String> listToDoOutdoorSeeding = new ArrayList<String>();
        List<String> listToDoIndoorSeeding = new ArrayList<String>();
        List<String> listToDoTransplantation = new ArrayList<String>();

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormatMonth = new SimpleDateFormat("MM");
        DateFormat dateFormatDay = new SimpleDateFormat("dd");
        int currentMonth = Integer.valueOf(dateFormatMonth.format(currentTime));
        int currentDay = Integer.valueOf(dateFormatDay.format(currentTime));
        String alfTermination = "AS";
        if(currentDay>=15){
            alfTermination = "AE";
        }

        List<VegetableCalendar> myVegetables = vegetableCalendarDB.fillMyVegetableGarden();

        for(VegetableCalendar currentVegetable :myVegetables){
            switch (currentMonth){
                case 1 :
                    if(currentVegetable.getVegetableCalendarJanuary().equals("T")||currentVegetable.getVegetableCalendarJanuary().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJanuary().equals("IS")||currentVegetable.getVegetableCalendarJanuary().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJanuary().equals("OS")||currentVegetable.getVegetableCalendarJanuary().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJanuary().equals("H")||currentVegetable.getVegetableCalendarJanuary().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 2 :
                    if(currentVegetable.getVegetableCalendarFebruary().equals("T")||currentVegetable.getVegetableCalendarFebruary().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarFebruary().equals("IS")||currentVegetable.getVegetableCalendarFebruary().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarFebruary().equals("OS")||currentVegetable.getVegetableCalendarFebruary().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarFebruary().equals("H")||currentVegetable.getVegetableCalendarFebruary().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 3 :
                    if(currentVegetable.getVegetableCalendarMarch().equals("T")||currentVegetable.getVegetableCalendarMarch().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMarch().equals("IS")||currentVegetable.getVegetableCalendarMarch().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMarch().equals("OS")||currentVegetable.getVegetableCalendarMarch().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMarch().equals("H")||currentVegetable.getVegetableCalendarMarch().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 4 :
                    if(currentVegetable.getVegetableCalendarApril().equals("T")||currentVegetable.getVegetableCalendarApril().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarApril().equals("IS")||currentVegetable.getVegetableCalendarApril().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarApril().equals("OS")||currentVegetable.getVegetableCalendarApril().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarApril().equals("H")||currentVegetable.getVegetableCalendarApril().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 5 :
                    if(currentVegetable.getVegetableCalendarMay().equals("T")||currentVegetable.getVegetableCalendarMay().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMay().equals("IS")||currentVegetable.getVegetableCalendarMay().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMay().equals("OS")||currentVegetable.getVegetableCalendarMay().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMay().equals("H")||currentVegetable.getVegetableCalendarMay().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 6 :
                    if(currentVegetable.getVegetableCalendarJune().equals("T")||currentVegetable.getVegetableCalendarJune().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJune().equals("IS")||currentVegetable.getVegetableCalendarJune().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJune().equals("OS")||currentVegetable.getVegetableCalendarJune().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJune().equals("H")||currentVegetable.getVegetableCalendarJune().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 7 :
                    if(currentVegetable.getVegetableCalendarJuly().equals("T")||currentVegetable.getVegetableCalendarJuly().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJuly().equals("IS")||currentVegetable.getVegetableCalendarJuly().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJuly().equals("OS")||currentVegetable.getVegetableCalendarJuly().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJuly().equals("H")||currentVegetable.getVegetableCalendarJuly().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 8 :
                    if(currentVegetable.getVegetableCalendarAugust().equals("T")||currentVegetable.getVegetableCalendarAugust().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarAugust().equals("IS")||currentVegetable.getVegetableCalendarAugust().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarAugust().equals("OS")||currentVegetable.getVegetableCalendarAugust().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarAugust().equals("H")||currentVegetable.getVegetableCalendarAugust().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 9 :
                    if(currentVegetable.getVegetableCalendarSeptember().equals("T")||currentVegetable.getVegetableCalendarSeptember().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarSeptember().equals("IS")||currentVegetable.getVegetableCalendarSeptember().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarSeptember().equals("OS")||currentVegetable.getVegetableCalendarSeptember().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarSeptember().equals("H")||currentVegetable.getVegetableCalendarSeptember().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 10 :
                    if(currentVegetable.getVegetableCalendarOctober().equals("T")||currentVegetable.getVegetableCalendarOctober().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarOctober().equals("IS")||currentVegetable.getVegetableCalendarOctober().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarOctober().equals("OS")||currentVegetable.getVegetableCalendarOctober().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarOctober().equals("H")||currentVegetable.getVegetableCalendarOctober().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 11 :
                    if(currentVegetable.getVegetableCalendarNovember().equals("T")||currentVegetable.getVegetableCalendarNovember().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarNovember().equals("IS")||currentVegetable.getVegetableCalendarNovember().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarNovember().equals("OS")||currentVegetable.getVegetableCalendarNovember().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarNovember().equals("H")||currentVegetable.getVegetableCalendarNovember().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 12 :
                    if(currentVegetable.getVegetableCalendarDecember().equals("T")||currentVegetable.getVegetableCalendarDecember().equals("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarDecember().equals("IS")||currentVegetable.getVegetableCalendarDecember().equals("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarDecember().equals("OS")||currentVegetable.getVegetableCalendarDecember().equals("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarDecember().equals("H")||currentVegetable.getVegetableCalendarDecember().equals("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                }
        }

        boolean allEmpty = true;

        LinearLayout toDoHarvestLinearLayout = v.findViewById(R.id.lineToDoHarvest);
        toDoHarvestLinearLayout.setVisibility(View.INVISIBLE);
        if(listToDoHarvest.size()>0){
            toDoHarvestLinearLayout.setVisibility(View.VISIBLE);
            TextView textView = v.findViewById(R.id.textToDoHarvest);
            textView.setText(listToDoHarvest.toString().replace("[","").replace("]",""));
            allEmpty=false;
        }

        LinearLayout toDoIndoorSeedinginearLayout = v.findViewById(R.id.lineToDoIndoorSeeding);
        toDoIndoorSeedinginearLayout.setVisibility(View.INVISIBLE);
        if(listToDoIndoorSeeding.size()>0){
            toDoIndoorSeedinginearLayout.setVisibility(View.VISIBLE);
            TextView textView = v.findViewById(R.id.textToDoIndoorSeeding);
            textView.setText(listToDoIndoorSeeding.toString().replace("[","").replace("]",""));
            allEmpty=false;
        }
        LinearLayout toDoOutdoorSeedinginearLayout = v.findViewById(R.id.lineToDoOutdoorSeeding);
        toDoOutdoorSeedinginearLayout.setVisibility(View.INVISIBLE);
        if(listToDoOutdoorSeeding.size()>0){
            toDoOutdoorSeedinginearLayout.setVisibility(View.VISIBLE);
            TextView textView = v.findViewById(R.id.textToDoOutdoorSeeding);
            textView.setText(listToDoOutdoorSeeding.toString().replace("[","").replace("]",""));
            allEmpty=false;
        }
        LinearLayout toDoTransplantationLinearLayout = v.findViewById(R.id.lineToDoTransplantation);
        toDoTransplantationLinearLayout.setVisibility(View.INVISIBLE);
        if(listToDoTransplantation.size()>0){
            toDoTransplantationLinearLayout.setVisibility(View.VISIBLE);
            TextView textView = v.findViewById(R.id.textToDoTransplantation);
            textView.setText(listToDoTransplantation.toString().replace("[","").replace("]",""));
            allEmpty=false;
        }

        TextView tvToDoHome = v.findViewById(R.id.textToDoHome);
        if(allEmpty){
            tvToDoHome.setText(getResources().getString(R.string.nothing_to_do_today));
        }else{
            tvToDoHome.setText(getResources().getString(R.string.today_i_can));
        }

        //start builder
        builder = new AlertDialog.Builder(v.getContext());
        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu mOptionsMenu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, mOptionsMenu);

        MenuItem myPreferences = (MenuItem) mOptionsMenu.findItem(R.id.app_bar_myparapeters);
        myPreferences.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((MainActivity) getActivity()).launchMyPreferences();
                return false;
            }
        });
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        super.onCreateOptionsMenu(mOptionsMenu, inflater);
    }
    public void goCalendarPlantation(){
        ((MainActivity) getActivity()).launchVegetableCalendarFragment();
    }

    public void goMyVegetableGarden(){
        ((MainActivity) getActivity()).launchMyVegetableGardenFragment();
    }

    //add some vegetable
    public void startDialogAdd(){
       builder.setTitle(getResources().getString(R.string.add_to_my_vegetable_garden));
       /* List<VegetableCalendar> vegetableCalendarResult = vegetableCalendarDB.findVegetableCalendar(vegetableName);
        final VegetableCalendar vegetableCalendarSelected = vegetableCalendarResult.get(0);*/

        final List<String> listNames = new ArrayList<String>();
        List<VegetableCalendar> allVegetables = vegetableCalendarDB.getVegetableCalendars();
        List<VegetableCalendar> vegetableCalendars = vegetableCalendarDB.fillMyVegetableGarden();

        for (VegetableCalendar vegetable: allVegetables){
            boolean isIn = false;
            for(VegetableCalendar myVegetable:vegetableCalendars){
                if(vegetable.getVegetableCalendarName().equals(myVegetable.getVegetableCalendarName())){
                    isIn = true;
                    break;
                }
            }
            if(!isIn){
                listNames.add(vegetable.getVegetableCalendarName());
            }
        }

        final CharSequence[] chSlistNames= listNames.toArray(new CharSequence[listNames.size()]);

        final boolean[] listCheckedName= new boolean[listNames.size()];

        builder.setMultiChoiceItems(chSlistNames,listCheckedName,new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                listCheckedName[which] = isChecked;
            }
        });

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //prepare toast
                List<String> addedSucces = new ArrayList<>();
                List<String> addedError = new ArrayList<>();
                int i = 0;
                while(i<listNames.size()){
                    if(listCheckedName[i]){
                        String vegetableName = chSlistNames[i].toString();
                        VegetableCalendar vegetableCalendar = vegetableCalendarDB.getVegetableCalendarByName(vegetableName);
                        String response = vegetableCalendarDB.addVegetableToMyVegetableGarden(vegetableCalendar);
                        if(response.equals("OK")){
                            addedSucces.add(vegetableName);
                        }else{
                            addedError.add(vegetableName);
                        }
                    }
                    i++;
                }

                // Do nothing but close the dialog
                if(addedSucces.size()>1){
                    Toast toast = Toast.makeText(getContext(), " "+ addedSucces+ " "+ getResources().getString(R.string.added_success_all) , Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }

                if(addedError.size()>1){
                    Toast toast = Toast.makeText(getContext(), " "+ addedError+ " "+ getResources().getString(R.string.already_exist_all) , Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }

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
}
