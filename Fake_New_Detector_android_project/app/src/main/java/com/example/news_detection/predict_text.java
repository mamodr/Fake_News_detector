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
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class predict_text extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private OkHttpClient okHttpClient;
    private TextView textView_response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.predict_text);

        editText = findViewById(R.id.edt_news);
        button = findViewById(R.id.btn_predict);
        okHttpClient = new OkHttpClient();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest("POST", "predict", "txt", editText.getText().toString());




            }


        });
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
            public void onResponse(Call call, final Response response) throws IOException {

                // Read data on the worker thread


                // Log.e("mmmmmmmmmmmmm",response.body().string());
                final String  respons=(response.body().string());


                predict_text.this.runOnUiThread(()->show_Result(respons));




            }


        });

        Log.e("mmmmmmmmmmmmmres",res[0]);

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
