package com.example.asm_ph38422;

import okhttp3.MultipartBody;
import retrofit2.Call;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {
    String DOMAIN = "http://192.168.10.105:3000/";

    @GET("/api/get-list-car")
    Call<List<CarModel>> getCars();

    @DELETE("/api/delete-car-by-id/{id}")
    Call<CarModel> deleteCar(@Path("id") String id);

    @POST("/api/post-car")
    Call<CarModel> createCar(@Body CarModel car);

    //DEMO
    @Multipart
    @POST("/api/post-car")
    Call<CarModel> createCar2(
            @Part MultipartBody.Part uri
    );

    @PUT("/api/update-car-by-id/{id}")
    Call<CarModel> updateCar(@Path("id") String id, @Body CarModel car);


}
