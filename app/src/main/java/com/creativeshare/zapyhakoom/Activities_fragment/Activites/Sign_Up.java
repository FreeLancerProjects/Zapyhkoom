package com.creativeshare.zapyhakoom.Activities_fragment.Activites;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.creativeshare.zapyhakoom.Language.Language;
import com.creativeshare.zapyhakoom.Model.UserModel;
import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_Up extends AppCompatActivity {
    EditText name;
    EditText phone;
    EditText pass;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        name=(EditText)findViewById(R.id.name_sinup);
        phone=(EditText)findViewById(R.id.phone_sinup);
        pass=(EditText)findViewById(R.id.pass_sinup);

    }
    public void sinup(View view){
        final ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.show();
        String sinup_phone = phone.getText().toString();
        String sinup_pass=pass.getText().toString();
        String sinup_name=name.getText().toString();
        if(sinup_phone.isEmpty()||sinup_pass.isEmpty()||sinup_pass.length()<6||sinup_name.isEmpty()){
           dialog.dismiss();

            if(sinup_phone.isEmpty()){
                phone.setError("");
            }
            if(sinup_pass.isEmpty()||sinup_pass.length()<6){
                pass.setError("");

            }
            if(sinup_name.isEmpty()){
                name.setError("");

            }
        }
        else{
            if(Common.validateUsing_libphonenumber("00966",sinup_phone)==true){
            Common.CloseKeyBoard(Sign_Up.this,view);

            Api.getService()
                    .register(sinup_phone,sinup_name,sinup_pass)
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            dialog.dismiss();

                            if (response.isSuccessful()) {

                                Preferences.getInstance().create_update_userdata(Sign_Up.this, response.body());
                                Intent i = new Intent(Sign_Up.this, Home_Activity.class);
                                i.putExtra("param","3");
                                startActivity(i);
                                finish();
                            }

                            else{
                                try {
                                    Log.e("code",response.code()+""+response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if(response.code()==409){
                                    Common.CreateSignAlertDialog(Sign_Up.this,getResources().getString(R.string.excuse_this_phone_register_before));

                                }
                                else
                                {
                                    Common.CreateSignAlertDialog(Sign_Up.this,getResources().getString(R.string.faild));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Common.CreateSignAlertDialog(Sign_Up.this,getResources().getString(R.string.faild));

                            Log.e("Error",t.getMessage());
                            dialog.dismiss();


                        }
                    });
        }
        else {
            Common.CreateSignAlertDialog(this,getResources().getString(R.string.invaild_phone));
            }
        }
    }

    public void skip(View view) {
        Intent i = new Intent(Sign_Up.this, Home_Activity.class);
        startActivity(i);
        finish();
    }
}
