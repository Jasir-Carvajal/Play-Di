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


public class Api {

    final OkHttpClient client = new OkHttpClient();
    private String token;





    public String login(String email, String password) throws IOException {

        Login login = new Login(email, password);
        login.start();
        try {
            login.join();
            return login.getRes();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }


    }

    public String register(String name,String email, String password) throws IOException {

        Register register = new Register(name, email,  password);
        register.start();
        try {
            register.join();
            return register.getRes();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }


    }


}