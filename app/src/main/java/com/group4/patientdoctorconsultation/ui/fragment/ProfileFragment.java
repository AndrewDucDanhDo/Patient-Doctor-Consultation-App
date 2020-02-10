package com.group4.patientdoctorconsultation.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.FirestoreFragment;
import com.group4.patientdoctorconsultation.databinding.FragmentProfileBinding;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModel;

public class ProfileFragment extends FirestoreFragment {

    private ProfileViewModel viewModel;
    private FragmentProfileBinding binding;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
         binding.setProfileHandler(this);
         observeProfile();
         return binding.getRoot();
    }

    private void observeProfile(){
        viewModel = DependencyInjector.provideProfileViewModel(requireActivity());
        viewModel.getProfile().observe(this, profile -> {
            if(profile != null && handleFirestoreResult(profile)){
                binding.setProfile(profile.getResource());
            }
        });
    }

    public void submit(View view){ //Do not remove parameter, required for data binding
        viewModel.updateProfile(binding.getProfile()).observe(this, isComplete -> {
            if(isComplete != null && handleFirestoreResult(isComplete) && isComplete.getResource()){
                Toast.makeText(requireContext(), "Saved",Toast.LENGTH_LONG).show();
            }
        });
    }
}
