package com.example.prm392_project_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_project_2.Repositories.UnitOfWork;
import com.example.prm392_project_2.Services.ProductService;
import com.example.prm392_project_2.dtos.Product;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    TextView txtProductName;
    TextView txtProductPrice;
    TextView txtProductDescripton;
    ImageView productImageView;
    ProductService productService;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_productdetail);
        txtProductName=(TextView) findViewById(R.id.productName);
        txtProductDescripton=(TextView) findViewById(R.id.productDescription);
        txtProductPrice=(TextView) findViewById(R.id.productPrice);
        productImageView=(ImageView) findViewById(R.id.prouctimageView);
        Button btnAddToCart =(Button) findViewById(R.id.btnAddToCart);
        productService= UnitOfWork.getProductService();
        Intent intent=getIntent();
        int productId= intent.getIntExtra("productId",0);
        Call<Product> call =productService.getProductById(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product =response.body();
                if(product==null){
                    return;
                }
                txtProductDescripton.setText(product.getDescription());
                txtProductName.setText(product.getProductName());
                txtProductPrice.setText(String.valueOf(product.getPrice()));
                Picasso.get().load(product.getImgPath()).into(productImageView);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                String errorMessage = "Load fail: " + t.getMessage();
                Toast.makeText(ProductDetailActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                Log.e("API Error", errorMessage);
            }
        });
    }
}
