package com.group4.patientdoctorconsultation.utilities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.group4.patientdoctorconsultation.data.repository.DataPacketRepository;
import com.group4.patientdoctorconsultation.data.repository.ProfileRepository;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModel;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModelFactory;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModel;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModelFactory;

public class DependencyInjector {

    private static FirebaseFirestore getFirestore() {
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(settings);

        return firestore;
    }

    private static FirebaseStorage getFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    private static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    private static ProfileRepository getProfileRepository() {
        return ProfileRepository.getInstance(getFirestore());
    }

    private static DataPacketRepository getDataPacketRepository() {
        return DataPacketRepository.getInstance(getFirestore(), getFirebaseStorage());
    }

    private static DataPacketViewModelFactory getDataPacketViewModelFactory() {
        return new DataPacketViewModelFactory(getDataPacketRepository(), getFirebaseAuth());
    }

    private static ProfileViewModelFactory getProfileViewModelFactory() {
        return new ProfileViewModelFactory(getProfileRepository(), getFirebaseAuth());
    }

    public static ProfileViewModel provideProfileViewModel(FragmentActivity activity) {
        return ViewModelProviders
                .of(activity, getProfileViewModelFactory())
                .get(ProfileViewModel.class);
    }

    public static DataPacketViewModel provideDataPacketViewModel(FragmentActivity activity) {
        return ViewModelProviders
                .of(activity, getDataPacketViewModelFactory())
                .get(DataPacketViewModel.class);
    }

}
