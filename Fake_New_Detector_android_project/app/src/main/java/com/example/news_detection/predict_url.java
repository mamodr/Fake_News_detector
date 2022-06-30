package com.example.news_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nguyencse.URLEmbeddedView;
import com.nguyencse.URLEmbeddedData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class predict_url extends AppCompatActivity {


    private TextView result;
    private EditText URL;
    private Button fetch;
    private Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_url);



        fetch = (Button) findViewById(R.id.fetch);
        URL = (EditText) findViewById(R.id.edt_url);
        proceed=(Button)findViewById(R.id.proceed_btn);




        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=URL.getText().toString();
                getBodyText( url);
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url=URL.getText().toString();

                URL.setVisibility(View.INVISIBLE);
                fetch.setVisibility(View.INVISIBLE);
                proceed.setVisibility(View.VISIBLE);


                URLEmbeddedView urlEmbeddedView = findViewById(R.id.url_uev2);
                urlEmbeddedView.setVisibility(View.VISIBLE);
                urlEmbeddedView.setURL(url, new URLEmbeddedView.OnLoadURLListener() {
                    @Override
                    public void onLoadURLCompleted(URLEmbeddedData data) {
                        urlEmbeddedView.title(data.getTitle());
                        urlEmbeddedView.description(data.getDescription());
                        urlEmbeddedView.host(data.getHost());
                        urlEmbeddedView.thumbnail(data.getThumbnailURL());
                        urlEmbeddedView.favor(data.getFavorURL());
                    }
                });


            }
        });




    }



    private void  getBodyText(String url) {

      //  final String[] content = {""};
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {

                    Document doc = Jsoup.connect(url).get();

                    Element body = doc.body();
                    builder.append(body.text());

                } catch (Exception e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      final String content =builder.toString();
                        Log.e("CCCCCCCCCCCCCCCCCCCCCCCCCCCc",content);
                        sendRequest("POST", "predict", "txt", content);
                    }
                });
            }
        }).start();


    }

      void  sendRequest(String type,String method,String paramname,String param){



        /* if url is of our get request, it should not have parameters according to our implementation.
         * But our post request should have 'name' parameter. */
        //  final String responseData ;
        String url="http://192.168.1.106:5000/";
        String fullURL=url+"/"+method+(param==null?"":"/"+param);
        Request request;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();

        /* If it is a post request, then we have to pass the parameters inside the request body*/
        if(type.equals("POST")){
            RequestBody formBody = new FormBody.Builder()
                    .add(paramname, param)
                    .build();

            request=new Request.Builder()
                    .url(fullURL)
                    .post(formBody)
                    .build();
        }else{
            /*If it's our get request, it doen't require parameters, hence just sending with the url*/
            request = new Request.Builder()
                    .url(fullURL)
                    .build();
        }
        /* this is how the callback get handled */
        final String[] res = {""};
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }






            @Override
            public void onResponse(Call call,  Response response) throws IOException {

                // Read data on the worker thread


                // Log.e("mmmmmmmmmmmmm",response.body().string());
               String  respons=(response.body().string());
                Log.e("mmmmmmmmmmmmm",respons);
                predict_url.this.runOnUiThread(()->show_Result(respons));





            }


        });


    }

    public void show_Result (String r){

        Intent intent1 = new Intent (this, real_news_activity.class);
        Intent intent2 = new Intent (this, fake_news_activity.class);
        if(r.equals("[[ True]]"))
            startActivity(intent1);
        else
            startActivity(intent2);

    }

}