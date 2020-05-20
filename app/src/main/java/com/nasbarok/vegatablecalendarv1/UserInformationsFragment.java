package com.nasbarok.vegatablecalendarv1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.UserInformations;
import com.nasbarok.vegatablecalendarv1.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInformationsFragment#newInstance} factory method to
 * create an instance of this fragment.  userInfoLayout
 */
public class UserInformationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout layoutUserInformation;
    private VegetableCalendarDBHelper vegetableCalendarDB;
    private Utils utils;
    // TODO: Rename and change types of parameters

    public UserInformationsFragment() {
        // Required empty public constructor
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_informations, container, false);
        layoutUserInformation = (LinearLayout) v.findViewById(R.id.userInfoLayout);
        utils = new Utils();
        loadUsersInformtionLayout();
        return v;
    }

    public void loadUsersInformtionLayout(){
      /*  LinearLayout.LayoutParams lyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);*/
        /*layoutUserInformation.setLayoutParams(lyParams);*/
        TextView tvSep = new TextView(getContext());
        final TextView tvMsg = new TextView(getContext());
        tvMsg.setText(getResources().getString(R.string.add_contact));
        layoutUserInformation.addView(tvMsg);

        vegetableCalendarDB = new VegetableCalendarDBHelper(getContext());
        final UserInformations userInformations = vegetableCalendarDB.getUserInformations();
        final TextView tvContactList = new TextView(getContext());
        tvContactList.setText(userInformations.getMails());
        /*RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        /*relativeLayoutInputMail.setLayoutParams(rlParams);*/
        final EditText addMail = new EditText(getContext());
        addMail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        Button buttonAddMail = new Button(getContext());
        buttonAddMail.setText(getResources().getString(R.string.add));
        buttonAddMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(utils.isValidEmail(addMail.getText())&&addMail.getText()!=null&&!addMail.getText().toString().equals("")
                &&!userInformations.getMails().contains(addMail.getText().toString())){
                    String listMail = userInformations.getMails();
                    if(listMail.contains(",")||!userInformations.getMails().equals("")){
                        listMail = listMail + ","+addMail.getText() ;
                    }else {
                        listMail = addMail.getText().toString();
                    }
                    userInformations.setMails(listMail);
                    tvContactList.setText(listMail);
                    vegetableCalendarDB.saveUserInformations(userInformations);
                    utils.notifyToast(getResources().getString(R.string.add)+" "+addMail.getText().toString(),getContext());
                }else{
                    utils.notifyToast(addMail.getText().toString()+":"+getResources().getString(R.string.not_valid_mail)+" ",getContext());

                }


            }
        });
        Button buttonClearMail = new Button(getContext());
        buttonClearMail.setText(getResources().getString(R.string.clear));
        /*relativeLayoutInputMail.addView(buttonAddMail);*/
        buttonClearMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    userInformations.setMails("");
                    tvContactList.setText(userInformations.getMails());
                    vegetableCalendarDB.saveUserInformations(userInformations);
                    utils.notifyToast(getResources().getString(R.string.clear),getContext());

            }
        });
        layoutUserInformation.addView(tvContactList);
        layoutUserInformation.addView(addMail);
        LinearLayout linearLayoutHBtn = new LinearLayout(getContext());
        linearLayoutHBtn.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutHBtn.addView(buttonAddMail);
        linearLayoutHBtn.addView(buttonClearMail);
        layoutUserInformation.addView(linearLayoutHBtn);
    }
}
