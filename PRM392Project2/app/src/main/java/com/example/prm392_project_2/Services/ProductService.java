package com.example.prm392_project_2.Services;

import com.example.prm392_project_2.dtos.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {
     String PATH = "Products";

    @GET(PATH)
    Call<Product[]> getAllProducts();
    @GET(PATH +"/{id}")
    Call<Product> getProductById(@Path("id") Object id);
    @POST(PATH)
    Call<Product> createProduct(@Body Product newProduct);
    @PUT(PATH +"/{id}")
    Call<Product> updateProduct(@Path("id") Object id, @Body Product product);
    @DELETE(PATH +"/{id}")
    Call<Product> removeProduct(@Path("id") Object id);
}
