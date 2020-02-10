package com.group4.patientdoctorconsultation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.group4.patientdoctorconsultation.data.repository.DataPacketRepository;


public class DataPacketViewModelFactory implements ViewModelProvider.Factory {

    private final DataPacketRepository dataPacketRepository;
    private final FirebaseAuth firebaseAuth;

    public DataPacketViewModelFactory(DataPacketRepository dataPacketRepository, FirebaseAuth firebaseAuth) {
        this.dataPacketRepository = dataPacketRepository;
        this.firebaseAuth = firebaseAuth;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(DataPacketViewModel.class)){
            return (T) new DataPacketViewModel(dataPacketRepository, firebaseAuth);
        }else{
            throw new IllegalArgumentException("View Model not found");
        }
    }

}
