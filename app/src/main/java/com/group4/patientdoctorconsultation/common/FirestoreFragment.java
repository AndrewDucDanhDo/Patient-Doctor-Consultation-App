package com.group4.patientdoctorconsultation.common;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

public abstract class FirestoreFragment extends Fragment {

    public boolean handleFirestoreResult(FailableResource resource){

        if(resource.isSuccessful() && resource.getResource() != null){
            return true;
        }else if(resource.getError() != null) {
            Log.w("TAG", resource.getError());
            Toast.makeText(
                    requireContext(), resource.getError().getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        return false;
    }

}
