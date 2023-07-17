package com.example.prm392_project_2.Services;

import com.example.prm392_project_2.dtos.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountService {
    String PATH = "Account";
    @GET(PATH+"/login")
    Call<Account> login(@Query("username") String username, @Query("password") String password);
    @GET(PATH)
    Call<Account[]> getAllAccounts();
    @GET(PATH +"/{id}")
    Call<Account> getAccountByID(@Path("id") Object id);
    @POST(PATH)
    Call<Account> createAccount(@Body Account newAccount);
    @PUT(PATH +"/{id}")
    Call<Account> updateAccount(@Path("id") Object id, @Body Account account);
    @DELETE(PATH +"/{id}")
    Call<Account> removeAccount(@Path("id") Object id);
}
