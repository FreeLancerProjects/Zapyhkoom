package com.creativeshare.zapyhakoom.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.Model.Model_Offr_Product;
import com.creativeshare.zapyhakoom.R;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Product_Offers_Adapter extends RecyclerView.Adapter<Product_Offers_Adapter.Eyas_Holder> {
    List<Model_Offr_Product.InnerData.Prods> list;
    Context context;
    Home_Activity activity;
    public Product_Offers_Adapter(List<Model_Offr_Product.InnerData.Prods> list, Context context){
        this.list=list;
        this.context=context;
        activity=(Home_Activity)context;
    }
    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_layout,viewGroup,false);
    Eyas_Holder eas=new Eyas_Holder(v);

        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, int i) {
        Model_Offr_Product.InnerData.Prods model=list.get(i);
        viewHolder.name.setText(model.getName());
        Log.e("im",model.getImage());

        Picasso.with(context).load(Uri.parse(Tags.base_IMage_url+model.getImage())).fit().into(viewHolder.frameLayout);
    viewHolder.price.setText(model.getPrice()+"");
viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       activity.display_First_Buy(list.get(viewHolder.getLayoutPosition()),1);
    }
});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class Eyas_Holder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;

        RoundedImageView frameLayout;
        public Eyas_Holder(View itemView) {
            super(itemView);
           name=itemView.findViewById(R.id.type_name);
            frameLayout= itemView.findViewById(R.id.img_type);
            price=itemView.findViewById(R.id.type_price);


        }
    }
}
