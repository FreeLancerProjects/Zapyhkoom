package com.creativeshare.zapyhakoom.Activities_fragment.Fragments.Fragment_user;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.creativeshare.zapyhakoom.Model.Model_Offr_Product;
import com.creativeshare.zapyhakoom.Model.Orders_Cart_Model;
import com.creativeshare.zapyhakoom.Model.Product_Model;
import com.creativeshare.zapyhakoom.Share.Common;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 *
 */
public class Fragment_Product_Details extends Fragment {
    private Preferences preferences;
    private Home_Activity activity;
    private Model_Offr_Product.InnerData.Prods prods;
    private Product_Model.InnerData data;
    private ImageView back;
    private Button increment;
    private Button decrement;
    private TextView quantity, size, total;
    private Spinner cutting;
    private RadioGroup covering, kersh;
    private CheckBox checkBox;
    private EditText details;
    private Button complete;
    private RadioButton without;
    private CircleImageView product_iamge;
    private int param;
    private double quantit, cutt, cover = 0;
    private static final String Tag = "param";
    private static final String Tag2 = "product_id";
    private com.creativeshare.zapyhakoom.Model.Orders_Cart_Model Orders_Cart_Model;
    private String cover_ing;
    private List<String> list1;
    private ArrayAdapter<String> adp3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);


        intiview(view);


        return view;
    }

    public static Fragment_Product_Details newInstance(int param, Model_Offr_Product.InnerData.Prods prods) {

        Fragment_Product_Details fragment_details = new Fragment_Product_Details();
        Bundle bundle = new Bundle();
        bundle.putInt(Tag, param);
        bundle.putSerializable(Tag2, prods);
        fragment_details.setArguments(bundle);
        return fragment_details;
    }

    public static Fragment_Product_Details newInstance(int param, Product_Model.InnerData data) {

        Fragment_Product_Details fragment_details = new Fragment_Product_Details();
        Bundle bundle = new Bundle();
        bundle.putInt(Tag, param);
        bundle.putSerializable(Tag2, data);
        fragment_details.setArguments(bundle);
        return fragment_details;
    }

    public void intiview(final View view) {
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        product_iamge = view.findViewById(R.id.img_product_details);
        back = (ImageView) view.findViewById(R.id.back_buy);
        increment = (Button) view.findViewById(R.id.increment);
        decrement = (Button) view.findViewById(R.id.decrement);
        quantity = (TextView) view.findViewById(R.id.quantity);
        size = (TextView) view.findViewById(R.id.size);
        cutting = (Spinner) view.findViewById(R.id.cutting);
        covering = (RadioGroup) view.findViewById(R.id.packing_buy);
        kersh = (RadioGroup) view.findViewById(R.id.Kersh);
        details = (EditText) view.findViewById(R.id.details);
        complete = (Button) view.findViewById(R.id.complete);
        total = (TextView) view.findViewById(R.id.total);
        checkBox=view.findViewById(R.id.checkbox);
        param = getArguments().getInt(Tag);
        Orders_Cart_Model = new Orders_Cart_Model();
        if (preferences.getUserData(activity) != null) {
            Orders_Cart_Model.setUser_id(preferences.getUserData(activity).getData().getId());
        }
        if (preferences.getlang(activity).equals("en")) {
            back.setRotation(180);
        }
        if (param == 1) {
            prods = (Model_Offr_Product.InnerData.Prods) getArguments().getSerializable(Tag2);

            cutt = Double.parseDouble(prods.getFridge_cutting_price());
            quantit = prods.getQuantity();
            Orders_Cart_Model.setName(prods.getName());
            Orders_Cart_Model.setProduct_id(prods.getId());
            Orders_Cart_Model.setImage(prods.getImage());

        } else {
            data = (Product_Model.InnerData) getArguments().getSerializable(Tag2);

            cutt = Double.parseDouble(data.getFridge_cutting_price());
            quantit = data.getQuantity();
            Orders_Cart_Model.setName(data.getName());
            Orders_Cart_Model.setProduct_id(data.getId());
            Orders_Cart_Model.setImage(data.getImage());
        }
        Drawable spinnerDrawable = cutting.getBackground().getConstantState().newDrawable();

        spinnerDrawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            cutting.setBackground(spinnerDrawable);
        } else {
            cutting.setBackgroundDrawable(spinnerDrawable);
        }

        list1 = new ArrayList<String>();
        adp3 = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, list1);
        list1.add(getResources().getString(R.string.fridge));
        list1.add(getResources().getString(R.string.hadrmi1));
        list1.add(getResources().getString(R.string.complete));

        list1.add(getResources().getString(R.string.stand));


        cutting.setAdapter(adp3);
        kersh.check(R.id.No);
        covering.check(R.id.without);
        if (param == 1) {
            Picasso.with(activity).load(Uri.parse(Tags.base_IMage_url + prods.getImage())).fit().into(product_iamge);
            size.setText((prods.getDescription().replaceAll("الحجم", "").replaceAll("من", "")));
            total.setText((prods.getPrice() + cutt + cover) + "");

        } else {
            Picasso.with(activity).load(Uri.parse(Tags.base_IMage_url + data.getImage())).fit().into(product_iamge);
            size.setText((data.getDescription().replaceAll("الحجم", "").replaceAll("من", "")));
            total.setText((data.getPrice() + cutt + cover) + "");

        }


        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double price;

                if (quantit > Integer.parseInt(quantity.getText().toString())) {
                    quantity.setText((Integer.parseInt(quantity.getText().toString()) + 1) + "");
                    if (param == 1) {
                        price = (double) (Double.parseDouble(total.getText().toString())) + prods.getPrice();
                    } else {
                        price = (double) (Double.parseDouble(total.getText().toString())) + data.getPrice();
                    }
                    total.setText(price + "");
                }
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double price;
                if (Integer.parseInt(quantity.getText().toString()) > 1) {
                    quantity.setText((Integer.parseInt(quantity.getText().toString()) - 1) + "");
                    if (param == 1) {
                        price = (double) (Double.parseDouble(total.getText().toString())) - prods.getPrice();
                    } else {
                        price = (double) (Double.parseDouble(total.getText().toString())) - data.getPrice();
                    }


                    total.setText(price + "");

                }
            }
        });

        cutting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                double total1 = Double.parseDouble(total.getText().toString()) - cutt;
                if (position == 0) {
                    if (param == 1) {
                        total1 += Double.parseDouble(prods.getFridge_cutting_price());
                        cutt = Double.parseDouble(prods.getFridge_cutting_price());
                    } else {
                        total1 += Double.parseDouble(data.getFridge_cutting_price());
                        cutt = Double.parseDouble(data.getFridge_cutting_price());

                    }

                } else if (position == 1) {
                    if (param == 1) {
                        total1 += Double.parseDouble(prods.getQuarter_cutting_price());
                        cutt = Double.parseDouble(prods.getQuarter_cutting_price());
                    } else {
                        total1 += Double.parseDouble(data.getQuarter_cutting_price());
                        cutt = Double.parseDouble(data.getQuarter_cutting_price());
                    }
                } else if (position == 2) {
                    if (param == 1) {
                        total1 += Double.parseDouble(prods.getHalf_cutting_price());
                        cutt = Double.parseDouble(prods.getHalf_cutting_price());
                    } else {
                        total1 += Double.parseDouble(data.getHalf_cutting_price());
                        cutt = Double.parseDouble(data.getHalf_cutting_price());
                    }

                } else if (position == 3) {
                    if (param == 1) {
                        total1 += Double.parseDouble(prods.getAlife_price());
                        cutt = Double.parseDouble(prods.getAlife_price());
                    } else {
                        total1 += Double.parseDouble(data.getAlife_price());
                        cutt = Double.parseDouble(data.getAlife_price());
                    }

                } else  {
                    if (param == 1) {
                        total1 += Double.parseDouble(prods.getStand_price());
                        cutt = Double.parseDouble(prods.getStand_price());
                    } else {
                        total1 += Double.parseDouble(data.getStand_price());
                        cutt = Double.parseDouble(data.getStand_price());
                    }

                }
                total.setText(total1 + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        covering.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                double total1 = Double.parseDouble(total.getText().toString()) - cover;
                if (group.getCheckedRadioButtonId() == R.id.plates) {
                    if (param == 1) {
                        total1 += Double.parseDouble(prods.getPlates_price());
                        cover = Double.parseDouble(prods.getPlates_price());
                    } else {
                        total1 += Double.parseDouble(data.getPlates_price());
                        cover = Double.parseDouble(data.getPlates_price());
                    }
                } else if (group.getCheckedRadioButtonId() == R.id.bags) {
                    if (param == 1) {
                        total1 += Double.parseDouble(prods.getPlastic_price());
                        cover = Double.parseDouble(prods.getPlastic_price());
                    } else {
                        total1 += Double.parseDouble(data.getPlastic_price());
                        cover = Double.parseDouble(data.getPlastic_price());

                    }
                } else {
                    cover = 0;
                }

                total.setText(total1 + "");
            }
        });
        kersh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                double total1 = Double.parseDouble(total.getText().toString());
                if (group.getCheckedRadioButtonId() == R.id.yes) {
                    if (param == 1) {
                        total1 += Double.parseDouble(prods.getKersh_and_mosran_price());
                    } else {
                        total1 += Double.parseDouble(data.getKersh_and_mosran_price());
                    }
                } else if (group.getCheckedRadioButtonId() == R.id.No) {
                    if (param == 1) {
                        total1 -= Double.parseDouble(prods.getKersh_and_mosran_price());
                    } else {
                        total1 -= Double.parseDouble(data.getKersh_and_mosran_price());
                    }

                }
                total.setText(total1 + "");
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.CloseKeyBoard(activity, view);

                if (kersh.getCheckedRadioButtonId() == R.id.No) {
                    Orders_Cart_Model.setKersh_and_mosran(0);
                } else {
                    Orders_Cart_Model.setKersh_and_mosran(1);
                }
                if (covering.getCheckedRadioButtonId() == R.id.bags) {
                    cover_ing = "2";
                } else if (covering.getCheckedRadioButtonId() == R.id.plates) {
                    cover_ing = "1";
                } else {
                    cover_ing = "0";

                }
                Orders_Cart_Model.setQuantity(Integer.parseInt(quantity.getText().toString()));
                Orders_Cart_Model.setCutting(cutting.getSelectedItemPosition() + "");
                Orders_Cart_Model.setCovering(cover_ing);
                Orders_Cart_Model.setDescription(details.getText().toString());
                Orders_Cart_Model.setOrder_total(total.getText().toString());
                activity.DisplayAddtoCart(Orders_Cart_Model);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });
    }
}
