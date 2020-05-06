package com.creativeshare.zapyhakoom.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales.Fragment_Sales_Completed_Order;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales.Fragment_Sales_Waiting_Order;

public class Sales_Order_page_Adapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public Sales_Order_page_Adapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_Sales_Waiting_Order tab1 = new Fragment_Sales_Waiting_Order();
                return tab1;
            case 1:
                Fragment_Sales_Completed_Order tab2 = new Fragment_Sales_Completed_Order();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}