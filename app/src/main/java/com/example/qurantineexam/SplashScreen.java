package com.example.qurantineexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        timer = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this){
                        wait(5000);
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    if(FirebaseAuth.getInstance().getCurrentUser() ==null) {
                        startActivity(new Intent(SplashScreen.this,
                                LogIn.class));
                    }else{
                        startActivity(new Intent(SplashScreen.this, DashboardAct.class));
                    }
                    finish();
                }
            }
        };
        timer.start();



    }
}