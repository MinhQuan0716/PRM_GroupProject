package com.example.prm392_project_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.prm392_project_2.cartutil.CartAdapter;
import com.example.prm392_project_2.cartutil.CartDAO;
import com.example.prm392_project_2.cartutil.CartDatabase;
import com.example.prm392_project_2.cartutil.CartItem;
import com.example.prm392_project_2.dtos.Product;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ArrayList<CartItem> listCart;
    private SharedPreferences sharedPreferences;
    private CartDatabase cartDatabase;
    private CartDAO cartItemDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView cartView = findViewById(R.id.cartList);
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("productData");

        // Initialize Room Database
        cartDatabase = Room.databaseBuilder(getApplicationContext(),
                CartDatabase.class, "cart-db").build();
        cartItemDao = cartDatabase.cartDao();

        // Retrieve cart items asynchronously
        new Thread(() -> {
            listCart = new ArrayList<>(cartItemDao.getAll());
            if(listCart==null) {listCart=new ArrayList<>();}
            boolean productExists = false;
            int existingProductIndex = -1;
            for (int i = 0; i < listCart.size(); i++) {
                if (listCart.get(i).getProduct().equals(product)) {
                    listCart.get(i).setQuantity(listCart.get(i).getQuantity() + 1);
                    productExists = true;
                    existingProductIndex = i;
                    break;
                }
            }

            if (!productExists) {

                listCart.add(new CartItem(product, 1));
                cartDatabase.cartDao().insert(new CartItem(product,1));
            } else {
                // Update the existing cart item in the database
                cartDatabase.cartDao().update(listCart.get(existingProductIndex));
            }
            // Update UI on the main thread

            runOnUiThread(() -> {
              CartAdapter  adapter = new CartAdapter(listCart);
                cartView.setAdapter(adapter);
                cartView.setLayoutManager(new LinearLayoutManager(this));
                adapter.notifyDataSetChanged();
            });
        }).start();

    }

}
