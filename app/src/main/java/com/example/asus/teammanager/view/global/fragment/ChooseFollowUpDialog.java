package com.example.asus.teammanager.view.global.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.example.asus.teammanager.R;

public class ChooseFollowUpDialog extends DialogFragment{

    private int type;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_choose_follow_up,null);
        builder.setView(view);

        builder.setTitle("Pick followup type");

        TextView followup_end_user = view.findViewById(R.id.followup_end_user);
        TextView followup_customer = view.findViewById(R.id.followup_customer);


        followup_end_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                Intent intent = new Intent()
                        .putExtra("TYPE", type);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
            }
        });

        followup_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                Intent intent = new Intent()
                        .putExtra("TYPE", type);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
            }
        });

        builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
