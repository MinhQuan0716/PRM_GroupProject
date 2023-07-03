package com.example.prm392_project_2.Repositories;

import com.example.prm392_project_2.Services.AccountService;
import com.example.prm392_project_2.Services.OrderDetailService;
import com.example.prm392_project_2.Services.ProductService;

public class UnitOfWork {
    // Repositories
    public  static AccountService getAccountService(){
        return APIClient.getClient().create(AccountService.class);
    }
    public  static OrderDetailService getOrderDetailService(){
        return APIClient.getClient().create(OrderDetailService.class);
    }
    public  static ProductService getProductService(){
        return APIClient.getClient().create(ProductService.class);
    }
}
