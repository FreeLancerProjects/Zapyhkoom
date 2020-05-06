package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.creativeshare.zapyhakoom.Model.Orders_Model;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.Locale;


public class Fragment_User_Details extends Fragment implements OnMapReadyCallback {

    private TextView phone, address;
    private Button ok;
    private Preferences preferences;
    private Home_Activity activity;
    private double lat = 0.0, lng = 0.0;
    private GoogleMap mMap;
    private Marker marker;
    private float zoom = 15.6f;
    private Orders_Model.InnerData oInnerData;
    final static String Tag = "orders";

    // TODO: Rename and change types and number of parameters
    public static Fragment_User_Details newInstance(Orders_Model.InnerData oInnerData) {
        Fragment_User_Details fragment = new Fragment_User_Details();
        Bundle bundle = new Bundle();
       bundle.putSerializable(Tag,oInnerData);
        fragment.setArguments(bundle);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user__details, container, false);
        intitview(view);
        updtae_map();

        return view;
    }

    private void intitview(View view) {
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        phone=view.findViewById(R.id.phone_complete_order);
        address=view.findViewById(R.id.address);
        ok=view.findViewById(R.id.com);
        oInnerData= (Orders_Model.InnerData) getArguments().getSerializable(Tag);
        phone.setText(oInnerData.getMobile());
        address.setText(oInnerData.getAddress());
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
                activity.Back();
            }
        });

    }

    private void updtae_map() {
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

    }

    private void AddMarker(final double lat, final double lng, boolean isMove) {

        this.lat = lat;
        this.lng = lng;
        if (marker == null) {
            IconGenerator iconGenerator = new IconGenerator(activity);
            iconGenerator.setBackground(null);
            View view = LayoutInflater.from(activity).inflate(R.layout.map_icon, null);
            iconGenerator.setContentView(view);
            ImageView im = view.findViewById(R.id.mapicon);
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    activity.startActivity(intent);
                }
            });
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())).anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV()));
            marker.setTitle(oInnerData.getAddress());
            marker.showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));
        } else {
            marker.setTitle(oInnerData.getAddress());
            marker.showInfoWindow();
            marker.setPosition(new LatLng(lat, lng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));


        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.maps));
        mMap.setTrafficEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(true);
        AddMarker(Double.parseDouble(oInnerData.getLatitude()+""),Double.parseDouble(oInnerData.getLongitude()+""),false);

    }
}
