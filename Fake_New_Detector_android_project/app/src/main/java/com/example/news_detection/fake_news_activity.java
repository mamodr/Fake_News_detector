package com.example.news_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class fake_news_activity extends AppCompatActivity {

    Handler timer=new Handler();
    Handler timer1=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingdialog loadingdialog=new loadingdialog(this);

        loadingdialog.startLoadingDialog();
        timer.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadingdialog.dismissDialog();
                setContentView(R.layout.activity_fake_news);
            }
        },7000);



        timer1.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent start=new Intent(fake_news_activity.this,Home_Page.class);
                startActivity(start);
            }
        },10000);

    }
}