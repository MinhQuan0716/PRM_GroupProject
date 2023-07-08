package com.example.prm392_project_2.cartutil;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.prm392_project_2.dtos.Product;

import java.util.ArrayList;
import java.util.UUID;

@Entity
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private int cartId;
    @ColumnInfo(name = "product")
    @TypeConverters(Converters.class)
    private Product product;

    @ColumnInfo(name = "quantity")
    private int quantity;


    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}
