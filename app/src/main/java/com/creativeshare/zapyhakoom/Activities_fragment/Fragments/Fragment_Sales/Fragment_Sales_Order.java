package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativeshare.zapyhakoom.Adapters.Sales_Order_page_Adapter;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;
import com.google.android.material.tabs.TabLayout;


public class Fragment_Sales_Order extends Fragment {
    private TabLayout tabLayout;
    private Home_Activity activity;
    private ViewPager viewPager;
    private Sales_Order_page_Adapter adapter;

    public static Fragment_Sales_Order newInstance() {
        return new Fragment_Sales_Order();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales__order, container, false);

        intitview(view);

        return view;


    }

    private void intitview(View view) {
        activity = (Home_Activity) getActivity();
        tabLayout = view.findViewById(R.id.tab_orders_sals);
        viewPager = view.findViewById(R.id.pager_sales);
        adapter = new Sales_Order_page_Adapter(activity.getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}