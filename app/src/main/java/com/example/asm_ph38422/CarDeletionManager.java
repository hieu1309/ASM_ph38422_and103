package com.example.asm_ph38422;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarDeletionManager {
    public void deleteCar(String id) {
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<Void> call = apiService.deleteCar(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xóa thành công
                } else {
                    // Xóa không thành công
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xảy ra lỗi
            }
        });
    }
}
