package com.example.tallervideojuego.modelo.Api;


import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Api {

    final OkHttpClient client = new OkHttpClient();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private ListeningExecutorService lExecService = MoreExecutors.listeningDecorator(executor);

    private String token;


    public  Api(){

    }



    public void login(String email, String password, FutureCallback<String> callback)  {
        ListenableFuture<String> asyncTask = lExecService.submit(() -> {
            String res;
            RequestBody formBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url("http://playdi.ml/api/login")
                    .header("Accept", "application/json")
                    .addHeader("User-Agent", "OkHttp Headers.java")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .post(formBody)
                    .build();

            try {
                Call call = client.newCall(request);
                Response response = call.execute();

                try {
                    JSONObject res_ = new JSONObject(response.body().string());
                    res = res_.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                    res = "false";
                } catch (IOException e) {
                    e.printStackTrace();
                    res = "false";
                }
            }catch (IOException e) {
                e.printStackTrace();
                res = "false";
            }

            return res;
        });
        Futures.addCallback(asyncTask,callback,lExecService);
    }


    public void register(String name,String email, String password, FutureCallback<String> callback){

        ListenableFuture<String> asyncTask = lExecService.submit(() -> {
            String res;
            RequestBody formBody = new FormBody.Builder()
                    .add("name", name)
                    .add("email", email)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url("http://playdi.ml/api/register")
                    .header("Accept", "application/json")
                    .addHeader("User-Agent", "OkHttp Headers.java")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .post(formBody)
                    .build();


            Call call = client.newCall(request);

            Response response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
                res = e.getMessage();
            }


            try {
                res = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                res = e.getMessage();
            }
            return res;
        });
        Futures.addCallback(asyncTask,callback,lExecService);


       /* Register register = new Register(name, email,  password);
        register.start();
        try {
            while (register.getRes()==null){
                Thread.sleep(100);
            }
            //register.join();
            return register.getRes();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }*/


    }


}