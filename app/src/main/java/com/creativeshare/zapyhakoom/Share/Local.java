package com.creativeshare.zapyhakoom.Share;


import android.app.Application;
import android.content.Context;

import com.creativeshare.zapyhakoom.Language.Language;

public class Local extends Application {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));

    }

}

