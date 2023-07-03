package com.example.prm392_project_2.Repositories;


import com.example.prm392_project_2.Services.AccountService;
import com.example.prm392_project_2.Services.OrderDetailService;
import com.example.prm392_project_2.Services.ProductService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit;
    private static String baseUrl = "https://localhost:5001/api/v1";
    private static String deployUrl = "404";

    public  static Retrofit getClient(){
        if(retrofit== null) {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }


}
