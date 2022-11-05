package com.example.tallervideojuego.modelo.Api;




import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Api {

    public static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");
    final OkHttpClient client = new OkHttpClient();
    private String token;





    public String login(String email, String password) throws IOException {


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


        Call call = client.newCall(request);
        Response response = call.execute();

        try {
            JSONObject res = new JSONObject(response.body().string());
            token = res.getString("token");
            return token;
        } catch (JSONException e) {
            e.printStackTrace();
            return "false";
        }


    }

    public String register(String name,String email, String password) throws IOException {

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
        Response response = call.execute();

        return response.body().string();

    }

}