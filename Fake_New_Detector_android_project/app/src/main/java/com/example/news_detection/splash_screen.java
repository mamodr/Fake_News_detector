package com.example.news_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash_screen extends AppCompatActivity {


    Handler timer=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       // getSupportActionBar().hide();

        timer.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent start=new Intent(splash_screen.this,Home_Page.class);
                startActivity(start);
            }
        },1000);

    }
}