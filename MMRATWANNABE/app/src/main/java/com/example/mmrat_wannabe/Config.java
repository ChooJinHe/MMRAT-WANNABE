package com.example.mmrat_wannabe;

import android.content.Context;

import com.example.mmrat_wannabe.retrofit.ApiService;

import okhttp3.OkHttpClient;

public class Config {

    public static String BASE_URL = "192.168.1.120";

    public static String PORT = "8765";

    public static Context context;

    public static OkHttpClient client;

    public static ApiService apiService;

}
