package com.group4.patientdoctorconsultation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.group4.patientdoctorconsultation.data.repository.ProfileRepository;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private final ProfileRepository profileRepository;
    private final FirebaseAuth firebaseAuth;

    public ProfileViewModelFactory(ProfileRepository profileRepository, FirebaseAuth firebaseAuth) {
        this.profileRepository = profileRepository;
        this.firebaseAuth = firebaseAuth;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ProfileViewModel.class)){
            return (T) new ProfileViewModel(profileRepository, firebaseAuth);
        }else{
            throw new IllegalArgumentException("View Model not found");
        }
    }

}
