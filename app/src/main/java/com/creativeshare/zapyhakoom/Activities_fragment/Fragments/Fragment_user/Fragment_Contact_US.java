package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;

import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.fragment.app.Fragment;

import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 */
public class Fragment_Contact_US extends Fragment {

    private Home_Activity activity;
    private Preferences preferences;
    private EditText name, email, message;
    private Button send;
    private ImageView back;
    public static Fragment_Contact_US newInstance() {
        return new Fragment_Contact_US();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        intitview(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void intitview(final View view) {
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        back = (ImageView) view.findViewById(R.id.back1);
        name = (EditText) view.findViewById(R.id.name_contact);
        email = (EditText) view.findViewById(R.id.email_contact);
        message = (EditText) view.findViewById(R.id.message_contact);
        send = (Button) view.findViewById(R.id.send);
        if (preferences.getlang(activity).equals("en")) {
            back.setRotation(180);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msname = name.getText().toString();
                String msemail = email.getText().toString();
                String msmessage = message.getText().toString();
                if (msemail.isEmpty() || msmessage.isEmpty() || msname.isEmpty()) {
                    if (msname.isEmpty()) {
                        name.setError("");
                    }
                    if (msemail.isEmpty()) {
                        email.setError("");
                    }

                    if (msmessage.isEmpty()) {
                        message.setError("");
                    }
                } else {
                    if(Common.isValidMail(msemail)==true){
                    Common.CloseKeyBoard(activity, view);
                    Call_us(msname, msemail, msmessage);}
                    else {
                        Common.CreateSignAlertDialog(activity,activity.getString(R.string.invaild_email));
                    }

                }
            }
        });
    }

    private void Call_us(String msname, String msemail, String msmessage) {
        Common.CloseKeyBoard(activity, getView());

        Api.getService().contact_us(msname, msemail, msmessage).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Common.CreateSignAlertDialog(activity,activity.getString(R.string.send_succsfull));
                } else {
                    try {
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error_code", t.getMessage());

            }
        });
    }




}
