package com.example.feeplusfix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseApp.initializeApp(this);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(fUser != null) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    Intent t = new Intent (getApplicationContext(), FirstPageActivity.class);
                    t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(t);
                }
            }
        },1300);

    }
}