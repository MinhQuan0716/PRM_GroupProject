package com.example.prm392_project_2.Repositories;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit;
    private static String baseUrl = "https://localhost:5001/api/v1";

    public  static Retrofit getClient(){
        if(retrofit== null) {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    // Repositories
    public  static AccountRepository getAccountRepository(){
        return APIClient.getClient().create(AccountRepository.class);
    }
    public  static OrderDetailRepository getOrderDetailRepository(){
        return APIClient.getClient().create(OrderDetailRepository.class);
    }
    public  static ProductRepository getProductRepository(){
        return APIClient.getClient().create(ProductRepository.class);
    }
}
