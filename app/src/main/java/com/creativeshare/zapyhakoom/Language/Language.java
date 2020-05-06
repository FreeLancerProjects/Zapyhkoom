package com.creativeshare.zapyhakoom.Language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;


import com.creativeshare.zapyhakoom.preferences.Preferences;

import java.util.Locale;

public class Language {
static Preferences preferences;


    public static void setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        updateResources(c, language);
    }

    public static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);

        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());

        }
        return context;


    }

    public static String getLanguage(Context c) {
        preferences=Preferences.getInstance();

      return   preferences.getlang(c);
    }
    private static void persistLanguage(Context c, String language) {
        preferences=Preferences.getInstance();

        preferences.create_update_lang(c,language);
    }}
