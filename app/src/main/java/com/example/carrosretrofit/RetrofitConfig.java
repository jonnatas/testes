package com.example.carrosretrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitConfig {
    @GET("notes/{id}")
    Call<Carro> getWithId(@Path("id") String id);

    @GET("notes")
    Call<List<Carro>> getAll();

    @POST("notes")
    Call<Carro> salvarCarro(@Body Carro carro);

    @PUT("notes/{id}")
    Call<Carro> updateCarro(@Path("id") String id, @Body Carro carro);

    @DELETE("notes/{id}")
    Call<Carro> deleteCarro(@Path("id") String id);
}
