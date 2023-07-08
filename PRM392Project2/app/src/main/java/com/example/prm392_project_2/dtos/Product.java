package com.example.prm392_project_2.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product extends BaseEntity implements Serializable {
    private String productName = "";
    private int price;
    private String description = "";
    private String imgPath = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getPrice() == product.getPrice() && getProductName().equals(product.getProductName()) && getDescription().equals(product.getDescription()) && getImgPath().equals(product.getImgPath()) && getId()==product.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductName(), getPrice(), getDescription(), getImgPath(), getOrderDetailsOrdered());
    }

    private List<OrderDetail> orderDetailsOrdered = new ArrayList<>();

    public Product(int id, String productName, int price, String description, String imgPath) {
        super(id);
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.imgPath = imgPath;
    }

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