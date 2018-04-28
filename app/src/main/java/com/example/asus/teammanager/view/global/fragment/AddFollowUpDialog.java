package com.example.asus.teammanager.view.global.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.class_project.Functionality;
import com.example.asus.teammanager.model.api_model.Customer;
import com.example.asus.teammanager.model.api_model.CustomerArea;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.customer_presenter.GetCustomerPresenter;

import java.util.ArrayList;
import java.util.Date;

public class AddFollowUpDialog extends DialogFragment {

    private Spinner spinner_shop;
    private TextView customer_area;
    private EditText description, end_user_name, end_user_address;
    private ProgressBar progress_shop;
    private ArrayList<String> customers_name = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<CustomerArea> customer_areas;
    private String txt_description, date_time;
    private ArrayAdapter<String> shop_adapter;

    //type disini adalah apakah type end user or customer
    // 0 end user
    // 1 customer
    //status means edit or add
    private int type;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view  = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_followup, null);
        builder.setView(view);
        builder.setTitle("Add Followup Plan");

        spinner_shop = view.findViewById(R.id.spinner_shop);
        customer_area = view.findViewById(R.id.customer_area);
        description = view.findViewById(R.id.description);
        progress_shop = view.findViewById(R.id.progress_shop);
        end_user_name = view.findViewById(R.id.end_user_name);
        end_user_address =view.findViewById(R.id.end_user_address);
        LinearLayout ll_customer = view.findViewById(R.id.ll_customer);
        LinearLayout ll_end_user = view.findViewById(R.id.ll_end_user);

        customer_areas = (ArrayList<CustomerArea>) getArguments().getSerializable("CUSTOMER_AREAS");
        type = getArguments().getInt("TYPE");

        SessionManager sm = new SessionManager(getActivity());
        builder.setTitle("Add Follow Up");

        if(type == 0){
            ll_customer.setVisibility(View.GONE);
            ll_end_user.setVisibility(View.VISIBLE);
        }
        else{
            ll_end_user.setVisibility(View.GONE);
            ll_customer.setVisibility(View.VISIBLE);
            GetCustomerPresenter customer_presenter = new GetCustomerPresenter(new GlobalPresenter() {
                @Override
                public void onSuccess(Object object) {
                    ArrayList<Customer> result = (ArrayList<Customer>) object;
                    customers.clear();
                    customers_name.clear();
                    customers.addAll(result);
                    for (Customer customer : result) {
                        customers_name.add(customer.getName());
                    }
                    shop_adapter.notifyDataSetChanged();
                    spinner_shop.setAdapter(shop_adapter);
                    spinner_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            for (int i = 0; i < customer_areas.size(); i++) {
                                if (customer_areas.get(i).getId() == customers.get(spinner_shop.getSelectedItemPosition()).getCustomer_area_id()) {
                                    customer_area.setText(customer_areas.get(i).getName());
                                    Log.e("tes", "test");
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    spinner_shop.setVisibility(View.VISIBLE);
                    progress_shop.setVisibility(View.GONE);
                }

                @Override
                public void onError(int code, String message) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(String message) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            });

            customer_presenter.getCustomer(sm.getToken().getAccess_token());
        }

        spinner_shop.setVisibility(View.GONE);
        progress_shop.setVisibility(View.VISIBLE);
        shop_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, customers_name);
        shop_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Date date = new Date();
                date_time = Functionality.formatDatetoString(date, "yyyy-MM-dd");
                Intent intent = new Intent();

                if(TextUtils.isEmpty(description.getText().toString().trim())){
                    txt_description = null;
                }
                else{
                    txt_description = description.getText().toString();
                }
                intent.putExtra("TYPE", type);
                intent.putExtra("DATE_TIME", date_time);
                intent.putExtra("DESCRIPTION", txt_description);
                if(type==0){
                    if(!TextUtils.isEmpty(end_user_name.getText().toString().trim())&&!TextUtils.isEmpty(end_user_address.getText().toString())){
                        intent.putExtra("END_USER_NAME", end_user_name.getText().toString());
                        intent.putExtra("END_USER_ADDRESS", end_user_address.getText().toString());
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                    else{
                        Toast.makeText(getContext(), "Add Follow up plan failed. Make sure you fill all things that are required.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    intent.putExtra("CUSTOMER_ID",customers.get(spinner_shop.getSelectedItemPosition()).getId());
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
