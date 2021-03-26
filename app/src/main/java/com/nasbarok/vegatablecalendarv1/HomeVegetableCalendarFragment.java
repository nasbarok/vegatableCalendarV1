package com.nasbarok.vegatablecalendarv1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

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
    private String inputTextCity;


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


        //start builder
        builder = new AlertDialog.Builder(v.getContext());

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
                    if(currentVegetable.getVegetableCalendarJanuary().contains("T")||currentVegetable.getVegetableCalendarJanuary().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJanuary().contains("IS")||currentVegetable.getVegetableCalendarJanuary().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJanuary().contains("OS")||currentVegetable.getVegetableCalendarJanuary().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJanuary().contains("H")||currentVegetable.getVegetableCalendarJanuary().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 2 :
                    if(currentVegetable.getVegetableCalendarFebruary().contains("T")||currentVegetable.getVegetableCalendarFebruary().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarFebruary().contains("IS")||currentVegetable.getVegetableCalendarFebruary().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarFebruary().contains("OS")||currentVegetable.getVegetableCalendarFebruary().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarFebruary().contains("H")||currentVegetable.getVegetableCalendarFebruary().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 3 :
                    if(currentVegetable.getVegetableCalendarMarch().contains("T")||currentVegetable.getVegetableCalendarMarch().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMarch().contains("IS")||currentVegetable.getVegetableCalendarMarch().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMarch().contains("OS")||currentVegetable.getVegetableCalendarMarch().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMarch().contains("H")||currentVegetable.getVegetableCalendarMarch().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 4 :
                    if(currentVegetable.getVegetableCalendarApril().contains("T")||currentVegetable.getVegetableCalendarApril().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarApril().contains("IS")||currentVegetable.getVegetableCalendarApril().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarApril().contains("OS")||currentVegetable.getVegetableCalendarApril().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarApril().contains("H")||currentVegetable.getVegetableCalendarApril().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 5 :
                    if(currentVegetable.getVegetableCalendarMay().contains("T")||currentVegetable.getVegetableCalendarMay().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMay().contains("IS")||currentVegetable.getVegetableCalendarMay().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMay().contains("OS")||currentVegetable.getVegetableCalendarMay().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarMay().contains("H")||currentVegetable.getVegetableCalendarMay().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 6 :
                    if(currentVegetable.getVegetableCalendarJune().contains("T")||currentVegetable.getVegetableCalendarJune().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJune().contains("IS")||currentVegetable.getVegetableCalendarJune().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJune().contains("OS")||currentVegetable.getVegetableCalendarJune().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJune().contains("H")||currentVegetable.getVegetableCalendarJune().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 7 :
                    if(currentVegetable.getVegetableCalendarJuly().contains("T")||currentVegetable.getVegetableCalendarJuly().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJuly().contains("IS")||currentVegetable.getVegetableCalendarJuly().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJuly().contains("OS")||currentVegetable.getVegetableCalendarJuly().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarJuly().contains("H")||currentVegetable.getVegetableCalendarJuly().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 8 :
                    if(currentVegetable.getVegetableCalendarAugust().contains("T")||currentVegetable.getVegetableCalendarAugust().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarAugust().contains("IS")||currentVegetable.getVegetableCalendarAugust().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarAugust().contains("OS")||currentVegetable.getVegetableCalendarAugust().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarAugust().contains("H")||currentVegetable.getVegetableCalendarAugust().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 9 :
                    if(currentVegetable.getVegetableCalendarSeptember().contains("T")||currentVegetable.getVegetableCalendarSeptember().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarSeptember().contains("IS")||currentVegetable.getVegetableCalendarSeptember().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarSeptember().contains("OS")||currentVegetable.getVegetableCalendarSeptember().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarSeptember().contains("H")||currentVegetable.getVegetableCalendarSeptember().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 10 :
                    if(currentVegetable.getVegetableCalendarOctober().contains("T")||currentVegetable.getVegetableCalendarOctober().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarOctober().contains("IS")||currentVegetable.getVegetableCalendarOctober().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarOctober().contains("OS")||currentVegetable.getVegetableCalendarOctober().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarOctober().contains("H")||currentVegetable.getVegetableCalendarOctober().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 11 :
                    if(currentVegetable.getVegetableCalendarNovember().contains("T")||currentVegetable.getVegetableCalendarNovember().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarNovember().contains("IS")||currentVegetable.getVegetableCalendarNovember().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarNovember().contains("OS")||currentVegetable.getVegetableCalendarNovember().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarNovember().contains("H")||currentVegetable.getVegetableCalendarNovember().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                case 12 :
                    if(currentVegetable.getVegetableCalendarDecember().contains("T")||currentVegetable.getVegetableCalendarDecember().contains("T"+alfTermination)){
                        listToDoTransplantation.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarDecember().contains("IS")||currentVegetable.getVegetableCalendarDecember().contains("IS"+alfTermination)){
                        listToDoIndoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarDecember().contains("OS")||currentVegetable.getVegetableCalendarDecember().contains("OS"+alfTermination)){
                        listToDoOutdoorSeeding.add(currentVegetable.getVegetableCalendarName());
                    };
                    if(currentVegetable.getVegetableCalendarDecember().contains("H")||currentVegetable.getVegetableCalendarDecember().contains("H"+alfTermination)){
                        listToDoHarvest.add(currentVegetable.getVegetableCalendarName());
                    };
                    break;
                }
        }

        boolean allEmpty = true;

        RelativeLayout toDoHarvestLinearLayout = v.findViewById(R.id.lineToDoHarvest);
        toDoHarvestLinearLayout.setVisibility(View.GONE);
        if(listToDoHarvest.size()>0){
            toDoHarvestLinearLayout.setVisibility(View.VISIBLE);
            TextView textView = v.findViewById(R.id.textToDoHarvest);
            textView.setText(listToDoHarvest.toString().replace("[","").replace("]",""));
            allEmpty=false;
        }

        RelativeLayout toDoIndoorSeedinginearLayout = v.findViewById(R.id.lineToDoIndoorSeeding);
        toDoIndoorSeedinginearLayout.setVisibility(View.GONE);
        if(listToDoIndoorSeeding.size()>0){
            toDoIndoorSeedinginearLayout.setVisibility(View.VISIBLE);
            TextView textView = v.findViewById(R.id.textToDoIndoorSeeding);
            textView.setText(listToDoIndoorSeeding.toString().replace("[","").replace("]",""));
            allEmpty=false;
        }
        RelativeLayout toDoOutdoorSeedinginearLayout = v.findViewById(R.id.lineToDoOutdoorSeeding);
        toDoOutdoorSeedinginearLayout.setVisibility(View.GONE);
        if(listToDoOutdoorSeeding.size()>0){
            toDoOutdoorSeedinginearLayout.setVisibility(View.VISIBLE);
            TextView textView = v.findViewById(R.id.textToDoOutdoorSeeding);
            textView.setText(listToDoOutdoorSeeding.toString().replace("[","").replace("]",""));
            allEmpty=false;
        }
        RelativeLayout toDoTransplantationLinearLayout = v.findViewById(R.id.lineToDoTransplantation);
        toDoTransplantationLinearLayout.setVisibility(View.GONE);
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
