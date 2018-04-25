package com.example.asus.teammanager.view.global.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.CustomerArea;
import com.example.asus.teammanager.model.api_model.VisitPlanList;
import com.example.asus.teammanager.presenter.customer_presenter.GetCustomerAreaPresenter;

import java.util.ArrayList;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.MyViewHolder>{

    private ArrayList<VisitPlanList> plan_list;
    private ArrayList<CustomerArea> customer_areas;

    public interface OnDeletePlanList{
        void onDelete(View view);
    }

    public interface OnClickPlanList{
        void onClick(View view);
    }

    private OnDeletePlanList onDeletePlanList;

    private OnClickPlanList onClickPlanList;

    public void setInterface(OnDeletePlanList onDeletePlanList, OnClickPlanList onClickPlanList){
        this.onDeletePlanList = onDeletePlanList;
        this.onClickPlanList = onClickPlanList;
    }

    public PlanListAdapter (ArrayList<VisitPlanList> plan_list, ArrayList<CustomerArea> customer_areas){
        this.plan_list = plan_list;
        this.customer_areas = customer_areas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_plan_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VisitPlanList plan = plan_list.get(position);

        holder.customer_name.setText(plan.getCustomer().getName());
        for(int i=0;i<customer_areas.size();i++){
            if(customer_areas.get(i).getId()==plan.getCustomer().getCustomer_area_id()){
                holder.customer_area.setText("Customer Area: ".concat(customer_areas.get(i).getName()));
                break;
            }
        }
        holder.plan_list_detail.setText(plan.getDescription()==null?"Description: -":"Description: ".concat(plan.getDescription()));

        if(plan.getStatus_color()==0){
            holder.color_indicator.setImageResource(R.drawable.ic_circle_red);
        }
        else if(plan.getStatus_color()==1){
            holder.color_indicator.setImageResource(R.drawable.ic_circle_green);
        }
        else if(plan.getStatus_color() ==2){
            holder.color_indicator.setImageResource(R.drawable.ic_circle_orange);
        }
        holder.txt_delete.setTag(position);
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return plan_list.size();
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
                    if(onDeletePlanList!=null){
                        onDeletePlanList.onDelete(v);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickPlanList!=null){
                        onClickPlanList.onClick(v);
                    }
                }
            });

        }
    }
}
