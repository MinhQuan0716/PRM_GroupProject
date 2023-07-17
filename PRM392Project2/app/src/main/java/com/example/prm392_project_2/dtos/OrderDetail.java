package com.example.prm392_project_2.dtos;

import java.io.Serializable;

public class OrderDetail extends BaseEntity implements Serializable {
    private int orderId;
    private int productId;
    private int Amount;
    private Account account;
    private Product product;

    public OrderDetail(int orderId, int productId, int amount) {
        this.orderId = orderId;
        this.productId = productId;
        Amount = amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
