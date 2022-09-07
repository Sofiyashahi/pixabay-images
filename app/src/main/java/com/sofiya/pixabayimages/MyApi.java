package com.sofiya.pixabayimages;

import com.sofiya.pixabayimages.ApiModel.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApi {
    @GET("?key=29684140-e4adb65c322ca3a0177df9da6")
    Call<Response> getImages(@Query("page") int page,
                             @Query("per_page") int per_page,
                             @Query("order") String order);
}
