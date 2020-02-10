package com.group4.patientdoctorconsultation.common;

import android.arch.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class LiveDocument<T extends IndexedFirestoreResource>
        extends LiveData<FailableResource<T>> implements EventListener<DocumentSnapshot> {

    private ListenerRegistration listenerRegistration;
    private final DocumentReference documentReference;
    private final Class<T> objectType;

    public LiveDocument(DocumentReference documentReference, Class<T> objectType) {
        this.documentReference = documentReference;
        this.objectType = objectType;
    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException exception) {
        if (exception != null) {
            setValue(new FailableResource<>(exception));
            return;
        }

        T object = snapshot.toObject(objectType);
        if (object != null){
            object.setId(snapshot.getId());
            setValue(new FailableResource<>(object));
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        listenerRegistration = documentReference.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if(listenerRegistration != null){
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }
}
