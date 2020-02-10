package com.group4.patientdoctorconsultation.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.group4.patientdoctorconsultation.data.model.DataPacketItem;

public abstract class PacketItemDialog extends DialogFragment {

    public static final String EXTRA_RESULT = "extra_result";
    AlertDialog alertDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getTargetFragment() == null) {
            throw new NullPointerException("PacketItemDialog must implement fragment");
        }

        alertDialog = new AlertDialog.Builder(requireActivity())
                .setTitle(getTitle())
                .setView(getView(getTargetFragment().getLayoutInflater()))
                .setNegativeButton("CANCEL", (dialog, which) -> {
                    dialog.cancel();
                    (getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                })
                .setPositiveButton("SAVE", (dialogInterface, i) -> {
                    Intent result = new Intent();
                    result.putExtra(EXTRA_RESULT, new DataPacketItem(getPacketItemType(), getDialogResult(), getDialogDisplayResult()));
                    (getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
                })
                .create();

        alertDialog.setOnShowListener(dialogInterface -> {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(saveEnabledByDefault());
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(cancelEnabledByDefault());
        });

        return alertDialog;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    protected boolean saveEnabledByDefault() {
        return true;
    }

    protected boolean cancelEnabledByDefault() {
        return true;
    }

    protected String getDialogDisplayResult(){
        return getDialogResult();
    }

    protected String getTitle(){
        return getPacketItemType().toString();
    }

    abstract public String getDialogResult();

    abstract public DataPacketItem.DataPacketItemType getPacketItemType();

    abstract public View getView(LayoutInflater inflater);
}
