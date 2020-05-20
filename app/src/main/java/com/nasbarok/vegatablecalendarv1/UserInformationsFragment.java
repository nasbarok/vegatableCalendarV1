package com.nasbarok.vegatablecalendarv1;

import android.annotation.SuppressLint;
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
    private Integer TVCONTACTLISTID ;
    private Integer INPUTMAILID ;
    private Integer BTNADDMAILID ;
    private Integer BTNCLEARMAILID ;



    private RelativeLayout contactUserInformationLayout;
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
        contactUserInformationLayout = (RelativeLayout) v.findViewById(R.id.contactUserInfoLayout);
        utils = new Utils();
        loadUsersInformtionLayout();
        return v;
    }

    public void loadUsersInformtionLayout(){

      /*  RelativeLayout.LayoutParams paramsLayout = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        contactUserInformationLayout.setLayoutParams(paramsLayout);*/
        vegetableCalendarDB = new VegetableCalendarDBHelper(getContext());
        final UserInformations userInformations = vegetableCalendarDB.getUserInformations();
        final TextView tvContactList = new TextView(getContext());
        TVCONTACTLISTID = tvContactList.getId();
        tvContactList.setText(userInformations.getMails());
        /*RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        /*relativeLayoutInputMail.setLayoutParams(rlParams);*/

        final EditText addMail = new EditText(getContext());
        addMail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        INPUTMAILID = addMail.getId();

        Button buttonAddMail = new Button(getContext());
        BTNADDMAILID = buttonAddMail.getId();

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
        BTNCLEARMAILID = buttonClearMail.getId();
        buttonClearMail.setId(BTNCLEARMAILID);
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


        RelativeLayout.LayoutParams relativeLayoutContactListParams = (RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutContactListParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        relativeLayoutContactListParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tvContactList.setLayoutParams(relativeLayoutContactListParams);
        contactUserInformationLayout.addView(tvContactList);
        //tvContactList.setLayoutParams();

        RelativeLayout.LayoutParams relativeLayoutAddMailParams = (RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutAddMailParams.addRule(RelativeLayout.ALIGN_BOTTOM,TVCONTACTLISTID);
        relativeLayoutAddMailParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        addMail.setLayoutParams(relativeLayoutAddMailParams);

        contactUserInformationLayout.addView(addMail);
        RelativeLayout.LayoutParams relativeLayoutButtonAddMailParams = (RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutButtonAddMailParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        relativeLayoutButtonAddMailParams.addRule(RelativeLayout.CENTER_VERTICAL);
        buttonAddMail.setLayoutParams(relativeLayoutButtonAddMailParams);
        contactUserInformationLayout.addView(buttonAddMail);
        RelativeLayout.LayoutParams relativeLayoutButtonClearMailParams = (RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutButtonClearMailParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        relativeLayoutButtonClearMailParams.addRule(RelativeLayout.END_OF,BTNADDMAILID);
        buttonClearMail.setLayoutParams(relativeLayoutButtonAddMailParams);
        contactUserInformationLayout.addView(buttonClearMail);
        contactUserInformationLayout.setPadding(5,5,5,5);
    }
}
