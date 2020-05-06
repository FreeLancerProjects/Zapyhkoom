package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.creativeshare.zapyhakoom.Model.Setting_Model;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;


public class Fragment_Bank_Account extends Fragment {
    final static private String banks = "bank";
    private Preferences preferences;
    private ImageView back;
    private TextView bank_txt;
    private Home_Activity activity;
    private Setting_Model bank_account;
    private String bank_accounta[];
    private String bank_accountaa[];
    private String finalbankaccount = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_account, container, false);
        intitview(view);
        return view;
    }

    private void intitview(View view) {

        bank_txt = view.findViewById(R.id.bank_txt);
        back = (ImageView) view.findViewById(R.id.back_bank);
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        bank_account = (Setting_Model) getArguments().getSerializable(banks);
        if (preferences.getlang(activity).equals("en")) {

            back.setRotation(180);
        }
        if(bank_account!=null){

        if (preferences.getlang(activity).equals("ar")) {
            bank_txt.setText(bank_account.getInnerData().getBank_details());
        } else {
            bank_accounta = bank_account.getInnerData().getBank_details().split("\n");
            for (int i = 0; i < bank_accounta.length; i++) {
                bank_accountaa = bank_accounta[i].split(":");
                finalbankaccount+="bank account : " + bank_accountaa[1]+"\n";
            }
            bank_txt.setText(finalbankaccount);
        }}
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });


    }

    public static Fragment_Bank_Account newInstance(Setting_Model bank) {

        Fragment_Bank_Account fragment_bank_account = new Fragment_Bank_Account();
        Bundle bundle = new Bundle();
        bundle.putSerializable(banks, bank);
        fragment_bank_account.setArguments(bundle);
        return fragment_bank_account;
    }
}
