package com.creativeshare.zapyhakoom.Activities_fragment.Activites;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales.Fragment_Order_details;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales.Fragment_Sales_Home;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales.Fragment_Sales_More;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales.Fragment_Sales_Order;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales.Fragment_Sales_Profile;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_Sales.Fragment_User_Details;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Add_Order_To_Cart;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Bank_Account;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Cart;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Completed_Order;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Contact_US;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Home;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Main;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_More;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Offers;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Orders;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Product;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Product_Details;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Profile;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Terms_Conditions;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_Waiting_Order;
import com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user.Fragment_ِBinding_Order;
import com.creativeshare.zapyhakoom.Adapters.Orders_Adpter;
import com.creativeshare.zapyhakoom.Language.Language;
import com.creativeshare.zapyhakoom.Model.Data_Model;
import com.creativeshare.zapyhakoom.Model.Model_Offr_Product;
import com.creativeshare.zapyhakoom.Model.Orders_Cart_Model;
import com.creativeshare.zapyhakoom.Model.Orders_Model;
import com.creativeshare.zapyhakoom.Model.Product_Model;
import com.creativeshare.zapyhakoom.Model.Setting_Model;
import com.creativeshare.zapyhakoom.Model.UserModel;
import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.remote.Api;
import com.creativeshare.zapyhakoom.R;

import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Activity extends AppCompatActivity  implements LocationListener {
    int fragment_count = 0;
    private FragmentManager fragmentManager;
    private Fragment_Sales_Home fragment_sales_home;
    private Fragment_Sales_Order fragment_sales_order;
    private Fragment_Sales_Profile fragment_sales_profile;
    private Fragment_Sales_More fragment_sales_more;
    private Fragment_Order_details fragment_order_details;
    private Fragment_User_Details fragment_user_details;
    private Fragment_Home fragment_home;
    private Fragment_Main fragment_main;
    private Fragment_Orders fragment_orders;
    private Fragment_Offers fragment_offers;
    private Fragment_Profile fragment_profile;
    private Fragment_More fragment_more;
    private Fragment_Product fragment_product;
    private Fragment_Add_Order_To_Cart fragmentCompleteOrder;
    private Fragment_Contact_US fragmentContactUS;
    private Fragment_Terms_Conditions fragmentTerms_conditions;
    private Fragment_Bank_Account fragment_bank_account;
    private Fragment_Product_Details fragmentProductDetails;
    private Fragment_Cart fragment_cart;
    //private Fragment_Sales_Order fragment_sales_order;
    //private Fragment_User_Address fragment_user_address;
    private Fragment_Completed_Order fragment_completed_order;
    private Fragment_Waiting_Order fragment_waiting_order;
    private Fragment_ِBinding_Order fragment_ِBinding_order;
    private Preferences preferences;
    private UserModel userModel;
    private String  user_type,address;
    public String param = "";
    public double lang,lat;
   public LocationManager locationManager;
    public int per=0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__main);
        initView();
        if (savedInstanceState == null) {

            // CheckPermission();
            fragmentManager = this.getSupportFragmentManager();
            if (userModel != null) {
                if (user_type.equals("user")) {

                    DisplayFragmentHome();
                    DisplayFragmentMain();
                } else if (user_type.equals("sales")) {
                    //DisplayFragment_Sales_Order();
                    DisplayFragmentSalesHome();
                    DisplayFragmentSalesOrder();
                }
            } else {
                DisplayFragmentHome();
                DisplayFragmentMain();
            }
        }


    }

    public void DisplayFragmentSalesHome() {
        fragment_count += 1;

        if (fragment_sales_home == null) {
            fragment_sales_home = Fragment_Sales_Home.newInstance();
        }

        if (fragment_sales_home.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sales_home).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_sales_home, "fragment_sales_home").addToBackStack("fragment_sales_home").commit();
        }
    }

    public void DisplayFragmentSalesOrder() {
        if (fragment_sales_profile != null && fragment_sales_profile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sales_profile).commit();

        }
        if (fragment_sales_more != null && fragment_sales_more.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sales_more).commit();

        }
        if (fragment_sales_order == null) {
            fragment_sales_order = Fragment_Sales_Order.newInstance();
        }
        if (fragment_sales_order.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sales_order).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sales_child, fragment_sales_order, "fragment_sales_order").addToBackStack("fragment_sales_order").commit();
        }
        if (fragment_sales_home != null && fragment_sales_home.isAdded()) {
            fragment_sales_home.UpdateAHBottomNavigationPosition(0);
        }
    }

    public void DisplayFragmentSales_profile() {
        if (fragment_sales_order != null && fragment_sales_order.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sales_order).commit();

        }
        if (fragment_sales_more != null && fragment_sales_more.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sales_more).commit();

        }
        if (fragment_sales_profile == null) {
            fragment_sales_profile = Fragment_Sales_Profile.newInstance();
        }
        if (fragment_sales_profile.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sales_profile).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sales_child, fragment_sales_profile, "fragment_sales_profile").addToBackStack("fragment_sales_profile").commit();
        }
        if (fragment_sales_home != null && fragment_sales_home.isAdded()) {
            fragment_sales_home.UpdateAHBottomNavigationPosition(1);
        }
    }

    public void DisplayFragmentSalesmore() {
        if (fragment_sales_profile != null && fragment_sales_profile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sales_profile).commit();

        }
        if (fragment_sales_order != null && fragment_sales_order.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_sales_order).commit();

        }
        if (fragment_sales_more == null) {
            fragment_sales_more = Fragment_Sales_More.newInstance();
        }
        if (fragment_sales_more.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sales_more).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sales_child, fragment_sales_more, "fragment_sales_more").addToBackStack("fragment_sales_more").commit();
        }
        if (fragment_sales_home != null && fragment_sales_home.isAdded()) {
            fragment_sales_home.UpdateAHBottomNavigationPosition(0);
        }
    }

    public void DisplayFragmentOrderDetails(Orders_Model.InnerData oInnerData) {
        fragment_order_details = Fragment_Order_details.newInstance(oInnerData);
        fragment_count += 1;
        if (fragment_order_details.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_order_details).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_order_details, "fragment_order_details").addToBackStack("fragment_order_details").commit();

        }

    }

    public void DisplayFragmentUserDetilas(Orders_Model.InnerData oInnerData) {
        fragment_count += 1;
        fragment_user_details = Fragment_User_Details.newInstance(oInnerData);
        if (fragment_user_details.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_user_details).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_user_details, "fragment_user_details").addToBackStack("fragment_user_details").commit();

        }
    }

    /*private void CheckPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, gps_perm) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{gps_perm}, gps_req);
        } else {

            if (isGpsOpen())
            {
               gpsisopen=true; //StartService(LocationRequest.PRIORITY_HIGH_ACCURACY);
            }else
            {
                CreateGpsDialog();

            }
        }
    }
  /*  private void StartService(int accuracy)
    {
        if (dialog == null)
        {
            dialog = Common.createProgressDialog(this,getString(R.string.fetching_your_location));
            dialog.setCancelable(true);
            dialog.show();



        }
        intentService = new Intent(this, UpdateLocationService.class);
        intentService.putExtra("accuracy",accuracy);
        startService(intentService);
    }
    public void DismissDialog()
    {
        if (dialog!=null)
        {
            dialog.dismiss();
        }

    }
    private boolean isGpsOpen()
    {
        boolean isOpened = false;
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager != null) {
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)||manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                isOpened = true;
            }
        }

        return isOpened;
    }
    private void OpenGps()
    {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 33);
    }
    private void CreateGpsDialog()
    {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .create();

        View view = LayoutInflater.from(this).inflate(R.layout.gps_dialog,null);
        Button btn_allow = view.findViewById(R.id.btn_allow);
        Button btn_deny = view.findViewById(R.id.btn_deny);

        btn_allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                OpenGps();
                gpsisopen=true;
            }
        });
        btn_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


       // dialog.getWindow().getAttributes().windowAnimations=R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
       // dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setView(view);
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }


        if (requestCode == 33) {
            if (isGpsOpen()) {
               // StartService(LocationRequest.PRIORITY_LOW_POWER);
            } else {
                CreateGpsDialog();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (requestCode == gps_req && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (isGpsOpen())
            {
                gpsisopen=true;
              //  StartService(LocationRequest.PRIORITY_HIGH_ACCURACY);
            }else
            {
                CreateGpsDialog();
            }
        }
    }*/
    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        if (userModel != null) {
            user_type = userModel.getData().getRole();
        }
    }

   /* private void DisplayFragment_Sales_Order() {
        fragment_count += 1;

        if (fragment_sales_order == null) {
            fragment_sales_order = Fragment_Sales_Order.newInstance();
        }

        if (fragment_sales_order.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sales_order).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_sales_order, "fragment_sales_order").addToBackStack("fragment_sales_order").commit();
        }
    }

    public void display_user_address(Orders_Model.InnerData model) {

        fragment_count += 1;

        if (fragment_user_address == null) {
            fragment_user_address = Fragment_User_Address.newInstance();
        }

        if (fragment_user_address.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_user_address).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_user_address, "fragment_user_address").addToBackStack("fragment_user_address").commit();
        }
    }*/

    public void DisplayFragmentHome() {

        fragment_count += 1;

        if (fragment_home == null) {
            fragment_home = Fragment_Home.newInstance();
        }

        if (fragment_home.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_home).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_home, "fragment_home").addToBackStack("fragment_home").commit();
        }

    }

    public void DisplayFragmentMain() {

        if (fragment_orders != null && fragment_orders.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }

        if (fragment_offers != null && fragment_offers.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_offers).commit();
        }
        if (fragment_profile != null && fragment_profile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }
        if (fragment_more != null && fragment_more.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_more).commit();
        }

        if (fragment_main == null) {
            fragment_main = Fragment_Main.newInstance();
        }

        if (fragment_main.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_main).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_main, "fragment_main").addToBackStack("fragment_main").commit();

        }
        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.UpdateAHBottomNavigationPosition(0);
        }

    }

    public void DisplayFragmentOrders() {

        if (userModel == null) {
            Common.CreateUserNotSignInAlertDialog(this);

        } else {
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }

            if (fragment_offers != null && fragment_offers.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_offers).commit();
            }
            if (fragment_profile != null && fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_profile).commit();
            }
            if (fragment_more != null && fragment_more.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_more).commit();
            }

            if (fragment_orders == null) {
                fragment_orders = Fragment_Orders.newInstance();
            }

            if (fragment_orders.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_orders).commit();
                fragment_orders.setpage(0);

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_orders, "fragment_orders").addToBackStack("fragment_orders").commit();

            }

            if (fragment_home != null && fragment_home.isAdded()) {
                fragment_home.UpdateAHBottomNavigationPosition(1);
            }

        }


    }
    /*public void DisplayFragmentWaitingOrder(){
        if(fragment_completed_order!=null&&fragment_completed_order.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_completed_order).commit();
        }
        if(fragment_ِBinding_order !=null&& fragment_ِBinding_order.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_ِBinding_order).commit();
        }
        if(fragment_waiting_order ==null){
            fragment_waiting_order = Fragment_Waiting_Order.newInstance();
        }
        if(fragment_waiting_order.isAdded()){
            fragmentManager.beginTransaction().show(fragment_waiting_order);
        }
        else {
            fragmentManager.beginTransaction().add(R.id.fragment_order_child, fragment_waiting_order,"fragment_waiting_order").addToBackStack("fragment_waiting_order").commit();
        }

    }
public void DisplayFragmentBindingOrder(){
    if(fragment_completed_order!=null&&fragment_completed_order.isAdded()){
        fragmentManager.beginTransaction().hide(fragment_completed_order).commit();
    }
    if(fragment_waiting_order !=null&& fragment_waiting_order.isAdded()){
        fragmentManager.beginTransaction().hide(fragment_waiting_order).commit();
    }
    if(fragment_ِBinding_order ==null){
        fragment_ِBinding_order = Fragment_ِBinding_Order.newInstance();
    }
    if(fragment_ِBinding_order.isAdded()){
        fragmentManager.beginTransaction().show(fragment_ِBinding_order);
    }
    else {
        fragmentManager.beginTransaction().add(R.id.fragment_order_child, fragment_ِBinding_order,"fragment_ِBinding_order").addToBackStack("fragment_ِBinding_order").commit();
    }
}
public void DisplayFragmentCompletedOrder(){
    if(fragment_ِBinding_order!=null&&fragment_ِBinding_order.isAdded()){
        fragmentManager.beginTransaction().hide(fragment_ِBinding_order).commit();
    }
    if(fragment_waiting_order !=null&& fragment_waiting_order.isAdded()){
        fragmentManager.beginTransaction().hide(fragment_waiting_order).commit();
    }
    if(fragment_completed_order ==null){
        fragment_completed_order = Fragment_Completed_Order.newInstance();
    }
    if(fragment_completed_order.isAdded()){
        fragmentManager.beginTransaction().show(fragment_completed_order);
    }
    else {
        fragmentManager.beginTransaction().add(R.id.fragment_order_child, fragment_completed_order,"fragment_completed_order").addToBackStack("fragment_completed_order").commit();
    }

}*/

    public void DisplayFragmentOffers() {


        if (fragment_main != null && fragment_main.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_main).commit();
        }

        if (fragment_orders != null && fragment_orders.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if (fragment_profile != null && fragment_profile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }
        if (fragment_more != null && fragment_more.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_more).commit();
        }

        if (fragment_offers == null) {
            fragment_offers = Fragment_Offers.newInstance();
        }

        if (fragment_offers.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_offers).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_offers, "fragment_offers").addToBackStack("fragment_offers").commit();

        }

        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.UpdateAHBottomNavigationPosition(2);
        }

    }

    public void DisplayFragmentProfile() {
        if (userModel == null) {
            Common.CreateUserNotSignInAlertDialog(this);
        } else {
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }

            if (fragment_orders != null && fragment_orders.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_orders).commit();
            }
            if (fragment_offers != null && fragment_offers.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_offers).commit();
            }
            if (fragment_more != null && fragment_more.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_more).commit();
            }

            if (fragment_profile == null) {
                fragment_profile = Fragment_Profile.newInstance();
            }

            if (fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_profile).commit();
            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_profile, "fragment_profile").addToBackStack("fragment_profile").commit();

            }

            if (fragment_home != null && fragment_home.isAdded()) {
                fragment_home.UpdateAHBottomNavigationPosition(3);
            }

        }


    }

    public void DisplayFragmentMore() {

        if (fragment_main != null && fragment_main.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_main).commit();
        }

        if (fragment_orders != null && fragment_orders.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if (fragment_offers != null && fragment_offers.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_offers).commit();
        }
        if (fragment_profile != null && fragment_profile.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }

        if (fragment_more == null) {
            fragment_more = Fragment_More.newInstance();
        }

        if (fragment_more.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_more).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_main_child, fragment_more, "fragment_sales_more").addToBackStack("fragment_sales_more").commit();

        }

        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.UpdateAHBottomNavigationPosition(4);
        }

    }

    public void display_bank_account(Setting_Model setting_models) {
        fragment_count += 1;
        fragment_bank_account = Fragment_Bank_Account.newInstance(setting_models);
        if (fragment_bank_account.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_bank_account).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_bank_account, "fragment_bank_account").addToBackStack("fragment_bank_account").commit();

        }

    }

    public void DisplayFragmentTerms_Condition(String terms_condition) {

        fragment_count += 1;
        fragmentTerms_conditions = Fragment_Terms_Conditions.newInstance(terms_condition);


        if (fragmentTerms_conditions.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentTerms_conditions).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragmentTerms_conditions, "fragmentTerms_conditions").addToBackStack("fragmentTerms_conditions").commit();

        }


    }


    public void DisplayFragment_ContactUS() {

        fragment_count += 1;

        fragmentContactUS = Fragment_Contact_US.newInstance();

        if (fragmentContactUS.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentContactUS).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragmentContactUS, "fragmentContactUS").addToBackStack("fragmentContactUS").commit();

        }


    }

    public void DisplayFragment_Product(int Product_id, String name, int param) {

        fragment_count += 1;

        fragment_product = fragment_product.newInstance(Product_id, name, param);

        if (fragment_product.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_product).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_product, "fragment_product").addToBackStack("fragment_product").commit();

        }


    }

    public void display_First_Buy(Model_Offr_Product.InnerData.Prods prods, int param) {
        fragmentProductDetails = Fragment_Product_Details.newInstance(param, prods);
        display_product_details();

    }

    public void display_First_Buy(Product_Model.InnerData data, int param) {
        fragmentProductDetails = Fragment_Product_Details.newInstance(param, data);
        display_product_details();

    }

    public void display_product_details() {
        fragment_count += 1;
        if (fragmentProductDetails.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentProductDetails).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragmentProductDetails, "fragmentProductDetails").addToBackStack("fragmentProductDetails").commit();

        }
    }

    public void DisplayAddtoCart(Orders_Cart_Model Orders_Cart_Model) {
        fragment_count += 1;
        fragmentCompleteOrder = Fragment_Add_Order_To_Cart.newInstance(Orders_Cart_Model);
        if (fragmentCompleteOrder.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentCompleteOrder).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragmentCompleteOrder, "fragmentCompleteOrder").addToBackStack("fragmentCompleteOrder").commit();

        }

    }

    public void display_Cart() {
        fragment_count += 1;
        fragment_cart = Fragment_Cart.newInstance();
        if (fragment_cart.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_cart).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_cart, "fragment_cart").addToBackStack("fragment_cart").commit();

        }

    }

    public void open_dialog() {
        final AlertDialog builder = new AlertDialog.Builder(this)
                .setCancelable(true)
                .create();
        builder.setCanceledOnTouchOutside(false);
        builder.setTitle(R.string.language_setting);
        View view = LayoutInflater.from(this).inflate(R.layout.language, null);
        final RadioGroup radioGroup = view.findViewById(R.id.languages);
        final Button choose = view.findViewById(R.id.choose);
        String lang = preferences.getlang(this);
        if (lang.equals("ar")) {
            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
        }
        Locale locale;
        choose.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                int id = radioGroup.getCheckedRadioButtonId();


                String languageToLoad = null;

                if (id == R.id.language1) {
                    languageToLoad = "ar";
                } else if (id == R.id.language2) {
                    languageToLoad = "en";
                }
                Language.setNewLocale(Home_Activity.this, languageToLoad);

                builder.dismiss();
                finish();
                Intent intent = getIntent();
                startActivity(intent);

            }
        });
        builder.setView(view);
        builder.show();

    }

    public void NavigateToSignInActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();

    }

    public void NavigateToSignupActivity() {
        Intent intent = new Intent(this, Sign_Up.class);
        startActivity(intent);
        finish();

    }


    @Override
    public void onBackPressed() {
        if (userModel != null && user_type.equals("sales")) {
            Back_sales();
        } else {

                Back();

        }
    }

    public void Back() {
        if (fragment_count > 1) {
            fragment_count -= 1;
            super.onBackPressed();
        } else {

            if (fragment_home != null && fragment_home.isVisible()) {
                if (fragment_main != null && fragment_main.isVisible()) {
                    if (userModel == null) {
                        NavigateToSignInActivity();
                    } else {
                        finish();
                    }
                } else {
                    DisplayFragmentMain();
                }
            } else {
                DisplayFragmentHome();
            }
        }

    }

    public void Back_sales() {
        if (fragment_count > 1) {
            fragment_count -= 1;
            super.onBackPressed();
        } else {

            if (fragment_sales_home != null && fragment_sales_home.isVisible()) {
                if (fragment_sales_order != null && fragment_sales_order.isVisible()) {

                    finish();

                } else {
                    DisplayFragmentSalesOrder();
                }
            } else {
                DisplayFragmentSalesHome();
            }
        }

    }


    public void Finishorder(int order_id, int user_id, final int postion, final Orders_Adpter.Eyas_Holder viewHolder) {
        Api.getService().finish_order(order_id, user_id).enqueue(new Callback<Data_Model>() {
            @Override
            public void onResponse(Call<Data_Model> call, Response<Data_Model> response) {
                if (response.isSuccessful()) {
                    viewHolder.removeAt(postion);
                } else {
                    Common.CreateSignAlertDialog(Home_Activity.this, getResources().getString(R.string.Something_Error));
                    Log.e("Error_code", response.code() + "_" + response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<Data_Model> call, Throwable t) {
                Log.e("Error_message", t.getMessage());

            }
        });
    }


    public void rating(int sales_id, int user_id, float rate) {
        Api.getService().rate_sales(user_id, sales_id, rate).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void Cancelorder(int order_id, int user_id, final Context context, final int postion, final Orders_Adpter.Eyas_Holder viewHolder) {
        Api.getService().cancel_order(order_id, user_id).enqueue(new Callback<Data_Model>() {
            @Override
            public void onResponse(Call<Data_Model> call, Response<Data_Model> response) {
                if (response.isSuccessful()) {
                    viewHolder.removeAt(postion);

                }
            }

            @Override
            public void onFailure(Call<Data_Model> call, Throwable t) {

            }
        });
    }
    public void check_permission(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},102);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (requestCode == 102 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED) {
          get_location();
        }
    }
public void get_location(){
try {
    locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);


}
catch (SecurityException e){
}

}
    @Override
    protected void onResume() {
        super.onResume();
get_location();
    }

    @Override
    protected void onPause() {
        super.onPause();
locationManager.removeUpdates(this);    }

    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lang=location.getLongitude();
        address=location.toString();

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
