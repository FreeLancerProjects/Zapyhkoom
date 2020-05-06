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

import com.creativeshare.zapyhakoom.Tags.Tags;
import com.creativeshare.zapyhakoom.preferences.Preferences;
import com.creativeshare.zapyhakoom.Activities_fragment.Activites.Home_Activity;
import com.creativeshare.zapyhakoom.Model.Orders_Model;
import com.creativeshare.zapyhakoom.R;
import com.creativeshare.zapyhakoom.Share.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Orders_Adpter extends RecyclerView.Adapter<Orders_Adpter.Eyas_Holder> {
    List<Orders_Model.InnerData> list;
    Context context;
    Home_Activity activity;
    String accur;
    Preferences preferences;

    public Orders_Adpter(List<Orders_Model.InnerData> list, Context context, String accur) {
        this.list = list;
        this.context = context;
        activity = (Home_Activity) context;
        this.accur = accur;
        preferences = Preferences.getInstance();
    }

    @Override
    public Eyas_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_layout, viewGroup, false);
        Eyas_Holder eas = new Eyas_Holder(v);
        return eas;
    }

    @Override
    public void onBindViewHolder(@NonNull final Orders_Adpter.Eyas_Holder viewHolder, int i) {
        final Orders_Model.InnerData model = list.get(i);
        viewHolder.name.setText(model.getproduct().getName());
        viewHolder.price.setText(model.getOrder_total() + accur);
        String time[] = model.getCreated_at().split("\\s");
        String time2[] = time[1].split(":");
        String timemode;
        if (Integer.parseInt(time2[0]) <12) {
            if (preferences.getlang(context).equals("ar")) {
                timemode = "صباحا";
            } else {
                timemode = "AM";
            }
        } else {
            if (preferences.getlang(context).equals("ar")) {
                timemode = "مساءا";
            } else {
                timemode = "PM";
            }
        }

        viewHolder.time.setText(time[1] + timemode);
        Log.e("im",model.getproduct().getImage());

        Picasso.with(context).load(Uri.parse(Tags.base_IMage_url+model.getproduct().getImage())).fit().into(viewHolder.frameLayout);

        //viewHolder.frameLayout.setImageResource(model.image);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getUserData(context) != null) {

                        if (list.get(viewHolder.getLayoutPosition()).getOrder_status().equals("1")) {
                            if (preferences.getUserData(activity).getData().getRole().equals("user")) {
                                Common.CreateRsting(activity,model.getUser_id(),model.getSales_id(),model.getId(),viewHolder.getLayoutPosition(),viewHolder);
                            } else if (preferences.getUserData((activity)).getData().getRole().equals("sales")) {
                                activity.DisplayFragmentOrderDetails(list.get(viewHolder.getLayoutPosition()));
                            }

                    }
                        else if (list.get(viewHolder.getLayoutPosition()).getOrder_status().equals("0")) {
                            if (preferences.getUserData(activity).getData().getRole().equals("user")) {

                                Common.CreateUserCancelOrder(activity,model.getId(),model.getUser_id(),viewHolder.getLayoutPosition(),viewHolder);


                            }
                        }

                        }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Eyas_Holder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView time;

        CircleImageView frameLayout;

        public Eyas_Holder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.order_name);
            price = (TextView) itemView.findViewById(R.id.order_price);
            time = (TextView) itemView.findViewById(R.id.time);

            frameLayout = (CircleImageView) itemView.findViewById(R.id.image_order);


        }
        public void removeAt(int position) {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size());
        }
    }
}


