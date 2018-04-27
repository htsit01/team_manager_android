package com.example.asus.teammanager.view.global.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.FollowUp;

import java.util.ArrayList;

public class FollowUpAdapter extends RecyclerView.Adapter<FollowUpAdapter.MyViewHolder> {

    private ArrayList<FollowUp> follow_ups;

    public FollowUpAdapter (ArrayList<FollowUp> follow_ups){
        this.follow_ups = follow_ups;
    }

    public interface OnDeleteFollowUp{
        void onDelete(View v);
    }

    private OnDeleteFollowUp onDeleteFollowUp;

    public void setInterface (OnDeleteFollowUp onDeleteFollowUp){
        this.onDeleteFollowUp = onDeleteFollowUp;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_plan_list,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FollowUp follow_up = follow_ups.get(position);
        holder.customer_name.setText(follow_up.getCustomer()==null?follow_up.getName():follow_up.getCustomer().getName());
        holder.customer_area.setText("Customer address: ".concat(follow_up.getCustomer()==null?follow_up.getAddress():follow_up.getCustomer().getShipping_address()));
        holder.plan_list_detail.setText(follow_up.getDescription()==null?"Description: -":"Description: ".concat(follow_up.getDescription()));
        holder.color_indicator.setImageResource(follow_up.getStatus_color()==0?R.drawable.ic_circle_red:R.drawable.ic_circle_green);
        holder.txt_delete.setTag(position);
        if(follow_up.getStatus_done()==0){
            holder.txt_delete.setVisibility(View.VISIBLE);
        }
        else{
            holder.txt_delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return follow_ups.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_delete, customer_name, customer_area,plan_list_detail;
        ImageView color_indicator;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_delete = itemView.findViewById(R.id.txt_delete);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_area = itemView.findViewById(R.id.customer_area);
            plan_list_detail = itemView.findViewById(R.id.plan_list_detail);
            color_indicator = itemView.findViewById(R.id.color_indicator);
            txt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onDeleteFollowUp!=null){
                        onDeleteFollowUp.onDelete(v);
                    }
                }
            });
        }
    }
}
