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
import com.creativeshare.zapyhakoom.Model.Offers_Model;
import com.creativeshare.zapyhakoom.R;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Offers_Adapter extends RecyclerView.Adapter<Offers_Adapter.Eyas_Holder> {
    List<Offers_Model.InnerData> list;
Context context;
    public Offers_Adapter(List<Offers_Model.InnerData> list, Context context){
        this.list=list;
        this.context=context;
    }
    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_offrs,viewGroup,false);
        Eyas_Holder eas=new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, int i) {
        Offers_Model.InnerData model=list.get(i);
        viewHolder.txt2.setText(model.getName());
        Log.e("im",model.getImage());

        Picasso.with(context).load(Uri.parse(Tags.base_IMage_url+model.getImage())).fit().into(viewHolder.frameLayout);

        //viewHolder.frameLayout.setImageResource(model.image);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_Activity activity = (Home_Activity) context;
                int product_id = list.get(viewHolder.getLayoutPosition()).getId() ;
                activity.DisplayFragment_Product(product_id,list.get(viewHolder.getLayoutPosition()).getName(),1);               }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class Eyas_Holder extends RecyclerView.ViewHolder {
        TextView txt2;

        RoundedImageView frameLayout;
        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            txt2=(TextView)itemView.findViewById(R.id.txt2);
            frameLayout=(RoundedImageView) itemView.findViewById(R.id.img1);


        }
    }
}

