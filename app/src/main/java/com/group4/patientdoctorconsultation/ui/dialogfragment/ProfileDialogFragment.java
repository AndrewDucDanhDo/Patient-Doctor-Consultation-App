package com.group4.patientdoctorconsultation.ui.dialogfragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.FirestoreFragment;
import com.group4.patientdoctorconsultation.common.PacketItemDialog;
import com.group4.patientdoctorconsultation.data.adapter.ProfileAdapter;
import com.group4.patientdoctorconsultation.data.model.DataPacketItem;
import com.group4.patientdoctorconsultation.data.model.Profile;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModel;

public class ProfileDialogFragment extends PacketItemDialog {

    RecyclerView profileList;
    Profile profile;

    @Override
    public String getDialogResult(){
        return profile != null ? profile.getId() : "";
    }

    @Override
    protected String getDialogDisplayResult() {
        return profile != null ? profile.getUserName() : "";
    }

    @Override
    public View getView(LayoutInflater inflater) {

        if (!(getTargetFragment() instanceof FirestoreFragment)) {
            throw new IllegalStateException("Host fragment must extend FireStoreFragment");
        }

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_profile, null);
        profileList = view.findViewById(R.id.profile_list);

        ProfileViewModel profileViewModel = DependencyInjector.provideProfileViewModel(requireActivity());

        ProfileAdapter profileAdapter = new ProfileAdapter(item -> profile = item);
        RecyclerView profileList = view.findViewById(R.id.profile_list);
        profileList.setLayoutManager(new LinearLayoutManager(requireContext()));
        profileList.setAdapter(profileAdapter);
        profileViewModel.getLinkedProfiles().observe(this, profiles -> {
            if(profiles != null && ((FirestoreFragment) getTargetFragment()).handleFirestoreResult(profiles)){
                profileAdapter.replaceListItems(profiles.getResource());
            }
        });

        return view;
    }

    @Override
    public DataPacketItem.DataPacketItemType getPacketItemType() {
        return DataPacketItem.DataPacketItemType.NOTE;
    }

    @Override
    protected String getTitle() {
        return "Select Doctor";
    }
}
