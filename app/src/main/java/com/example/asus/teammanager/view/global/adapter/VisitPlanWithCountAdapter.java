package com.example.asus.teammanager.view.global.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.class_project.Functionality;
import com.example.asus.teammanager.model.api_model.VisitPlanWithCount;

import java.util.ArrayList;

public class VisitPlanWithCountAdapter extends RecyclerView.Adapter<VisitPlanWithCountAdapter.MyViewHolder>{

    private ArrayList<VisitPlanWithCount> visit_plans;
    private Context context;
    public interface onDeleteVisitPlan{
        void onDelete(View view);
    }
    private onDeleteVisitPlan onDeleteVisitPlan;

    public void setInteface(onDeleteVisitPlan onDeleteVisitPlan){
        this.onDeleteVisitPlan = onDeleteVisitPlan;
    }

    public VisitPlanWithCountAdapter(ArrayList<VisitPlanWithCount> visit_plans){
        this.visit_plans = visit_plans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_visit_plans, parent, false);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VisitPlanWithCount visit_plan = visit_plans.get(position);
        holder.visit_plan_date.setText("valid on: ".concat(Functionality.formatDate("yyyy-MM-dd", "EEE, dd MMM yyyy", visit_plan.getValid_date())));
        holder.visit_plan_numbOfPlans.setText("Number of plans: ".concat(String.valueOf(visit_plan.getNumb_of_plans())));
        holder.visit_plan_numbOfDone.setText("Number of done: ".concat(String.valueOf(visit_plan.getNumb_of_done())));
        holder.txt_delete.setTag(visit_plan.getId());

        if(visit_plan.getStatus()!=null){
            if(visit_plan.getStatus()==0){
                holder.visit_plan_status.setText("Status: Pending");
                holder.visit_plan_status.setTextColor(ContextCompat.getColor(context,R.color.colorTextSecondary));
                holder.txt_delete.setVisibility(View.VISIBLE);
            }
            else if(visit_plan.getStatus() == 1){
                holder.visit_plan_status.setText("Status: Rejected");
                holder.visit_plan_status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
                holder.txt_delete.setVisibility(View.VISIBLE);
            }
            else{
                holder.visit_plan_status.setText("Status: Approved");
                holder.visit_plan_status.setTextColor(ContextCompat.getColor(context, R.color.colorTextGreen));
                holder.txt_delete.setVisibility(View.GONE);
            }
        }
        else{
            holder.txt_delete.setVisibility(View.VISIBLE);
            holder.visit_plan_status.setTextColor(ContextCompat.getColor(context,R.color.colorTextSecondary));
            holder.visit_plan_status.setText("Status: None");
        }
    }

    @Override
    public int getItemCount() {
        return visit_plans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  visit_plan_status, visit_plan_numbOfPlans, visit_plan_numbOfDone, visit_plan_date, txt_delete;

        public MyViewHolder(View itemView) {
            super(itemView);

            visit_plan_status = itemView.findViewById(R.id.visit_plan_status);
            visit_plan_numbOfPlans = itemView.findViewById(R.id.visit_plan_numbOfPlans);
            visit_plan_numbOfDone = itemView.findViewById(R.id.visit_plan_numbOfDone);
            visit_plan_date = itemView.findViewById(R.id.visit_plan_date);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            txt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onDeleteVisitPlan!=null){
                        onDeleteVisitPlan.onDelete(v);
                    }
                }
            });
            
        }
    }
}
