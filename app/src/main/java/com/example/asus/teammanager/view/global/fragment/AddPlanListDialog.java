package com.example.asus.teammanager.view.global.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.Customer;
import com.example.asus.teammanager.model.api_model.CustomerArea;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.customer_presenter.GetCustomerPresenter;

import java.util.ArrayList;

public class AddPlanListDialog extends DialogFragment {

    private Spinner spinner_shop;
    private TextView lbl_customer_area, customer_area;
    private EditText description;
    private ProgressBar progress_shop;
    private GetCustomerPresenter customer_presenter;
    private int status, type, intent_type; //0 add 1 edit
    private ArrayList<String> customers_name = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private SessionManager sm;
    private ArrayList<CustomerArea> customer_areas;
    private RadioGroup type_group;
    private String txt_descripton;
    private ArrayAdapter<String> shop_adapter;
    private String intent_description;

    public interface OnSubmit{
        void onSubmit(int customer_id, int type, String description);
    }

    private OnSubmit onSubmit;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_plan,null);
        builder.setView(view);

        spinner_shop = view.findViewById(R.id.spinner_shop);
        lbl_customer_area = view.findViewById(R.id.lbl_customer_area);
        customer_area = view.findViewById(R.id.customer_area);
        description = view.findViewById(R.id.description);
        progress_shop = view.findViewById(R.id.progress_shop);
        type_group = view.findViewById(R.id.type_group);


//        status = getArguments().getInt("status");
        customer_areas = (ArrayList<CustomerArea>) getArguments().getSerializable("CUSTOMER_AREAS");
        status = getArguments().getInt("STATUS");
        intent_description = getArguments().getString("DESCRIPTION");
        intent_type = getArguments().getInt("TYPE");

        if(status==0){
            builder.setTitle("Add Plan List");
        }
        else{
            builder.setTitle("Edit Plan List");
            if(intent_type == 0){
                type_group.check(R.id.type_daily);
            }
            else{
                type_group.check(R.id.type_periodic);
            }
            description.setText(intent_description);
        }

        sm = new SessionManager(getActivity());

        spinner_shop.setVisibility(View.GONE);
        progress_shop.setVisibility(View.VISIBLE);
        shop_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, customers_name);
        shop_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        customer_presenter = new GetCustomerPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                ArrayList<Customer> result = (ArrayList<Customer>) object;
                customers.clear();
                customers_name.clear();
                customers.addAll(result);
                for (Customer customer: result) {
                    customers_name.add(customer.getName());
                }
                shop_adapter.notifyDataSetChanged();
                spinner_shop.setAdapter(shop_adapter);
                spinner_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for(int i=0;i<customer_areas.size();i++){
                            if(customer_areas.get(i).getId()==customers.get(spinner_shop.getSelectedItemPosition()).getCustomer_area_id()){
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

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(onSubmit!=null){
                    if(status==0){
                        if(type_group.getCheckedRadioButtonId()==-1){
                            Toast.makeText(getContext(), "Please, make sure you select one of the plan type.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(type_group.getCheckedRadioButtonId() == R.id.type_daily){
                                type = 0;
                            }
                            else {
                                type = 1;
                            }
                            if(TextUtils.isEmpty(description.getText().toString().trim())){
                                txt_descripton = null;
                            }
                            else{
                                txt_descripton = description.getText().toString();
                            }
                            onSubmit.onSubmit(customers.get(spinner_shop.getSelectedItemPosition()).getId(),type,txt_descripton);
                        }
                    }
                    else{
                        //TODO:: edit visitplan list
                    }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try{
            onSubmit = (OnSubmit) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + "must implement OnSubmit listener");
        }
    }
}
