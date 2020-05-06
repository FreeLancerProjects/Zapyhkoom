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
import com.creativeshare.zapyhakoom.Model.Catogry_Model_Slide;
import com.creativeshare.zapyhakoom.R;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Catogries_Adapter extends RecyclerView.Adapter<Catogries_Adapter.Eyas_Holder>{
List<Catogry_Model_Slide.InnerData> list;
Context context;
  public Catogries_Adapter(List<Catogry_Model_Slide.InnerData> list, Context context){
this.list=list;
this.context=context;

   }
    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_home,viewGroup,false);
        Eyas_Holder eas=new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Eyas_Holder viewHolder, int i) {
Catogry_Model_Slide.InnerData  model=list.get(i);
       viewHolder.txt2.setText(model.getName());
        Log.e("im",model.getImage());

        Picasso.with(context).load(Uri.parse(Tags.base_IMage_url+model.getImage())).fit().into(viewHolder.im);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_Activity activity = (Home_Activity) context;
                int Product_id = list.get(viewHolder.getLayoutPosition()).getId();
                activity.DisplayFragment_Product(Product_id,list.get(viewHolder.getLayoutPosition()).getName(),2);                }

            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class Eyas_Holder extends RecyclerView.ViewHolder {
TextView txt2;
CircleImageView im;

        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            txt2=itemView.findViewById(R.id.txt2);
            im= itemView.findViewById(R.id.img);

        }


    }
}
