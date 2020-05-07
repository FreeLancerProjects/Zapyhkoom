package com.creativeshare.zapyhakoom.Activities_fragment.Activites;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.creativeshare.zapyhakoom.Language.Language;
import com.creativeshare.zapyhakoom.Model.UserModel;
import com.creativeshare.zapyhakoom.R;
import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_code extends AppCompatActivity {
    private EditText edt_code;
    private Button btn_resend, btn_confirm;
    private String phone_code = "", phone_number = "";
    private boolean canResend = true;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String id;
    private String code;
    private FirebaseAuth mAuth;
    private CountDownTimer countDownTimer;
    private UserModel userModel;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));

    }
    private void getDataFromIntent()
    {
        Intent intent = getIntent();
        if (intent!=null) {
phone_number=intent.getStringExtra("phone");
phone_code=intent.getStringExtra("phone_code");
userModel= (UserModel) intent.getSerializableExtra("user");
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        getDataFromIntent();
        edt_code = findViewById(R.id.edt_code);
        btn_resend = findViewById(R.id.btn_resend);
        btn_confirm = findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (canResend) {
                    // sendSMSCode(phone_code,phone_number);
                    sendverficationcode(phone_number, phone_code.replace("00", "+"));
                    startCounter();
                }
            }
        });



        startCounter();

        authn();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendverficationcode(phone_number, phone_code.replace("00", "+"));
            }
        }, 3);

    }

    private void authn() {

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e("id", s);
                id = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                Log.e("code",phoneAuthCredential.getSmsCode());
//phoneAuthCredential.getProvider();
                if (phoneAuthCredential.getSmsCode() != null) {
                    code = phoneAuthCredential.getSmsCode();
                    edt_code.setText(code);
                   // edt_code.setPinBackground(activity.getResources().getDrawable(R.drawable.edit_shape2));
                    siginwithcredental(phoneAuthCredential);
                } else {
                    siginwithcredental(phoneAuthCredential);
                }


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("llll", e.getMessage());
            }


        };


    }

    private void checkData() {
        String code = edt_code.getText().toString().trim();
        if (!TextUtils.isEmpty(code)) {
            Common.CloseKeyBoard(this, edt_code);
            verfiycode(code);
        } else {
            edt_code.setError(getString(R.string.field_req));
        }
    }


    private void verfiycode(String code) {
        // Toast.makeText(register_activity,code,Toast.LENGTH_LONG).show();
        countDownTimer.cancel();

        Log.e("ccc", code);
        if (id != null) {

            try {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, code);
                siginwithcredental(credential);

            }catch (Exception e){

            }
        }
    }

    private void siginwithcredental(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //  Log.e("data",phone);
                    phone_code = phone_code.replace("+","00");
                    mAuth.signOut();
                    //
                    // activity.NavigateToClientHomeActivity();
                    // ValidateCode("1234");
                  //  activity.signIn(phone_number, country_code, phone_code);
                    Preferences.getInstance().create_update_userdata(Activity_code.this, userModel);
                    Intent i = new Intent(Activity_code.this, Home_Activity.class);
                    startActivity(i);
                    finish();

                }
                else {
                    Log.e("llllll",";llllll");

                }


            }
        });
    }

    private void sendverficationcode(String phone, String phone_code) {
        Log.e("kkk", phone_code + phone);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_code + phone, 59, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallbacks);

    }

    private void startCounter() {
        countDownTimer = new CountDownTimer(59000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                canResend = false;

                int AllSeconds = (int) (millisUntilFinished / 1000);
                int seconds = AllSeconds % 60;


                btn_resend.setText("00:" + seconds);
            }

            @Override
            public void onFinish() {
                canResend = true;
                btn_resend.setText(getString(R.string.resend));
            }
        }.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
