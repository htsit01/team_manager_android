package com.example.asus.teammanager.view.global.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.UserLocation;
import com.example.asus.teammanager.view.global.activity.LocationHistoryActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserLocationAdapter extends RecyclerView.Adapter<UserLocationAdapter.MyViewHolder>{

    private ArrayList<UserLocation> user_locations;
    private Context context;

    public UserLocationAdapter (ArrayList<UserLocation> user_locations){
        this.user_locations = user_locations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_location,parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserLocation item = user_locations.get(position);
        if(item.getLast_location()!=null){
            holder.card_view.setVisibility(View.VISIBLE);

            holder.user_name.setText(item.getName());

            if(item.getLast_location().getStatus()==0){
                holder.user_location.setText("Last location: ".concat(item.getLast_location().getAddress()));
                holder.user_location_time.setText("Time: ".concat(item.getLast_location().getTime_since()));
            }
            else{
                holder.user_location.setText("Last checkin: ".concat(item.getLast_location().getCustomer()));
                holder.user_location_time.setText("Time: ".concat(item.getLast_location().getTime_since()));
            }
        }
        else{
            holder.card_view.setVisibility(View.GONE);
        }
        holder.itemView.setTag(item.getId());
    }

    @Override
    public int getItemCount() {
        return user_locations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView user_img;
        TextView user_name, user_location, user_location_time;
        CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);

            user_img = itemView.findViewById(R.id.user_img);
            user_name = itemView.findViewById(R.id.user_name);
            user_location = itemView.findViewById(R.id.user_location);
            user_location_time = itemView.findViewById(R.id.user_location_time);
            card_view = itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LocationHistoryActivity.class);
                    intent.putExtra("id", (int)v.getTag());
                    context.startActivity(intent);
                }
            });
        }
    }
}
