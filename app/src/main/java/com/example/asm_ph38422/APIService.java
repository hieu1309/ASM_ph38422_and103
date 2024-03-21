package com.example.asm_ph38422;

import retrofit2.Call;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    String DOMAIN = "http://192.168.1.200:3000/";

    @GET("/api/list")
    Call<List<CarModel>> getCars();

    @GET("/xoa/{id}")
    Call<Void> deleteCar(@Path("id") String id);
}
