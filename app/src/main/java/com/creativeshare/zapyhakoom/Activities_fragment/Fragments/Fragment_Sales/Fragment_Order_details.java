package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.creativeshare.zapyhakoom.Model.Orders_Model;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Order_details extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView quantity, size, cutting, covering, kersh_mosran, details, total;
    private CircleImageView circleImageView;
    private Button user_details;
    private Orders_Model.InnerData orInnerData;
    final static String Tag = "orders";
    private Home_Activity activity;

    public static Fragment_Order_details newInstance(Orders_Model.InnerData innerData) {
        Fragment_Order_details fragment = new Fragment_Order_details();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag, innerData);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        intitview(view);
        return view;
    }

    private void intitview(View view) {
        activity = (Home_Activity) getActivity();
        quantity = view.findViewById(R.id.quantity);
        size = view.findViewById(R.id.size);
        cutting = view.findViewById(R.id.cutting);
        covering = view.findViewById(R.id.packing_buy);
        kersh_mosran = view.findViewById(R.id.Kersh);
        details = view.findViewById(R.id.details);
        total = view.findViewById(R.id.total);
        circleImageView = view.findViewById(R.id.img_product_details);
        user_details = view.findViewById(R.id.complete);
        orInnerData = (Orders_Model.InnerData) getArguments().getSerializable(Tag);
        quantity.setText(orInnerData.getQuantity() + "");
        size.setText(orInnerData.getproduct().getDescription().replaceAll("الحجم","").replaceAll("من",""));
        cutt();
        cover();
        details.setText(orInnerData.getDescription());
        total.setText(orInnerData.getOrder_total());
        Picasso.with(activity).load(Uri.parse(Tags.base_IMage_url+orInnerData.getproduct().getImage())).fit().into(circleImageView);
        if (orInnerData.getKersh_and_mosran().equals("0")) {
            kersh_mosran.setText(getString(R.string.without)+" " + getString(R.string.kersh_and_mosran));
        } else {
            kersh_mosran.setText(getString(R.string.kersh_and_mosran));

        }
        user_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.DisplayFragmentUserDetilas(orInnerData);
            }
        });

    }

    public void cutt() {
        String cut = "";
        if (orInnerData.getCutting().equals("0")) {
            cut = getString(R.string.fridge);
        } else if (orInnerData.getCutting().equals("1")) {
            cut = getString(R.string.quarter);
        } else if (orInnerData.getCutting().equals("2")) {
            cut = getString(R.string.half);
        } else if (orInnerData.getCutting().equals("3")) {
            cut = getString(R.string.alife);
        } else if (orInnerData.getCutting().equals("4")) {
            cut = getString(R.string.stand);
        }
        else if(orInnerData.getCutting().equals("5")){
            cut = getString(R.string.complete);
        }
        else if(orInnerData.getCutting().equals("6")){
            cut = getString(R.string.hadrmi1);


        } else if(orInnerData.getCutting().equals("7")){
            cut = getString(R.string.hadrmi2);

        }
        cutting.setText(cut + "");
    }

    public void cover() {
        String cover = "";
        if (orInnerData.getCovering().equals("0")) {
            cover = getString(R.string.without);
        } else if (orInnerData.getCovering().equals("1")) {
            cover = getString(R.string.plates);
        } else if (orInnerData.getCovering().equals("2")) {
            cover = getString(R.string.bags);
        }
        covering.setText(cover + "");
    }
}
