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

public class Login extends AppCompatActivity {
    EditText phone;
   // EditText password;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      phone=(EditText)findViewById(R.id.phone_login);
     //   password=(EditText)findViewById(R.id.password_login);


    }

    public void sign_up(View view) {
        Intent i=new Intent(Login.this,Sign_Up.class);
        startActivity(i);
    }

    public void login(View view) {
        final ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.show();

        final String login_phone = phone.getText().toString();
       // final String login_pass=password.getText().toString();
        if(login_phone.isEmpty()
//                ||login_pass.isEmpty()
        ){
            dialog.dismiss();

            if(login_phone.isEmpty()){
              phone.setError("");
          }
//          if(login_pass.isEmpty()){
//              password.setError("");
//
//          }
        }
        else{
            if(Common.isValidMobile(login_phone)==true){
            Common.CloseKeyBoard(Login.this,view);
        Api.getService()
                .login(login_phone)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
dialog.dismiss();
                        if (response.isSuccessful()) {
                           // Preferences.getInstance().create_update_userdata(Login.this, response.body());
                            Intent i = new Intent(Login.this, Activity_code.class);
                            i.putExtra("phone",login_phone);
                            i.putExtra("phone_code","+966");
i.putExtra("user",response.body());
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            try {
                                Log.e("code",response.code()+""+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (response.code() == 404)
                            {   Common.CreateSignAlertDialog(Login.this,getResources().getString(R.string.user_not_found));

                            }
                            else if(response.code()==403){
                                Common.CreateSignAlertDialog(Login.this,getResources().getString(R.string.Incorrect_password));

                            }
                            else{
                                Common.CreateSignAlertDialog(Login.this,getResources().getString(R.string.faild));

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.e("Error",t.getMessage());
                        Common.CreateSignAlertDialog(Login.this,getResources().getString(R.string.faild));
                        dialog.dismiss();
                    }
                });}else {
                dialog.dismiss();

                Common.CreateSignAlertDialog(this,getResources().getString(R.string.invaild_phone));

            }

    }}

    public void skip(View view) {
        Intent i = new Intent(Login.this, Home_Activity.class);
        startActivity(i);
        finish();
    }
}
