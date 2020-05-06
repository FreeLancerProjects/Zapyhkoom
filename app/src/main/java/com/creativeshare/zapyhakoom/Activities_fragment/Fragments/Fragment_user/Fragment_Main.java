package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;

import android.graphics.PorterDuff;
import android.os.Bundle;


import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.creativeshare.zapyhakoom.Adapters.Catogries_Adapter;
import com.creativeshare.zapyhakoom.Adapters.SlidingImage_Adapter;
import com.creativeshare.zapyhakoom.Model.Catogry_Model_Slide;
import com.creativeshare.zapyhakoom.Model.Slider_Model;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class Fragment_Main extends Fragment {

    private List<Catogry_Model_Slide.InnerData> list;
    private ViewPager mPager;
    private TabLayout indicator;
    private Home_Activity activity;
    private int current_page = 0,NUM_PAGES;
    private TextView error;
    private ProgressBar progBar, progBarAds;
    private RecyclerView recc;
    private Catogries_Adapter eas;
    private SlidingImage_Adapter slidingImage__adapter;

    public static Fragment_Main newInstance() {
        return new Fragment_Main();
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);

        categories();
        get_slider();
        change_slide_image();
        return view;
    }

    private void change_slide_image() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (current_page==NUM_PAGES){
                    current_page = 0;
                }
                mPager.setCurrentItem(current_page++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }


    private void initView(View view) {
        list=new ArrayList<>();
        activity = (Home_Activity) getActivity();
        error = view.findViewById(R.id.error);
        recc = view.findViewById(R.id.rec);
        mPager = view.findViewById(R.id.view1);
        indicator = view.findViewById(R.id.tab1);
        recc.setLayoutManager(new GridLayoutManager(activity, 2));
        progBar = view.findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        progBarAds = view.findViewById(R.id.progBarAds);
        progBarAds.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        recc.setItemViewCacheSize(25);
        recc.setDrawingCacheEnabled(true);
        recc.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        eas = new Catogries_Adapter(list, activity);
        recc.setAdapter(eas);
        recc.setNestedScrollingEnabled(true);

    }

    private void get_slider() {
        Api.getService().get_slider().enqueue(new Callback<Slider_Model>() {
            @Override
            public void onResponse(Call<Slider_Model> call, Response<Slider_Model> response) {
                progBarAds.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getInnerData().size()>0){
                        NUM_PAGES=response.body().getInnerData().size();
                        slidingImage__adapter = new SlidingImage_Adapter(activity,response.body().getInnerData());
                        mPager.setAdapter(slidingImage__adapter);
                        indicator.setupWithViewPager(mPager);
                    }
                    else {
                    //    error.setText("No data Found");
                       // recc.setVisibility(View.GONE);
                        mPager.setVisibility(View.GONE);
                    }
                }
                else if (response.code() == 404) {
                  //  error.setText(activity.getString(R.string.no_data));
                    //recc.setVisibility(View.GONE);
                    mPager.setVisibility(View.GONE);
                } else {
                   // recc.setVisibility(View.GONE);
                    mPager.setVisibility(View.GONE);
                    try {
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Slider_Model> call, Throwable t) {
                Log.e("Error", t.getMessage());
                progBarAds.setVisibility(View.GONE);
                //error.setText(activity.getString(R.string.faild));
                //recc.setVisibility(View.GONE);
                mPager.setVisibility(View.GONE);
            }
        });

    }

    public void categories() {

        Api.getService().getcateogries().enqueue(new Callback<Catogry_Model_Slide>() {
            @Override
            public void onResponse(Call<Catogry_Model_Slide> call, Response<Catogry_Model_Slide> response) {
                progBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response.body().getData()!=null&&response.body().getData().size() > 0) {
                        list.addAll(response.body().getData());
                        eas.notifyDataSetChanged();

                    } else {
                        error.setText(activity.getString(R.string.no_data));
                        recc.setVisibility(View.GONE);
                  //      mPager.setVisibility(View.GONE);
                    }

                    // Inflate the layout for this fragment
                } else if (response.code() == 404) {
                    error.setText(activity.getString(R.string.no_data));
                    recc.setVisibility(View.GONE);
                    //mPager.setVisibility(View.GONE);
                } else {
                    recc.setVisibility(View.GONE);
                    //mPager.setVisibility(View.GONE);
                    try {
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    error.setText(activity.getString(R.string.faild));

                }
            }

            @Override
            public void onFailure(Call<Catogry_Model_Slide> call, Throwable t) {

                Log.e("Error", t.getMessage());
                progBar.setVisibility(View.GONE);
                error.setText(activity.getString(R.string.faild));
                recc.setVisibility(View.GONE);
                //mPager.setVisibility(View.GONE);
            }
        });
    }


}
