package com.group4.patientdoctorconsultation.data.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group4.patientdoctorconsultation.common.LiveDocument;
import com.group4.patientdoctorconsultation.common.LiveQuery;
import com.group4.patientdoctorconsultation.common.LiveResultListener;
import com.group4.patientdoctorconsultation.data.model.Profile;

public class ProfileRepository {

    private static ProfileRepository instance;
    private final CollectionReference profileCollection;

    private ProfileRepository(FirebaseFirestore firestore) {
        this.profileCollection = firestore.collection(Profile.COLLECTION_NAME);
    }

    public LiveDocument<Profile> profileFromUserId(String userId){
        return new LiveDocument<>(
                profileCollection.document(userId),
                Profile.class
        );
    }

    public LiveResultListener<Boolean> updateProfile(Profile profile){
        LiveResultListener<Boolean> liveCompleteListener = new LiveResultListener<>();
        profileCollection.document(profile.getId())
                .set(profile)
                .addOnSuccessListener(runnable -> liveCompleteListener.onSuccess(true))
                .addOnFailureListener(liveCompleteListener);
        return liveCompleteListener;
    }

    public LiveQuery<Profile> getLinkedProfiles(String profileId){
        return new LiveQuery<>(
                profileCollection.whereEqualTo(Profile.FIELD_LINKED_PROFILES + "." + profileId, true),
                Profile.class
        );
    }

    public static synchronized ProfileRepository getInstance(FirebaseFirestore firestore){
        if(instance == null){
            instance = new ProfileRepository(firestore);
        }

        return instance;
    }


}
