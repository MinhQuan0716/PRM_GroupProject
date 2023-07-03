package com.example.prm392_project_2.Services;

import com.example.prm392_project_2.dtos.OrderDetail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderDetailService {
     String PATH = "OrderDetail";

    @GET(PATH)
    Call<OrderDetail[]> getAllOrderDetails();
    @GET(PATH +"/{id}")
    Call<OrderDetail> getAllOrderDetails(@Path("id") Object id);
    @POST(PATH)
    Call<OrderDetail> createOrderDetail(@Body OrderDetail newOrderDetail);
    @PUT(PATH +"/{id}")
    Call<OrderDetail> updateOrderDetail(@Path("id") Object id, @Body OrderDetail orderDetail);
    @DELETE(PATH +"/{id}")
    Call<OrderDetail> removeOrderDetail(@Path("id") Object id);
}
