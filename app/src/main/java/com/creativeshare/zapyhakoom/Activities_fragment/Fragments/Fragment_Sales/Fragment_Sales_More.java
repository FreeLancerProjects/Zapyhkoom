package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.creativeshare.zapyhakoom.Model.Setting_Model;
import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Login;
import com.creativeshare.zapyhakoom.R;


public class Fragment_Sales_More extends Fragment {
    private ImageView logout_img,lang_img;
    private LinearLayout logout;
    private Home_Activity activity;
    private Preferences preferences;
    private Setting_Model setting_models;
    public static Fragment_Sales_More newInstance() {

        return new Fragment_Sales_More();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_sales_more, container, false);
       intitview(view);
       return view;
    }
    private void intitview(View view) {

        logout_img =  view.findViewById(R.id.logout_img);
        lang_img =  view.findViewById(R.id.lang);
        logout =  view.findViewById(R.id.logout);
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        activity = (Home_Activity) getActivity();
        if (preferences.getlang(activity).equals("ar")) {
            lang_img.setRotation(180);
        } else {
            logout_img.setRotation(180);
        }


        lang_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.open_dialog();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preferences.getUserData(activity)!=null){
                    preferences.create_update_userdata(activity, null);
                    preferences.create_update_session(activity, Tags.session_logout);
                   // preferences.create_update_lang(activity,Tags.pref_lang);
                    Intent intent = new Intent(activity, Login.class);
                    startActivity(intent);
                    activity.finish();}
                else {
                    Common.CreateUserNotSignInAlertDialog(activity);
                }
            }
        });
    }


}
