package com.creativeshare.zapyhakoom.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.Model.Orders_Cart_Model;
import com.creativeshare.zapyhakoom.R;
import com.creativeshare.zapyhakoom.Tags.Tags;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cart_Adpter extends RecyclerView.Adapter<Cart_Adpter.Eyas_Holder> {
    List<Orders_Cart_Model> list;
    Context context;
Home_Activity activity;
Preferences preferences;
    public Cart_Adpter(List<Orders_Cart_Model> list, Context context) {
        this.list = list;
        this.context = context;
        activity=(Home_Activity)context;
        preferences=Preferences.getInstance();
    }

    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_cart, viewGroup, false);
        Eyas_Holder eas = new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Cart_Adpter.Eyas_Holder viewHolder, int i) {
        Orders_Cart_Model model = list.get(i);
        viewHolder.name.setText(model.getName() + "");
        viewHolder.price.setText(model.getOrder_total());
        viewHolder.total.setText(model.getQuantity() + "");
        Log.e("im", model.getImage());
        Picasso.with(context).load(Uri.parse(Tags.base_IMage_url + model.getImage())).fit().into(viewHolder.frameLayout);
viewHolder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
deleteItem(viewHolder.getLayoutPosition());
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
        TextView total;
        ImageView delete;
        CircleImageView frameLayout;

        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_name);
            price = itemView.findViewById(R.id.cart_price);
            total = itemView.findViewById(R.id.total_cart);
            frameLayout = itemView.findViewById(R.id.image_cart);
            delete = itemView.findViewById(R.id.image_delete);

        }
    }
    private void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
        preferences.create_update_order(activity,list);
        if(list.size()==0){
            activity.update();
        }
    }
}


