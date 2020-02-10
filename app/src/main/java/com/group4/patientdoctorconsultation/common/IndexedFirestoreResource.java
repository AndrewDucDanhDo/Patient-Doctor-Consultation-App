package com.group4.patientdoctorconsultation.common;

import com.google.firebase.firestore.Exclude;

public abstract class IndexedFirestoreResource {

    private String id;

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getId() {
        return id;
    }
}
