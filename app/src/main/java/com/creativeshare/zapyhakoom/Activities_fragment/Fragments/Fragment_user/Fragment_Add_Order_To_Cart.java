package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.creativeshare.zapyhakoom.Model.Orders_Cart_Model;
import com.creativeshare.zapyhakoom.Model.PlaceGeocodeData;
import com.creativeshare.zapyhakoom.Model.PlaceMapDetailsData;
import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 */
public class Fragment_Add_Order_To_Cart extends Fragment implements OnMapReadyCallback , LocationListener {
    private List<com.creativeshare.zapyhakoom.Model.Orders_Cart_Model> buy_modelArrayList;
    private Orders_Cart_Model Orders_Cart_Model;
    private static PlaceMapDetailsData placeMapDetailsData;
    private final static String Tag = "Orders_Cart_Model";
    private final static String Tag1 = "lat";
    private final static String Tag2 = "lng";

    private String current_language;
    private Preferences preferences;
    private Home_Activity activity;
    private double lat , lng ;
    private GoogleMap mMap;
    private Marker marker;
    private float zoom = 15.6f;
    private EditText address, phone;
    private String address_path;
    private Button complete;
    private ImageView back;
    private boolean isgetaddress = false,changeaddress=false;
    public LocationManager locationManager;
    public int per=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_order_to_cart, container, false);
        updtae_map();
        intitview(view);
        check_permission();


        return view;
    }

    private void updtae_map() {
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    private void intitview(final View view) {
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        current_language = preferences.getlang(activity);
        complete = view.findViewById(R.id.com);
        phone = view.findViewById(R.id.phone_complete_order);
        address = view.findViewById(R.id.address);
        back = view.findViewById(R.id.back_complete);
        Orders_Cart_Model = (Orders_Cart_Model) getArguments().getSerializable(Tag);
//        lat=getArguments().getDouble(Tag1);
//        lng=getArguments().getDouble(Tag2);

        if(preferences.getUserData(activity)!=null){
        phone.setText(preferences.getUserData(activity).getData().getMobile());}
        getgecode(lat,lng);
        //gpsisopen=getArguments().getBoolean(Tag2);
        buy_modelArrayList = preferences.getUserOrder(activity);

        if (preferences.getlang(activity).equals("en")) {
            back.setRotation(180);
        }
        if (buy_modelArrayList == null) {
            buy_modelArrayList = new ArrayList<>();
        }
        address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = address.getText().toString();

                    if (!TextUtils.isEmpty(query)) {
                        Search(query);
                        Common.CloseKeyBoard(activity,view);
                        return true;
                    }
                }

                return false;
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.getText().toString().isEmpty()) {
                    address.setError("");
                } else if (isgetaddress == false) {
                    Common.CreateSignAlertDialog(activity, activity.getString(R.string.please_enter_address));
                }  else if (phone.getText().toString().isEmpty()) {
                    phone.setError("");

                } else {
                    Common.CloseKeyBoard(activity, view);

                    Orders_Cart_Model.setMobile(phone.getText().toString());
                    if(changeaddress==true) {
                        Orders_Cart_Model.setLatitude(placeMapDetailsData.getCandidates().get(0).getGeometry().getLocation().getLat() + "");
                        Orders_Cart_Model.setLongitude(placeMapDetailsData.getCandidates().get(0).getGeometry().getLocation().getLng() + "");
                        Orders_Cart_Model.setAddress(placeMapDetailsData.getCandidates().get(0).getFormatted_address());
                    }
                    else{
                        Orders_Cart_Model.setLatitude(lat+"");
                        Orders_Cart_Model.setLongitude(lng+"");
                        Orders_Cart_Model.setAddress(address.getText().toString());

                    }
                    buy_modelArrayList.add(Orders_Cart_Model);
                    preferences.create_update_order(activity, buy_modelArrayList);
                    activity.Back();
                    activity.Back();
                    activity.Back();
                    activity.display_Cart();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
activity.Back();            }
        });
    }
public void getgecode(final double lat, final double lng){

        String latlng=lat+","+lng;
        Api.getService("https://maps.googleapis.com/maps/api/").getGeoData(latlng,current_language,activity.getString(R.string.map_api_key)).enqueue(new Callback<PlaceGeocodeData>() {
            @Override
            public void onResponse(Call<PlaceGeocodeData> call, Response<PlaceGeocodeData> response) {
                if(response.isSuccessful()){
                    if(response.body().getResults().size()>0){
                        isgetaddress=true;
                        address.setText(response.body().getResults().get(0).getFormatted_address());
                        AddMarker(lat, lng,false);
                    }

                }
                else {
                    Log.e("Error code",response.code()+response.message()+""+response.errorBody()+"");
                }
            }

            @Override
            public void onFailure(Call<PlaceGeocodeData> call, Throwable t) {

            }
        });
}
  /*  private void getdevicelocation() {
mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(activity);
try {
    Task task=mFusedLocationProviderClient.getLastLocation();
    task.addOnCompleteListener(new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
if(task.isSuccessful()){
    Location currentlocation= (Location) task.getResult();
  place1=new MarkerOptions().position(new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude())).title(currentlocation.getProvider());
}
        }
    });
}catch (SecurityException e){
    Log.e("Errror","SecurityException"+e.getMessage());
}
    }*/

    public static Fragment_Add_Order_To_Cart newInstance(Orders_Cart_Model orders_Cart_Model) {
        Fragment_Add_Order_To_Cart fragment_add_orderCart = new Fragment_Add_Order_To_Cart();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag, orders_Cart_Model);
//        bundle.putDouble(Tag1,lat);
//        bundle.putDouble(Tag2,lang);
        fragment_add_orderCart.setArguments(bundle);
        return fragment_add_orderCart;
    }

    private void Search(String query) {

        // image_pin.setVisibility(View.GONE);
        //progBar.setVisibility(View.VISIBLE);
        String fields = "id,place_id,name,geometry,formatted_address";
        Api.getService("https://maps.googleapis.com/maps/api/")
                .searchOnMap("textquery", query, fields, current_language, getString(R.string.map_api_key))
                .enqueue(new Callback<PlaceMapDetailsData>() {
                    @Override
                    public void onResponse(Call<PlaceMapDetailsData> call, Response<PlaceMapDetailsData> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            /*image_pin.setVisibility(View.VISIBLE);
                            progBar.setVisibility(View.GONE);
*/
                            Fragment_Add_Order_To_Cart.placeMapDetailsData = response.body();

                            if (response.body().getCandidates().size() > 0) {
                                isgetaddress = true;
                                changeaddress=true;
                                address_path = response.body().getCandidates().get(0).getFormatted_address().replace("Unnamed Road,","");
                                //place_id = response.body().getCandidates().get(0).getPlace_id();

                                AddMarker(response.body().getCandidates().get(0).getGeometry().getLocation().getLat(), response.body().getCandidates().get(0).getGeometry().getLocation().getLng(), false);
                            }
                        } else {


                            try {
                                Log.e("error_code", response.code()+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<PlaceMapDetailsData> call, Throwable t) {
                        try {


                           Log.e("Error",t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void AddMarker(final double lat, final double lng, boolean isMove) {

        this.lat = lat;
        this.lng = lng;

        if (marker == null) {
            IconGenerator iconGenerator = new IconGenerator(activity);
            iconGenerator.setBackground(null);
             View view = LayoutInflater.from(activity).inflate(R.layout.map_icon, null);
              iconGenerator.setContentView(view);
              ImageView im=view.findViewById(R.id.mapicon);
              im.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
                      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                      activity.startActivity(intent);
                  }
              });
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())).anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV()));
           if(changeaddress==true){
            marker.setTitle(address_path);}
           else {
               marker.setTitle(address.getText().toString());
           }
            marker.showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));
        } else {
            if(changeaddress==true){
                marker.setTitle(address_path);}
            else {
                marker.setTitle(address.getText().toString());
            }            marker.showInfoWindow();
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
    }

    public void check_permission(){
        if(ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},102);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 102 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED) {
            get_location();
        }
    }
    public void get_location(){
        try {
            locationManager=(LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,activity);


        }
        catch (SecurityException e){
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        get_location();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(activity);    }

    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lng=location.getLongitude();
        address_path=location.toString();
        AddMarker(lat,lng,false);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        buildAlertMessageNoGps();
    }

    public int buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        per=1;
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 33);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        per=0;
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
        return per;
    }

}
