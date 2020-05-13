package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creativeshare.zapyhakoom.Adapters.Cart_Adpter;
import com.creativeshare.zapyhakoom.Model.Item_Cart_Model;
import com.creativeshare.zapyhakoom.Model.Orders_Cart_Model;
import com.creativeshare.zapyhakoom.Model.Orders_Model;
import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class Fragment_Cart extends Fragment {
    private RecyclerView cart;
    private Button continue_order, back;
    private LinearLayout linearLayout;
    private TextView error;
    private Cart_Adpter cart_adpter;
    private Item_Cart_Model item_cart_model;
    private List<Orders_Cart_Model> order;
    private Preferences preferences;
    private Home_Activity activity;

    public static Fragment_Cart newInstance() {
        return new Fragment_Cart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        intitview(view);
        return view;
    }

    private void intitview(View view) {
        item_cart_model = new Item_Cart_Model();
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        linearLayout = view.findViewById(R.id.lin_cart);
        error = view.findViewById(R.id.error_cart);
        cart = view.findViewById(R.id.cart_data);

        continue_order = view.findViewById(R.id.Continue_order);
        back = view.findViewById(R.id.back_cart);
        order = preferences.getUserOrder(activity);
       cart.setItemViewCacheSize(25);
       cart.setDrawingCacheEnabled(true);
       cart.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        if (order != null) {

            cart_adpter = new Cart_Adpter(order, activity);
            cart.setLayoutManager(new GridLayoutManager(activity, 1));
            cart.setAdapter(cart_adpter);
            continue_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (preferences.getUserData(activity) == null) {
                        Common.CreateUserNotSignInAlertDialog(activity);
                    } else {
                        if (order.get(0).getUser_id() == 0) {

                            for (int i = 0; i < order.size(); i++) {
                                order.get(i).setUser_id(preferences.getUserData(activity).getData().getId());
                            }
                        }
                        item_cart_model.setOrders(order);

                        accept_order();
                    }
                }
            });
        }
        else {
            error.setText(activity.getString(R.string.no_data));
            linearLayout.setVisibility(View.GONE);
            cart.setVisibility(View.GONE);
back.setVisibility(View.GONE);
continue_order.setVisibility(View.GONE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });
    }

    private void accept_order() {


        Api.getService().accept_orders(item_cart_model).enqueue(new Callback<Orders_Model>() {
            @Override
            public void onResponse(Call<Orders_Model> call, Response<Orders_Model> response) {


                if (response.isSuccessful()) {
                   Accept();
                } else {

                    Log.e("Error code", response.code() + "" + response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<Orders_Model> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });
    }

    private void Accept() {
        error.setText(activity.getString(R.string.no_data));
        linearLayout.setVisibility(View.GONE);
        cart.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        continue_order.setVisibility(View.GONE);
        order.removeAll(order);
        cart_adpter.notifyDataSetChanged();
        preferences.create_update_order(activity, null);
        Common.CreateSignAlertDialog(activity, getResources().getString(R.string.sucess));
        activity.Back();
        activity.DisplayFragmentOrders();
    }


}
