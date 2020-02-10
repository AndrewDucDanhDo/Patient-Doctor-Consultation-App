package com.group4.patientdoctorconsultation.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.ProfileViewModel;

import java.util.Collections;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class NavigationActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        initialiseViewModel();
        initialiseNavigationController();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK){
            startSignIn();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigation.findNavController(this, R.id.navigation_fragment).navigateUp();
        return super.onSupportNavigateUp();
    }

    private void initialiseNavigationController(){
        NavController navController = Navigation.findNavController(this, R.id.navigation_fragment);
        setSupportActionBar(findViewById(R.id.toolbar));
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.navigation_view), navController);
    }

    private void initialiseViewModel(){
        ProfileViewModel viewModel = DependencyInjector.provideProfileViewModel(this);
        viewModel.getIsSignedIn().observe(this, isSignedIn -> {
            if(isSignedIn != null && !isSignedIn){
                startSignIn();
            }
        });
    }

    private void startSignIn() {
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                ))
                .setIsSmartLockEnabled(false)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }
}
