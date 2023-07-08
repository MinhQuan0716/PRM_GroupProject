package com.example.prm392_project_2.cartutil;

import androidx.room.TypeConverter;

import com.example.prm392_project_2.dtos.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Converters {
    @TypeConverter
    public static Product fromJson(String value) {
        Gson gson = new Gson();
        Type productType = new TypeToken<Product>() {}.getType();
        return gson.fromJson(value, productType);
    }

    @TypeConverter
    public static String toJson(Product product) {
        Gson gson = new Gson();
        return gson.toJson(product);
    }
}
