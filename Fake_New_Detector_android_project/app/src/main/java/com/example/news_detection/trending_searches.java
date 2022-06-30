package com.example.news_detection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class trending_searches extends AppCompatActivity {

    @BindView(R.id.trendyurl_recview)
    RecyclerView trendyurl_recview;

    List<url_result> urls_result_list =new ArrayList<>();
    url_result u1=new url_result("https://www.bbc.com/news/uk-politics-61945712","True","",20);
    url_result u2=new url_result("https://www.bbc.com/news/entertainment-arts-61943613","True","",13);
    url_result u3=new url_result("https://edition.cnn.com/2022/06/25/asia/china-navy-aircraft-carrier-analysis-intl-hnk-ml-dst/index.html","True","",4);
    url_result u4=new url_result("https://edition.cnn.com/2022/06/26/americas/colombia-stadium-collapse/index.html","True","",2);
    url_result u5=new url_result("https://www.washingtonpost.com/national-security/2022/06/28/cassidy-hutchinson-trump-criminal/?itid=hp-top-table-main","True","",1);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        urls_result_list.add(u1);
        urls_result_list.add(u2);
        urls_result_list.add(u3);
        urls_result_list.add(u4);
        urls_result_list.add(u5);
        setContentView(R.layout.activity_trending_searches);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager( this);


    trendyurl_recview.setLayoutManager(layoutManager);
    trendyurl_recview.addItemDecoration (new DividerItemDecoration(  this, layoutManager.getOrientation()));
        trendyurl_recview.setAdapter (new url_recyclerview_adabter(this,urls_result_list));

    }

}