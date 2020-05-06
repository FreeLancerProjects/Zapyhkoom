package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;

import android.graphics.PorterDuff;
import android.os.Bundle;


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

import com.creativeshare.zapyhakoom.Adapters.Offers_Adapter;
import com.creativeshare.zapyhakoom.Model.Offers_Model;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 */
public class Fragment_Offers extends Fragment {
    private RecyclerView offers_Rececyle_View;
    private List<Offers_Model.InnerData> list;
    private Offers_Adapter offers_Adapter_;
    private Home_Activity activity;
    private int page_number = 1;
    private ProgressBar progressBar;
    private boolean isloading = true;
    private TextView error;

    public static Fragment_Offers newInstance() {
        return new Fragment_Offers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (Home_Activity) getActivity();

        View view = inflater.inflate(R.layout.fragment_offres, container, false);
        intitview(view);


        // Inflate the layout for this fragment
        return view;
    }

    private void intitview(View view) {
        list=new ArrayList<>();
        error = view.findViewById(R.id.error_offers);
        progressBar = (ProgressBar) view.findViewById(R.id.progBar2);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        offers_Rececyle_View = (RecyclerView) view.findViewById(R.id.offfr);
        offers_Rececyle_View.setItemViewCacheSize(25);
        offers_Rececyle_View.setDrawingCacheEnabled(true);
        offers_Rececyle_View.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        offers_Rececyle_View.setVisibility(View.VISIBLE);
        offers_Adapter_ = new Offers_Adapter(list, activity);

        offers_Rececyle_View.setLayoutManager(new GridLayoutManager(activity, 1));
        offers_Rececyle_View.setAdapter(offers_Adapter_);
        get_offrs();
    }



    public void get_offrs(){
        Api.getService().getoffrs().enqueue(new Callback<Offers_Model>() {
            @Override
            public void onResponse(Call<Offers_Model> call, Response<Offers_Model> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {


                    if (response.body().getData()!=null&&response.body().getData().size() > 0) {
                        list.addAll(response.body().getData());
                        offers_Adapter_.notifyDataSetChanged();

                    }
                 else {
                            error.setText(activity.getString(R.string.no_data));
                            offers_Rececyle_View.setVisibility(View.GONE);
                        }

                } else if (response.code() == 404) {
                 error.setText(activity.getString(R.string.no_data));
                offers_Rececyle_View.setVisibility(View.GONE);
                }
                else {
                    try {
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    error.setText(activity.getString(R.string.faild));

                }
            }

            @Override
            public void onFailure(Call<Offers_Model> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                error.setText(activity.getString(R.string.faild));

                Log.e("Error_code", t.getMessage());

            }
        });
    }

}
