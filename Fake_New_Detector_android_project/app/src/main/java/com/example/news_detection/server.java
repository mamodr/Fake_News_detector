package com.example.news_detection;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class server
{
    public static String respons;

    public static void  sendRequest(String type,String method,String paramname,String param){

        respons="";

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
                respons=(response.body().string());


                // Run view-related code back on the main thread.


            }


        });


    }

}

