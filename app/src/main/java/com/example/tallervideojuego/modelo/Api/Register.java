package com.example.tallervideojuego.modelo.Api;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends Thread{
    final OkHttpClient client = new OkHttpClient();
    private String res, name, email,  password;

    public Register(String name,String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run() {
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
            return;
        }


        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            res = e.getMessage();
            return;
        }
    }

    public String getRes() {
        return res;
    }
}

