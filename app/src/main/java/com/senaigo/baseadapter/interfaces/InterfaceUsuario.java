package com.senaigo.baseadapter.interfaces;

import com.senaigo.baseadapter.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface InterfaceUsuario {

    @GET("users")
    Call<List<Users>> get();

    @GET("users/{id}")
    Call<Users> getPorId(@Path("id") Integer id);

    @POST("users")
    Call<Users> post(@Body Users users);

    @PUT("users/{id}")
    Call<Users> put(@Path("id") Integer id, @Body Users users);

    @DELETE("/users/{id}")
    Call<Void> delete(@Path("id") Integer id);

}
