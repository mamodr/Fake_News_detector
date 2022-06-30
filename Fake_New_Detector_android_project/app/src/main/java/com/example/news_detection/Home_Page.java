package com.example.news_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button URL_predict=findViewById(R.id.predic_URL);
        Button text_predict=findViewById(R.id.predic_text);
        Button trending=findViewById(R.id.trending_searches);

        Intent intent1 = new Intent (this, predict_url.class);
        Intent intent2 = new Intent (this, predict_text.class);
        Intent intent3 = new Intent (this, trending_searches.class);
        URL_predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent1);
            }
        });


       text_predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent2);
            }
        });

        trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent3);
            }
        });


    }
}