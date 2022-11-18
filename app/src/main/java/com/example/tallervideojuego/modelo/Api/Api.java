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

    private final static OkHttpClient client = new OkHttpClient();
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    private static ListeningExecutorService lExecService = MoreExecutors.listeningDecorator(executor);

    private static String token = "30|oVFV7b84rf3J5aTIwD2ycocYWTXpChxRdNolw5vY";


    public  Api(){

    }

    public static void isToken(String token, FutureCallback<String> callback) {
        ListenableFuture<String> asyncTask = lExecService.submit(() -> {
            String res;

            Request request = new Request.Builder()
                    .url("http://playdi.ml/api/validate/")
                    .header("Accept", "application/json")
                    .addHeader("User-Agent", "OkHttp Headers.java")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Bearer "+token)
                    .build();

            try {
                Call call = client.newCall(request);
                Response response = call.execute();

                try {
                    JSONObject res_ = new JSONObject(response.body().string());

                    System.out.println(res_.toString());
                    res = res_.getString("res");
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

    public static void setToken(String token) {
        Api.token=token;
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
                    System.out.println(res_.toString());
                    Api.token = res_.getString("token");
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
            Api.token = new JSONObject(res).getString("token");
            return res;
        });
        Futures.addCallback(asyncTask,callback,lExecService);

    }

    public void send_Cambios() {

        ListenableFuture<JSONObject> asyncTask = lExecService.submit(() -> {
            JSONObject res_ = null;
            SyncDB syncDB = new SyncDB();
            System.out.println("\n+++++++++\n");
            System.out.println(syncDB.makeJson());
            System.out.println("\n+++++++++\n");
            String auth_token = "Bearer "+Api.token;

            RequestBody formBody = new FormBody.Builder()
                    .add("cambios", syncDB.makeJson())
                    .build();

            Request request = new Request.Builder()
                    .url("http://playdi.ml/api/cambios")
                    .header("Accept", "application/json")
                    .header("Authorization", auth_token)
                    .addHeader("User-Agent", "OkHttp Headers.java")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .post(formBody)
                    .build();

            try {
                Call call = client.newCall(request);
                Response response = call.execute();
                try {
                    res_ = new JSONObject(response.body().string());

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res_;

        });
        FutureCallback callback = new FutureCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                System.out.println("\n+++++++++\n");
                System.out.println(result.toString());
                System.out.println("\n+++++++++\n");
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        Futures.addCallback(asyncTask, callback, lExecService);
    }
}