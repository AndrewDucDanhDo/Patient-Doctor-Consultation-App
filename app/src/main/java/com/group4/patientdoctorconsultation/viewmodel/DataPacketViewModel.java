package com.group4.patientdoctorconsultation.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageMetadata;
import com.group4.patientdoctorconsultation.common.FailableResource;
import com.group4.patientdoctorconsultation.common.LiveResultListener;
import com.group4.patientdoctorconsultation.data.model.DataPacket;
import com.group4.patientdoctorconsultation.data.repository.DataPacketRepository;

import java.io.InputStream;
import java.util.List;

public class DataPacketViewModel extends ViewModel implements FirebaseAuth.AuthStateListener {

    private final DataPacketRepository dataPacketRepository;

    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<String> profileId = new MutableLiveData<>();

    private final LiveData<FailableResource<List<DataPacket>>> dataPackets;
    private final MutableLiveData<String> activePacketId;
    private final LiveData<FailableResource<DataPacket>> activePacket;


    DataPacketViewModel(DataPacketRepository dataPacketRepository, FirebaseAuth firebaseAuth) {
        this.dataPacketRepository = dataPacketRepository;
        this.firebaseAuth = firebaseAuth;
        firebaseAuth.addAuthStateListener(this);

        activePacketId = new MutableLiveData<>();
        dataPackets = Transformations.switchMap(profileId, dataPacketRepository::getDataPacketsByPatientId);
        activePacket = Transformations.switchMap(activePacketId, dataPacketRepository::getDataPacketById);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        this.profileId.setValue(
                firebaseAuth.getCurrentUser() == null ? "" : firebaseAuth.getUid()
        );
    }

    @Override
    protected void onCleared() {
        firebaseAuth.removeAuthStateListener(this);
        super.onCleared();
    }

    public LiveResultListener<Boolean> updateDataPacket(DataPacket dataPacket){
        return dataPacketRepository.updateDataPacket(dataPacket);
    }

    public LiveData<FailableResource<List<DataPacket>>> getDataPackets(){
        return dataPackets;
    }

    public LiveData<FailableResource<DataPacket>> getActivePacket() {
        return activePacket;
    }

    public LiveResultListener<DocumentReference> addDataPacket(DataPacket dataPacket){
        dataPacket.setPatientId(profileId.getValue());
        return dataPacketRepository.addDataPacket(dataPacket);
    }

    public void setActivePacketId(String activePacketId){
        this.activePacketId.setValue(activePacketId);
    }

    public LiveResultListener<Uri> uploadAttachment(String fileName, InputStream inputStream){
        return dataPacketRepository.uploadAttachment(activePacketId.getValue(), fileName, inputStream);
    }

    public LiveResultListener<StorageMetadata> getFileMetaData(String fileReference){
        return dataPacketRepository.getFileMetadata(fileReference);
    }
}
