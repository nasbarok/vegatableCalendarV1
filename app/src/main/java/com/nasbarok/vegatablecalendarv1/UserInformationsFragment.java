package com.nasbarok.vegatablecalendarv1;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.UserInformations;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;
import com.nasbarok.vegatablecalendarv1.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInformationsFragment#newInstance} factory method to
 * create an instance of this fragment.  userInfoLayout
 */
public class UserInformationsFragment extends Fragment {

    private RelativeLayout contactUserInformationLayout;
    private VegetableCalendarDBHelper vegetableCalendarDB;
    private Utils utils;
    private TextView tvContactList;
    private EditText etMail;
    private Button btnAddMail;
    private Button btnClearMail;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private Button btnStartTime;
    private Button btnEndTime;
    private int  mHourEndTime, mMinuteEndTime, mHourStartTime, mMinuteStartTime;
    private UserInformations userInformations;


    public UserInformationsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserInformationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInformationsFragment newInstance() {
        UserInformationsFragment fragment = new UserInformationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_informations, container, false);
        //contactUserInformationLayout = (RelativeLayout) v.findViewById(R.id.contactUserInfoLayout);
        utils = new Utils();
        vegetableCalendarDB = new VegetableCalendarDBHelper(getContext());
        userInformations = vegetableCalendarDB.getUserInformations();

        tvContactList = v.findViewById(R.id.tvContactListId);
        etMail = v.findViewById(R.id.etMailId);
        btnAddMail = v.findViewById(R.id.btnAddMailId);
        btnClearMail = v.findViewById(R.id.btnClearMailId);
        loadCantactMail();

        tvStartTime  = v.findViewById(R.id.tvStartTime);
        tvEndTime = v.findViewById(R.id.tvEndTime);
        btnStartTime = v.findViewById(R.id.btnChangeStartTime);
        btnEndTime = v.findViewById(R.id.btnChangeEndTime);

        loadTimerSetterNotification();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu mOptionsMenu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_information_menu, mOptionsMenu);

        MenuItem homeBtn = (MenuItem) mOptionsMenu.findItem(R.id.app_bar_home);
        homeBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((MainActivity) getActivity()).launchHomeFragment();
                return false;
            }
        });

        MenuItem myVegetableGardenCalendarBtn = (MenuItem) mOptionsMenu.findItem(R.id.app_bar_calendar_vegetable_garden);
        myVegetableGardenCalendarBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((MainActivity) getActivity()).launchVegetableCalendarFragment();
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

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.option_menu));


        super.onCreateOptionsMenu(mOptionsMenu, inflater);
    }

    public void loadCantactMail(){


        if(userInformations.getMails()==null||userInformations.getMails().equals("")){
            tvContactList.setText(getResources().getString(R.string.not_mail_exist));
        }else{
            tvContactList.setText(userInformations.getMails());
        }
        tvContactList.setText(userInformations.getMails());
        etMail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        //etMail.setIex(getResources().getString(R.string.input_mails_msg));
        btnAddMail.setText(getResources().getString(R.string.add));
        btnAddMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(utils.isValidEmail(etMail.getText())&&etMail.getText()!=null&&!etMail.getText().toString().equals("")
                &&!userInformations.getMails().contains(etMail.getText().toString())){
                    String listMail = userInformations.getMails();
                    if(listMail.contains(",")||!userInformations.getMails().equals("")){
                        listMail = listMail + ","+etMail.getText() ;
                    }else {
                        listMail = etMail.getText().toString();
                    }
                    userInformations.setMails(listMail);
                    tvContactList.setText(listMail);
                    vegetableCalendarDB.saveUserInformations(userInformations);
                    utils.notifyToast(getResources().getString(R.string.add)+" "+etMail.getText().toString(),getContext());
                }else{
                    utils.notifyToast(etMail.getText().toString()+":"+getResources().getString(R.string.not_valid_mail)+" ",getContext());

                }


            }
        });
        
        btnClearMail.setText(getResources().getString(R.string.clear));
        /*relativeLayoutInputMail.addView(buttonAddMail);*/
        btnClearMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    userInformations.setMails("");
                    tvContactList.setText(userInformations.getMails());
                    vegetableCalendarDB.saveUserInformations(userInformations);
                    utils.notifyToast(getResources().getString(R.string.clear),getContext());

            }
        });
    }

    public void loadTimerSetterNotification(){
        tvStartTime.setText(getResources().getString(R.string.start_time_msg));
        tvEndTime.setText(getResources().getString(R.string.end_time_msg));
        btnStartTime.setText(userInformations.getStartTime());
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] ints = utils.splitTimeValue(userInformations.getStartTime());
                mHourStartTime =ints[0];
                mMinuteStartTime = ints[1];

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                userInformations.setStartTime(hourOfDay + "h" + minute);
                                vegetableCalendarDB.saveUserInformations(userInformations);
                                btnStartTime.setText(userInformations.getStartTime());
                                utils.notifyToast(userInformations.getStartTime()+" "+getResources().getString(R.string.saved),getContext());
                            }
                        }, mHourStartTime, mMinuteStartTime, true);
                timePickerDialog.show();
            }

        });

        /*        btnEndTime*/
        btnEndTime.setText(userInformations.getEndTime());
        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] ints = utils.splitTimeValue(userInformations.getEndTime());
                mHourEndTime =ints[0];
                mMinuteEndTime = ints[1];

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                userInformations.setEndTime(hourOfDay + "h" + minute);
                                vegetableCalendarDB.saveUserInformations(userInformations);
                                btnEndTime.setText(userInformations.getEndTime());
                                utils.notifyToast(userInformations.getEndTime()+" "+getResources().getString(R.string.saved),getContext());
                            }
                        }, mHourEndTime, mMinuteEndTime, true);
                timePickerDialog.show();
            }

        });
    }

}
