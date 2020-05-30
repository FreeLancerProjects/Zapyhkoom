package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;

import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.creativeshare.zapyhakoom.Model.BalanceCount;
import com.creativeshare.zapyhakoom.Model.Setting_Model;
import com.creativeshare.zapyhakoom.Model.UserModel;
import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Login;
import com.creativeshare.zapyhakoom.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 */
public class Fragment_More extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ImageView call, terms, bank_img, logout_img, lang_img;
    private TextView tv_balance;
    private LinearLayout logout;
    private ConstraintLayout consbalance;
    private Home_Activity activity;
    private Preferences preferences;
    private Setting_Model setting_models;
    private UserModel userModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_, container, false);
        intitview(view);
        get_setting();
if(userModel!=null){
    get_Balance();
}else {
    consbalance.setVisibility(View.GONE);
}
        // Inflate the layout for this fragment
        return view;
    }

    private void get_setting() {
        Api.getService().get_setting().enqueue(new Callback<Setting_Model>() {
            @Override
            public void onResponse(Call<Setting_Model> call, Response<Setting_Model> response) {
                if (response.isSuccessful()) {
                    setting_models = response.body();
                } else {
                    try {
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Setting_Model> call, Throwable t) {
                Log.e("Error_code", t.getMessage());


            }
        });
    }

    private void get_Balance() {
        Api.getService().getBalance(userModel.getData().getId()).enqueue(new Callback<BalanceCount>() {
            @Override
            public void onResponse(Call<BalanceCount> call, Response<BalanceCount> response) {
                if (response.isSuccessful()) {
                   // setting_models = response.body();
                    tv_balance.setText(response.body().getBalance()+"");
                } else {
                    try {
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BalanceCount> call, Throwable t) {
                Log.e("Error_code", t.getMessage());


            }
        });
    }

    private void intitview(View view) {
        call = view.findViewById(R.id.call);
        terms = view.findViewById(R.id.term);
        bank_img = view.findViewById(R.id.bank_img);
        logout_img = view.findViewById(R.id.logout_img);
        lang_img = view.findViewById(R.id.lang);
        logout = view.findViewById(R.id.logout);
        tv_balance = view.findViewById(R.id.tv_balance);
        consbalance=view.findViewById(R.id.cons_balance);
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        userModel=preferences.getUserData(activity);
        activity = (Home_Activity) getActivity();
        if (preferences.getlang(activity).equals("ar")) {
            call.setRotation(180);
            terms.setRotation(180);
            bank_img.setRotation(180);
            lang_img.setRotation(180);
        } else {
            logout_img.setRotation(180);
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                activity.DisplayFragment_ContactUS();

            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         if (setting_models != null) {
                                             if (preferences.getlang(activity).equals("ar")) {

                                                 activity.DisplayFragmentTerms_Condition(setting_models.getInnerData().getTerms_and_conditions());
                                             } else {
                                                 activity.DisplayFragmentTerms_Condition(setting_models.getInnerData().getTerms_and_conditions_en());
                                             }
                                         } else {
                                             activity.DisplayFragmentTerms_Condition(null);
                                         }

                                     }

                                 }


        );
        lang_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.open_dialog();
            }
        });
        bank_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.display_bank_account(setting_models);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getUserData(activity) != null) {
                    preferences.create_update_userdata(activity, null);
                    preferences.create_update_session(activity, Tags.session_logout);
                    // preferences.create_update_lang(activity,Tags.pref_lang);
                    Intent intent = new Intent(activity, Login.class);
                    startActivity(intent);
                    activity.finish();
                } else {
                    Common.CreateUserNotSignInAlertDialog(activity);
                }
            }
        });
    }


    public static Fragment_More newInstance() {
        return new Fragment_More();
    }


}
