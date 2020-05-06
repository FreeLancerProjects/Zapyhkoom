package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;


/**
 *
 */
public class Fragment_Terms_Conditions extends Fragment {
    private Preferences preferences;
    private ImageView back;
    private TextView terms_cond;
    private Home_Activity activity;
    final static private String terms="terms";
    public static Fragment_Terms_Conditions newInstance(String terms_condition) {

Fragment_Terms_Conditions fragment_terms_conditions=new Fragment_Terms_Conditions();
    Bundle bundle=new Bundle();
    bundle.putString(terms,terms_condition);
    fragment_terms_conditions.setArguments(bundle);
    return fragment_terms_conditions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms__conditions, container, false);
        intitview(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void intitview(View view) {
        terms_cond=view.findViewById(R.id.terms_txt);
        back = (ImageView) view.findViewById(R.id.back2);
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        if(getArguments().getString(terms)!=null){
        terms_cond.setText(getArguments().getString(terms));}
        if (preferences.getlang(activity).equals("en")) {

            back.setRotation(180);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });
    }


}
