package com.example.prm392_project_2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import androidx.room.Room;


import com.example.prm392_project_2.Repositories.NotificationChannel;
import com.example.prm392_project_2.Repositories.UnitOfWork;
import com.example.prm392_project_2.Services.ProductService;
import com.example.prm392_project_2.cartutil.CartDAO;
import com.example.prm392_project_2.cartutil.CartDatabase;
import com.example.prm392_project_2.cartutil.CartItem;
import com.example.prm392_project_2.dtos.Account;
import com.example.prm392_project_2.dtos.Product;
import com.squareup.picasso.Picasso;


import java.util.Date;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    TextView txtProductName;
    TextView txtProductPrice;
    TextView txtProductDescription;
    ImageView productImageView;
    ProductService productService;
    Button btnAddToCart;

    private CartDatabase cartDatabase;
    private CartDAO cartItemDao;
    private ArrayList<CartItem> listCart;


    private int REQUEST_CART = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_productdetail);
        txtProductName = (TextView) findViewById(R.id.productName);
        txtProductDescription = (TextView) findViewById(R.id.productDescription);
        txtProductPrice = (TextView) findViewById(R.id.productPrice);
        productImageView = (ImageView) findViewById(R.id.prouctimageView);
        Button btnAddToCart = (Button) findViewById(R.id.btnAddToCart);
        productService = UnitOfWork.getProductService();
        Intent intent = getIntent();
        Account account =(Account) intent.getSerializableExtra("Account");
        int productId = intent.getIntExtra("productId", 0);
        Call<Product> call = productService.getProductById(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                if (product == null) {
                    return;
                }
                txtProductDescription.setText(product.getDescription());
                txtProductName.setText(product.getProductName());
                txtProductPrice.setText(String.valueOf(product.getPrice()));
                Picasso.get().load(product.getImgPath()).into(productImageView);
                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =new Intent(ProductDetailActivity.this,MainActivity.class);
                        sendNotification();
                        intent.putExtra("productData",product);
                        startActivity(intent);

                        submitCart(product);
                        Toast.makeText(ProductDetailActivity.this, "Add to cart", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                String errorMessage = "Load fail: " + t.getMessage();
                Toast.makeText(ProductDetailActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                Log.e("API Error", errorMessage);
            }
        });
    }


    private void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, NotificationChannel.CHANNEL_ID)
                .setContentTitle("Order has been added")
                .setContentText("Your order has been added to your cart")
                .setSmallIcon(R.drawable.baseline_shopping_cart_24)
                .setLargeIcon(bitmap)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(getNotificationId(), notification);
        }
    }
    private int getNotificationId(){
        return (int) new Date().getTime();
    }

    private void submitCart(Product product) {
        cartDatabase = Room.databaseBuilder(ProductDetailActivity.this,
                CartDatabase.class, "cart-db").build();
        cartItemDao = cartDatabase.cartDao();
        new Thread(() -> {
            List<CartItem> list = cartItemDao.getAll();
            listCart = new ArrayList<>(list);
            if(listCart == null) {
                listCart=new ArrayList<>();
            }
            boolean productExists = false;
            int existingProductIndex = -1;
            if (product != null) {
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
            }
        }).start();
    }
}