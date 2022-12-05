package com.example.tallervideojuego.modelo.Api;


import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.json.JSONArray;
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
    private SyncDB syncDB;
    private static String token = "";


    public  Api(){
        syncDB = new SyncDB();
    }

    /**
     * Valida si un token de acceso a la base de datos es valido
     *
     * */
    public static void isToken(String token, FutureCallback<String> callback) {
        ListenableFuture<String> asyncTask = lExecService.submit(() -> {
            String res;

            Request request = new Request.Builder()
                    .url("https://playdi.ml/api/validate/")
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
                    res = res_.getString("res");
                } catch (JSONException e) {
                    res = "02";
                }

            }catch (IOException e) {
                res = "01";
            }

            return res;
        });
        Futures.addCallback(asyncTask,callback,lExecService);

    }
    /**
     * Guarda un token para el uso de la appi con el cual se acreditara en la api
     * */

    public static void setToken(String token) {
        Api.token=token;
    }


    /**
     * Inicio de secion clasico, se envian los datos de login a la Api esperando su respuesta
     * */
    public void login(String email, String password, FutureCallback<String> callback)  {
        ListenableFuture<String> asyncTask = lExecService.submit(() -> {
            String res;
            RequestBody formBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url("https://playdi.ml/api/login")
                    .header("Accept", "application/json")
                    .addHeader("User-Agent", "OkHttp Headers.java")
                    .post(formBody)
                    .build();

            try {
                Call call = client.newCall(request);
                Response response = call.execute();

                try {
                    JSONObject res_ = new JSONObject(response.body().string());
                    Api.token = res_.getString("token");
                    res = res_.getString("token");
                } catch (JSONException e) {//control de errores
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

    /**
     * Registro de un nuevo usuario en la Api
     * */
    public void register(String name,String email, String password, FutureCallback<String> callback){

        ListenableFuture<String> asyncTask = lExecService.submit(() -> {
            String res;
            RequestBody formBody = new FormBody.Builder()
                    .add("name", name)
                    .add("email", email)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url("https://playdi.ml/api/register")
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
    /**
     * sincroniza la base de datos local con la api, de manera que se sustitulle la antigua por la mas reciente ya sea la local o la de la api
     * */
    public void sincronizar( FutureCallback<String> call_) {

        //inicio de la comunicacion enviando lo que tengo y esperando reciibir que debo guardar o si la api guardo mi version
        ListenableFuture<JSONObject> asyncTask = lExecService.submit(() -> {
            JSONObject res_ = null;


            String auth_token = "Bearer "+Api.token;
            String json = syncDB.makeJson();

            RequestBody formBody;
            if (json.length()>0){
                 formBody = new FormBody.Builder()
                        .add("cambios", json)
                        .build();
            }else {
                 formBody = new FormBody.Builder().build();
            }
            Request request = new Request.Builder()
                    .url("https://playdi.ml/api/cambios")
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
                    System.out.println("--------------"+res_.toString());

                } catch (JSONException e) {
                    System.out.println("--------------");
                    System.out.println(response.body().string());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("===================");
                    System.out.println(response.body().string());
                    e.printStackTrace();
                }
            } catch (IOException e) {
                res_ = null;
            }

            return res_;

        });

        //callback de la primer peticion, recibe la respuesta de la Api y si debe de guardar datos enviados por la Api ejecuta el metodo syncDB y ejecuta el callbackk
        FutureCallback callback = new FutureCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    if (result == null){
                        call_.onSuccess("01");
                        return;}//ejecuta el callback
                    switch (result.getInt("type")){
                        case 0:
                            JSONArray cartas = result.getJSONArray("cartas");
                            JSONArray categorias = result.getJSONArray("categorias");
                            JSONArray cambios = result.getJSONArray("cambios");

                            syncDB.sync(cartas,categorias,cambios);
                            break;
                        case 1://la base de datos local se guardo en la api

                            break;
                        case 2://las dos bases de datos son iguales
                            break;
                        default:
                            break;
                    }

                }catch (JSONException e){
                    call_.onSuccess("01");//ejecuta el callback
                }
                call_.onSuccess("loged");//ejecuta el callback
            }

            @Override
            public void onFailure(Throwable t) {

                call_.onFailure(t);
            }
        };
        Futures.addCallback(asyncTask, callback, lExecService);


    }


    //callback de la primer peticion, recibe la respuesta de la Api y si debe de guardar datos enviados por la Api ejecuta el metodo syncDB 
    public void sincronizar() {

        ListenableFuture<JSONObject> asyncTask = lExecService.submit(() -> {
            JSONObject res_ = null;


            String auth_token = "Bearer "+Api.token;
            String json = syncDB.makeJson();
            RequestBody formBody;
            if (json.length()>0){
                formBody = new FormBody.Builder()
                        .add("cambios", json)
                        .build();
            }else {
                formBody = new FormBody.Builder().build();
            }
            Request request = new Request.Builder()
                    .url("https://playdi.ml/api/cambios")
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
                    System.out.println(response.body().string());

                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println(response.body().string());

                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(json);
            }

            return res_;

        });
        FutureCallback callback = new FutureCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    switch (result.getInt("type")){
                        case 0:
                            JSONArray cartas = result.getJSONArray("cartas");
                            JSONArray categorias = result.getJSONArray("categorias");
                            JSONArray cambios = result.getJSONArray("cambios");
                            syncDB.sync(cartas,categorias,cambios);


                            break;
                        case 1:

                            return;
                        case 2:
                            return;
                        default:
                            break;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        Futures.addCallback(asyncTask, callback, lExecService);
    }
}