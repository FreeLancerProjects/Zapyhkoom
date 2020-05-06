package com.creativeshare.zapyhakoom.preferences;

import android.content.Context;
import android.content.SharedPreferences;


import com.creativeshare.zapyhakoom.Model.Orders_Cart_Model;
import com.creativeshare.zapyhakoom.Model.UserModel;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Preferences {

    private static Preferences instance=null;

    private Preferences() {
    }

    public static Preferences getInstance()
    {
        if (instance==null)
        {
            instance = new Preferences();
        }
        return instance;
    }
    public void create_update_lang(Context context,String session){
        SharedPreferences sharedPreferences=context.getSharedPreferences("langu",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("lang",session);
        editor.apply();


    }

    public String getlang(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("langu",Context.MODE_PRIVATE);
String session=preferences.getString("lang", Tags.pref_lang);
        return session;
    }
    public void create_update_userdata(Context context, UserModel usermodel){
        SharedPreferences sharedPreferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String user_data=gson.toJson(usermodel);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("user_data",user_data);
        editor.apply();
        create_update_session(context,Tags.session_login);

    }
    public UserModel getUserData(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = preferences.getString("user_data","");
        UserModel userModel = gson.fromJson(user_data, UserModel.class);
        return userModel;
    }
    public void create_update_session(Context context,String session){
        SharedPreferences sharedPreferences=context.getSharedPreferences("session",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("state",session);
        editor.apply();


    }
    public String getSession(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("session",Context.MODE_PRIVATE);
        String session=preferences.getString("state",Tags.session_logout);
        return session;
    }
    public void create_update_order(Context context, List<Orders_Cart_Model> buy_models){
        SharedPreferences sharedPreferences=context.getSharedPreferences("order",Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String user_order=gson.toJson(buy_models);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("user_order",user_order);
        editor.apply();

    }
    public ArrayList<Orders_Cart_Model> getUserOrder(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("order",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_order = preferences.getString("user_order",null);
        Type type=new TypeToken<ArrayList<Orders_Cart_Model>>(){}.getType();
        ArrayList<Orders_Cart_Model> buy_models=gson.fromJson(user_order,type);
        return buy_models;
    }
    public void create_first_time(Context context, boolean isfirsttime){
        SharedPreferences sharedPreferences=context.getSharedPreferences("isfirsttime",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isfirsttime",isfirsttime);
        editor.apply();

    }
    public boolean getisfirsttime(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("isfirsttime",Context.MODE_PRIVATE);
         return sharedPreferences.getBoolean("isfirsttime",true);
    }
}
