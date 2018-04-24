package com.example.asus.teammanager.view.global.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.VisitPlanList;

import java.util.ArrayList;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.MyViewHolder>{

    private ArrayList<VisitPlanList> plan_list;

    public PlanListAdapter (ArrayList<VisitPlanList> plan_list){
        this.plan_list = plan_list;
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
        holder.customer_area.setText(plan.getCustomer().getCustomer_area_id()); //TODO:: set sebuah route untuk ambil khusus customer area id, kemudian update value berdasarkan id customer area
        holder.plan_list_detail.setText(plan.getDescription()==null?"-":plan.getDescription());

    }

    @Override
    public int getItemCount() {
        return plan_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_delete, customer_name, customer_area,plan_list_detail;

        public MyViewHolder(View itemView) {
            super(itemView);


            txt_delete = itemView.findViewById(R.id.txt_delete);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_area = itemView.findViewById(R.id.customer_area);
            plan_list_detail = itemView.findViewById(R.id.plan_list_detail);
        }
    }
}
