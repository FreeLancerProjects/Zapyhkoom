package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;

import android.graphics.PorterDuff;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.zapyhakoom.Adapters.Product_Adapter;
import com.creativeshare.zapyhakoom.Adapters.Product_Offers_Adapter;
import com.creativeshare.zapyhakoom.Model.Model_Offr_Product;
import com.creativeshare.zapyhakoom.Model.Product_Model;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 */
public class Fragment_Product extends Fragment {

    private Home_Activity activity;
    private Product_Model model;

    private ArrayList<Product_Model.InnerData> list;
    private ArrayList<Model_Offr_Product.InnerData.Prods> list2;
    private ProgressBar progressBar;
    private RecyclerView products;
    private TextView error, product_txt;
    private ImageView img2;
    private int Product_id, current = 1, param;
    private Preferences preferences;
    private Product_Adapter product_adapter = null;
    private Product_Offers_Adapter product_offers_adapter = null;
    private static final String Tag = "param";
    private static final String Tag2 = "product_id";
    private static final String Tag3 = "name";

    private boolean isloading = true;
    private String name;

    public static Fragment_Product newInstance(int product_id, String name, int param) {
        Fragment_Product fragment_details = new Fragment_Product();
        Bundle bundle = new Bundle();
        bundle.putInt(Tag, param);
        bundle.putInt(Tag2, product_id);
        bundle.putString(Tag3,name);
        fragment_details.setArguments(bundle);
        return fragment_details;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        intitview(view);

        if (param == 2) {
            // product_txt.setText();
            list=new ArrayList<>();
            products.setVisibility(View.VISIBLE);
            product_adapter = new Product_Adapter(list, activity);
            products.setLayoutManager(new GridLayoutManager(activity, 1));
            products.setAdapter(product_adapter);
            getproductbycatogry(Product_id);


        } else if (param == 1) {
            list2=new ArrayList<>();
            products.setVisibility(View.VISIBLE);
            product_offers_adapter = new Product_Offers_Adapter(list2, activity);
            products.setLayoutManager(new GridLayoutManager(activity, 1));
            products.setAdapter(product_offers_adapter);
            getproduct_offer(Product_id);
        }
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void intitview(View view) {
        Product_id = getArguments().getInt(Tag2);
        param = getArguments().getInt(Tag);
        activity = (Home_Activity) getActivity();
        error = view.findViewById(R.id.error_product);
        product_txt = view.findViewById(R.id.product_txt);
        progressBar = (ProgressBar) view.findViewById(R.id.progBar3);
        products = (RecyclerView) view.findViewById(R.id.types);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        products.setItemViewCacheSize(25);
        products.setDrawingCacheEnabled(true);
        products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        img2 = (ImageView) view.findViewById(R.id.img_pro);
        name=getArguments().getString(Tag3);
        product_txt.setText(name);
        preferences = Preferences.getInstance();
        if (preferences.getlang(activity).equals("en")) {
            img2.setRotation(180);
        }
    }

    private void getproduct_offer(int product_id) {
        Api.getService().get_product_offrid(Product_id).enqueue(new Callback<Model_Offr_Product>() {
            @Override
            public void onResponse(Call<Model_Offr_Product> call, Response<Model_Offr_Product> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response.body().getInnerData().getData()!=null&&response.body().getInnerData().getData().size() > 0) {
                        list2.addAll(response.body().getInnerData().getData());
                        product_offers_adapter.notifyDataSetChanged();

                    } else {
                        error.setText("No data Found");
                        products.setVisibility(View.GONE);
                    }
                } else if (response.code() == 404) {
                    error.setText("No data Found");
                    products.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Model_Offr_Product> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    public void getproductbycatogry(int product_id) {
        Api.getService().get_product(product_id).enqueue(new Callback<Product_Model>() {
            @Override
            public void onResponse(Call<Product_Model> call, Response<Product_Model> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {


                    if (response.body().getData()!=null&&response.body().getData().size() > 0) {
                        list.addAll(response.body().getData());
                        product_adapter.notifyDataSetChanged();

                    } else {
                        error.setText(activity.getString(R.string.no_data));
                        error.setVisibility(View.VISIBLE);
                        products.setVisibility(View.GONE);
                    }

                } else if (response.code() == 404) {
                    error.setText(activity.getString(R.string.no_data));
                    error.setVisibility(View.VISIBLE);
                    products.setVisibility(View.GONE);
                    Log.e("Error_code", response.code() + "_" + response.errorBody());

                }
                else {
                    Log.e("Error_code", response.code() + "_" + response.errorBody());
                    error.setText(activity.getString(R.string.faild));

                }
            }

            @Override
            public void onFailure(Call<Product_Model> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                error.setText(activity.getString(R.string.faild));
                Log.e("Error_code", t.getMessage());

            }
        });
    }


}
