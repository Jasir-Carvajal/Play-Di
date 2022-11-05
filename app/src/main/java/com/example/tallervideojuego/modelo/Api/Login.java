package com.example.tallervideojuego.modelo.Api;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Login {

    public static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");
    final OkHttpClient client = new OkHttpClient();

    public String post(String url) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("email", "jean@test.com")
                .add("password", "test")
                .build();

        Request request = new Request.Builder()
                .url(url)
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