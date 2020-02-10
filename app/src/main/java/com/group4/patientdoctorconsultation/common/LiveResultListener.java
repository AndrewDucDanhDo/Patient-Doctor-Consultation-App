package com.group4.patientdoctorconsultation.common;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LiveResultListener<T> extends LiveData<FailableResource<T>>
    implements OnSuccessListener<T>, OnFailureListener {

    @Override
    public void onFailure(@NonNull Exception e) {
        setValue(new FailableResource<>(e));
    }

    @Override
    public void onSuccess(T t) {
        setValue(new FailableResource<>(t));
    }
}
