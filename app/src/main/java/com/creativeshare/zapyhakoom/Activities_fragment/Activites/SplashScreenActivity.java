package com.creativeshare.zapyhakoom.Activities_fragment.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.creativeshare.zapyhakoom.Language.Language;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.R;


public class SplashScreenActivity  extends AppCompatActivity {

    Preferences preferences;

    FrameLayout im;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreenmaker);
        preferences=Preferences.getInstance();
im=(FrameLayout)findViewById(R.id.fl);
         Animation animation;

animation= AnimationUtils.loadAnimation(getBaseContext(),R.anim.lanuch);
        im.startAnimation(animation);
animation.setAnimationListener(new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(preferences.getSession(SplashScreenActivity.this).equals(Tags.session_login)){
            Intent i = new Intent(SplashScreenActivity.this, Home_Activity.class);
            i.putExtra("param","4");

            startActivity(i);
        }
        else{
            if(preferences.getisfirsttime(SplashScreenActivity.this)==true){
                Intent i = new Intent(SplashScreenActivity.this,Intro_Activity.class);
                startActivity(i);

            }
            else{
            Intent i = new Intent(SplashScreenActivity.this,Login.class);
            startActivity(i);}
        }
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
});
    }
}
