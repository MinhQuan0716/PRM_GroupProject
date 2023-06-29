package com.example.prm392_project_2.dtos;

import java.util.ArrayList;
import java.util.List;

public class Product extends BaseEntity {
    private String productName = "";
    private int price;
    private String description = "";
    private String imgPath = null;
    private List<OrderDetail> orderDetailsOrdered = new ArrayList<>();

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<OrderDetail> getOrderDetailsOrdered() {
        return orderDetailsOrdered;
    }

    public void setOrderDetailsOrdered(List<OrderDetail> orderDetailsOrdered) {
        this.orderDetailsOrdered = orderDetailsOrdered;
    }
}