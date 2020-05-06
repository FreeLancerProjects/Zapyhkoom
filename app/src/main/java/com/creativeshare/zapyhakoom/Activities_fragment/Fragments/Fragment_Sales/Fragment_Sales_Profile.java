package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.creativeshare.zapyhakoom.Model.UserModel;
import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Sales_Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private Preferences preferences;
    private TextView phone, name,pass;
    private Button edit;
    private int user_id;
    private Home_Activity activity;
    private UserModel usermodel;


    // TODO: Rename and change types and number of parameters
    public static Fragment_Sales_Profile newInstance() {
       return new Fragment_Sales_Profile();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_sales__profile, container, false);
        intitview(view);
        return view;
    }

    private void intitview(final View view) {
        preferences = Preferences.getInstance();
        activity = (Home_Activity) getActivity();
        phone = view.findViewById(R.id.account_phone);
        name = view.findViewById(R.id.account_name);
        pass = (EditText) view.findViewById(R.id.account_pass);
        edit = (Button) view.findViewById(R.id.edit_account);
        usermodel = preferences.getUserData(activity);
        phone.setText(usermodel.getData().getMobile());
        name.setText(usermodel.getData().getName());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upateprofile(view);

            }
        });

    }

    private void upateprofile(View view) {
        String user_phone = phone.getText().toString();
        String user_name = name.getText().toString();
        String user_pass = pass.getText().toString();
        if ( user_pass.isEmpty() ) {

            if (user_pass.isEmpty()) {
                pass.setError("");
            }

        }


        else {
            user_id = usermodel.getData().getId();
            Common.CloseKeyBoard(activity, view);
            Api.getService().update_profile(user_id, user_name, user_pass, user_phone).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        Common.CreateSignAlertDialog(activity, activity.getString(R.string.update_success));
                        Preferences.getInstance().create_update_userdata(activity, response.body());
                        pass.setText("");
                    } else {
                        try {
                            Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.e("Error_code", t.getMessage());
                    Common.CreateSignAlertDialog(activity, activity.getString(R.string.faild));


                }
            });

        }
    }


}
