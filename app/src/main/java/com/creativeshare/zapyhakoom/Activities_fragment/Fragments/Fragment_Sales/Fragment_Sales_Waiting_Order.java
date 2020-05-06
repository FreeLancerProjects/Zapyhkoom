package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.creativeshare.zapyhakoom.Adapters.Orders_Adpter;
import com.creativeshare.zapyhakoom.Model.Orders_Model;
import com.creativeshare.zapyhakoom.Model.UserModel;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Sales_Waiting_Order extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Preferences preferences;
    private List<Orders_Model.InnerData> Orders;
    private ProgressBar progBar;
    private Home_Activity activity;
    private Orders_Adpter order_adapter;
    private RecyclerView Orders_Recycle_View;
    private TextView error;
    private UserModel user_model;
    public static Fragment_Sales_Waiting_Order newInstance() {
       return new Fragment_Sales_Waiting_Order();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sales__waiting__order, container, false);
        intitview(view);
        return view;
    }
    private void intitview(View view) {
        Orders=new ArrayList<>();
        preferences = Preferences.getInstance();
        activity = (Home_Activity) getActivity();
        user_model = preferences.getUserData(activity);
        error = view.findViewById(R.id.error_sales_wait);
        Orders_Recycle_View = view.findViewById(R.id.salesorders_wait);
        progBar =  view.findViewById(R.id.progBar_sales_wait);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        Orders_Recycle_View.setItemViewCacheSize(25);
        Orders_Recycle_View.setDrawingCacheEnabled(true);
        Orders_Recycle_View.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        order_adapter = new Orders_Adpter(Orders, activity, getResources().getString(R.string.Currency));

        Orders_Recycle_View.setLayoutManager(new GridLayoutManager(activity, 1));
        Orders_Recycle_View.setAdapter(order_adapter);
        get_orders( user_model);

    }


    public void get_orders( UserModel user_model) {
        Api.getService().getsales_order(user_model.getData().getId(),1).enqueue(new Callback<Orders_Model>() {
            @Override
            public void onResponse(Call<Orders_Model> call, Response<Orders_Model> response) {
                progBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response.body().getData()!=null&&response.body().getData().size() > 0) {
                        Orders.addAll(response.body().getData());
                        order_adapter.notifyDataSetChanged();

                    } else {
                        error.setText(activity.getString(R.string.no_data));
                        Orders_Recycle_View.setVisibility(View.GONE);
                    }

                } else if (response.code() == 404) {
                    error.setText(activity.getString(R.string.no_data));
                    Orders_Recycle_View.setVisibility(View.GONE);
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
            public void onFailure(Call<Orders_Model> call, Throwable t) {
                progBar.setVisibility(View.GONE);
                error.setText(activity.getString(R.string.faild));

                Log.e("Error_code", t.getMessage());

            }
        });
    }
}
