package com.example.tallervideojuego.modelo.Api;

import android.util.Pair;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Api {

    public static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");
    final OkHttpClient client = new OkHttpClient();

    public String post() throws IOException {


        RequestBody formBody = new FormBody.Builder()
                .add("email", "jean@test.com")
                .add("password", "test")
                .build();

        Request request = new Request.Builder()
                .url("http://playdi.ml/api/login/")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }



}