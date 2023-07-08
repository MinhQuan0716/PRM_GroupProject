package com.example.prm392_project_2.cartutil;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM cartitem")
    List<CartItem> getAll();


    @Insert
    void insert(CartItem cartItem);

    @Update
    void update(CartItem cartItem);

    @Delete
    void delete(CartItem cartItem);
}
