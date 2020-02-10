package com.group4.patientdoctorconsultation.common;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public final class LiveQuery<T extends IndexedFirestoreResource>
        extends LiveData<FailableResource<List<T>>> implements EventListener<QuerySnapshot> {

    private final Query query;
    private final Class<T> type;
    private ListenerRegistration listenerRegistration;

    public LiveQuery(Query query, Class<T> type) {
        this.query = query;
        this.type = type;
    }

    @Override
    public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException exception) {
        if (exception != null) {
            setValue(new FailableResource<>(exception));
            return;
        }
        setValue(new FailableResource<>(documentToList(snapshots)));
    }

    @Override
    protected void onActive() {
        super.onActive();
        listenerRegistration = query.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }

    @NonNull
    private List<T> documentToList(QuerySnapshot snapshots) {
        final List<T> convertedList = new ArrayList<>();

        for (DocumentSnapshot document : snapshots.getDocuments()) {
            T object = document.toObject(type);
            if (object != null){
                object.setId(document.getId());
            }
            convertedList.add(object);
        }

        return convertedList;
    }
}
