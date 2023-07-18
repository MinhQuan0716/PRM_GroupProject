package com.example.prm392_project_2.dtos;

public class DetailsFormat {
    public  int productId ;
    public  int amount = 200;

    public DetailsFormat(int productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
