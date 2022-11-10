package com.example.tallervideojuego.modelo.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends Thread{
    final OkHttpClient client = new OkHttpClient();
    private String res,email, password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void run()  {

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
                    JSONObject res = new JSONObject(response.body().string());
                    this.res = res.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
//                    res = response.body().string();
                    res = "false";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }



    }

    public String getRes() {
        return res;
    }
}
