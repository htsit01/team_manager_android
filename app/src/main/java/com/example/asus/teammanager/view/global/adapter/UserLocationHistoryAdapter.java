package com.example.asus.teammanager.view.global.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.Location;
import com.example.asus.teammanager.model.api_model.UserLocation;
import com.example.asus.teammanager.model.session_manager.SessionManager;

import java.util.ArrayList;

public class UserLocationHistoryAdapter extends RecyclerView.Adapter<UserLocationHistoryAdapter.MyViewHolder> {

    private ArrayList<Location> user_locations;
    private ArrayList<Location> user_checkins;
    private ArrayList<Location> user_checkouts;
    private int type;
    private SessionManager sm;

    public UserLocationHistoryAdapter (ArrayList<Location> user_locations,ArrayList<Location> user_checkins,ArrayList<Location> user_checkouts,  int type){
        this.user_locations = user_locations;
        this.user_checkins = user_checkins;
        this.user_checkouts = user_checkouts;
        this.type = type;
    }

    @NonNull
    @Override
    public UserLocationHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_location_history, parent, false);
        sm = new SessionManager(parent.getContext());
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserLocationHistoryAdapter.MyViewHolder holder, int position) {


        holder.txt_number.setText(String.valueOf(position));
        holder.user_name.setText(sm.getUser().getName());

        if(type==0){ //means view location
            holder.user_location.setText("At: ".concat(user_locations.get(position).getAddress()));
            if(position==0){
                holder.user_location_time_from.setText("From: ".concat(user_locations.get(position).getDate_time()));
                holder.user_location_time_until.setText("Until: ".concat("now."));
            }
            else{
                holder.user_location_time_from.setText("From: ".concat(user_locations.get(position).getDate_time()));
                holder.user_location_time_until.setText("Until: ".concat(user_locations.get(position-1).getDate_time()));
            }
        }
        else{
            holder.user_location.setText("At: ".concat(user_checkins.get(position).getCustomer()));
            if(user_checkins.size()-1==position){
                holder.user_location_time_from.setText("From: ".concat(user_checkins.get(position).getDate_time()));
                holder.user_location_time_until.setText("Until: ".concat("now."));
            }
            else{
                holder.user_location_time_from.setText("From: ".concat(user_checkins.get(position).getDate_time()));
                holder.user_location_time_until.setText("Until: ".concat(user_checkouts.get(position).getDate_time()));
            }
        }
    }

    @Override
    public int getItemCount() {
        if(type==0){
            return user_locations.size();
        }
        return user_checkins.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_number, user_name, user_location, user_location_time_from, user_location_time_until;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_number = itemView.findViewById(R.id.txt_number);
            user_name = itemView.findViewById(R.id.user_name);
            user_location = itemView.findViewById(R.id.user_location);
            user_location_time_from = itemView.findViewById(R.id.user_location_time_from);
            user_location_time_until = itemView.findViewById(R.id.user_location_time_until);
        }
    }
}
