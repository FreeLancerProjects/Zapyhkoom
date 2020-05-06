package com.creativeshare.zapyhakoom.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Completed_Order;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Waiting_Order;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_ِBinding_Order;

public class User_Orders_Page_Adapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public User_Orders_Page_Adapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
               Fragment_ِBinding_Order tab1 = new Fragment_ِBinding_Order();
                return tab1;
            case 1:
              Fragment_Waiting_Order tab2 = new Fragment_Waiting_Order();
                return tab2;
            case 2:
                Fragment_Completed_Order tab3 = new Fragment_Completed_Order();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}