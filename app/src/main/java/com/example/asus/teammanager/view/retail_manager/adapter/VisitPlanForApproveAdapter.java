package com.example.asus.teammanager.view.retail_manager.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.class_project.Functionality;
import com.example.asus.teammanager.model.api_model.VisitPlanForApprove;

import java.util.ArrayList;

public class VisitPlanForApproveAdapter extends RecyclerView.Adapter<VisitPlanForApproveAdapter.MyViewHolder>{

    private ArrayList<VisitPlanForApprove> visit_plans;

    public VisitPlanForApproveAdapter (ArrayList<VisitPlanForApprove> visit_plans){
        this.visit_plans = visit_plans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_visit_plan_approve,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VisitPlanForApprove plan = visit_plans.get(position);
        holder.visit_plan_title.setText(plan.getUser().concat("\'s Visit Plan"));
        holder.visit_plan_date.setText("Valid Date: ".concat(Functionality.formatDate("yyyy-MM-dd", "EEE, dd MMM yyyy", plan.getValid_date())));
        holder.visit_plan_last_edit.setText("Last Edit: ".concat(Functionality.formatDate("yyyy-MM-dd", "EEE, dd MMM yyyy", plan.getUpdated_at())));
    }

    @Override
    public int getItemCount() {
        return visit_plans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView visit_plan_title, visit_plan_date, visit_plan_last_edit;

        public MyViewHolder(View itemView) {
            super(itemView);

            visit_plan_title = itemView.findViewById(R.id.visit_plan_title);
            visit_plan_date = itemView.findViewById(R.id.visit_plan_date);
            visit_plan_last_edit = itemView.findViewById(R.id.visit_plan_last_edit);
        }
    }
}
