package com.creativeshare.zapyhakoom.Share;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.creativeshare.zapyhakoom.Adapters.Orders_Adpter;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class Common {
    public  static int re =0;

    public static ProgressDialog createProgressDialog(Context context , String msg)
    {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        ProgressBar bar = new ProgressBar(context);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        dialog.setIndeterminateDrawable(drawable);
        return dialog;



    }
    public static void CreateSignAlertDialog(Context context, String msg)
    {
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sign,null);
        Button doneBtn = view.findViewById(R.id.doneBtn);
        TextView tv_msg = view.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

       // dialog.getWindow().getAttributes().windowAnimations=R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
       // dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setView(view);
        dialog.show();
    }

    public static void CreateUserNotSignInAlertDialog(final Context context)
    {


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();


        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog,null);
        Button btn_sign_in = view.findViewById(R.id.btn_sign_in);
        Button btn_sign_up = view.findViewById(R.id.btn_sign_up);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);

        TextView tv_msg = view.findViewById(R.id.tv_msg);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                if (context instanceof Home_Activity)
                {
                    Home_Activity activity = (Home_Activity) context;
                    activity.NavigateToSignInActivity();
                }



            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                if (context instanceof Home_Activity)
                {
                    Home_Activity activity = (Home_Activity) context;
                    activity.NavigateToSignupActivity();

                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

       // dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
    }
    public static void CreateUserBackAlertDialog(final Context context)
    {


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();


        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog,null);
        Button ok = view.findViewById(R.id.yes_sure);
        Button btn_cancel = view.findViewById(R.id.No_cancell);

        TextView tv_msg = view.findViewById(R.id.tv_msg);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                if (context instanceof Home_Activity)
                {
                    Home_Activity activity = (Home_Activity) context;
                    activity.Back();

                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
    }
    public static void CreateUserFinishOrder(final Context context, final int order_id, final int user_id, final int postion, final Orders_Adpter.Eyas_Holder viewHolder)
    {


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();


        View view = LayoutInflater.from(context).inflate(R.layout.dialog_finish_order,null);
        Button ok = view.findViewById(R.id.yes_sure);
        Button btn_cancel = view.findViewById(R.id.No_cancell);

        TextView tv_msg = view.findViewById(R.id.tv_msg);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                if (context instanceof Home_Activity)
                {
                    Home_Activity activity = (Home_Activity) context;
                    activity.Finishorder(order_id,user_id,postion,viewHolder);

                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
    }

    public static void CreateUserCancelOrder(final Context context, final int order_id, final int user_id, final int postion, final Orders_Adpter.Eyas_Holder viewHolder)
    {


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();


        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cancel_order,null);
        Button ok = view.findViewById(R.id.yes_sure);
        Button btn_cancel = view.findViewById(R.id.No_cancell);

        TextView tv_msg = view.findViewById(R.id.tv_msg);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (context instanceof Home_Activity)
                {
                    Home_Activity activity = (Home_Activity) context;
                    activity.Cancelorder(order_id,user_id,activity,postion,viewHolder);

                }

                     }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();

    }
    public static void CreateRsting(final Context context, final int user_id, final int sales_id, final int id, final int postion, final Orders_Adpter.Eyas_Holder viewHolder)
    {


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();


        View view = LayoutInflater.from(context).inflate(R.layout.rating_dialog,null);
        Button ok = view.findViewById(R.id.rating_bt);
        final RatingBar ratingBar=view.findViewById(R.id.rating_bar);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                if (context instanceof Home_Activity)
                {
                    Home_Activity activity = (Home_Activity) context;
                    activity.rating(sales_id,user_id,ratingBar.getRating());
                    Common.CreateUserFinishOrder(activity, id, user_id,postion,viewHolder);
                }
            }
        });


        // dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
    }
    public static void CloseKeyBoard(Context context, View view)
    {
        if (context!=null&&view!=null)
        {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (manager!=null){
                manager.hideSoftInputFromWindow(view.getWindowToken(),0);

            }

        }


    }

    public static boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
  public static boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            return true;
        } else {

            return false;
        }
    }
}
