package com.group4.patientdoctorconsultation.ui.dialogfragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.LocationServices;
import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.PacketItemDialog;
import com.group4.patientdoctorconsultation.data.model.DataPacketItem;

public class LocationDialogFragment extends PacketItemDialog {

    TextView locationText;

    @Override
    public String getDialogResult() {
        return locationText.getText().toString();
    }

    @SuppressLint("MissingPermission")
    @Override
    public View getView(LayoutInflater inflater) {
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.fragment_dialog_location, null);
        ProgressBar loadingIcon = view.findViewById(R.id.loading_icon);

        locationText = view.findViewById(R.id.location_text);

        LocationServices.getFusedLocationProviderClient(requireActivity()).getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        locationText.setText(getLocationString(location));
                        getAlertDialog().getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        loadingIcon.setVisibility(View.GONE);
                    }
                });

        return view;
    }

    @Override
    public DataPacketItem.DataPacketItemType getPacketItemType() {
        return DataPacketItem.DataPacketItemType.LOCATION;
    }

    @Override
    protected boolean saveEnabledByDefault() {
        return false;
    }

    private String getLocationString(Location location){
        return "http://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
    }
}
