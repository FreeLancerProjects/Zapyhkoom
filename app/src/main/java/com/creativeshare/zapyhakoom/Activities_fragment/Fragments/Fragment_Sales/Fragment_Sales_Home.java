package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;


public class Fragment_Sales_Home extends Fragment {
    private AHBottomNavigation bottomNavigationView;
    private Preferences preferences;
    private Home_Activity activity;
    private TextView tv_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences = Preferences.getInstance();
        activity = (Home_Activity) getActivity();

        View view = inflater.inflate(R.layout.fragment_sales__home, container, false);
        tv_title = view.findViewById(R.id.tv_title);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        setUpBottomNav();
        return view;
    }

    public static Fragment_Sales_Home newInstance() {

        return new Fragment_Sales_Home();
    }

    public void setUpBottomNav() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.needs, R.drawable.ic_needs, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.accountِ, R.drawable.ic_user, R.color.colorPrimary);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.more, R.drawable.ic_more_circular_button_interface_symbol_of_three_horizontal_dots, R.color.colorPrimary);

        bottomNavigationView.addItem(item1);
        bottomNavigationView.addItem(item2);
        bottomNavigationView.addItem(item5);
        bottomNavigationView.setAccentColor(ContextCompat.getColor(activity, R.color.white));
        bottomNavigationView.setDefaultBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        bottomNavigationView.setInactiveColor(ContextCompat.getColor(activity, R.color.white));
        bottomNavigationView.setForceTint(true);
        bottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigationView.setColored(false);

        bottomNavigationView.setCurrentItem(0);
        bottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        activity.DisplayFragmentSalesOrder();
                        break;
                    case 1:
                        activity.DisplayFragmentSales_profile();
                        break;
                    case 2:
                        activity.DisplayFragmentSalesmore();
                        break;

                }
                return false;
            }
        });

    }

    public void UpdateAHBottomNavigationPosition(int pos) {

        if (pos == 0) {
            tv_title.setText(getString(R.string.needs));
        } else if (pos == 1) {
            tv_title.setText(getString(R.string.accountِ));

        } else if (pos == 2) {
            tv_title.setText(getString(R.string.more));

        }
        bottomNavigationView.setSelectedBackgroundVisible(true);

        bottomNavigationView.setCurrentItem(pos, false);
    }

}
