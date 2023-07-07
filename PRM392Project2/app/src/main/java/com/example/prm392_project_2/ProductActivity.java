package com.example.prm392_project_2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project_2.Repositories.UnitOfWork;
import com.example.prm392_project_2.Services.ProductService;
import com.example.prm392_project_2.dtos.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
ProductService productService;
 ArrayList<Product> listProduct;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_product);
        RecyclerView productView =(RecyclerView) findViewById(R.id.productView);
        productService= UnitOfWork.getProductService();
        listProduct=new ArrayList<>();
        Call<Product[]> calls=productService.getAllProducts();
        calls.enqueue(new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                Product[] produtcs=response.body();
                if(produtcs==null){
                    return;
                }
                for(Product product:produtcs){
                    listProduct.add(product);
                }
                ProductAdapter adapter=new ProductAdapter(listProduct,ProductActivity.this);
                productView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Product[]> call, Throwable t) {
                String errorMessage = "Load fail: " + t.getMessage();
                Toast.makeText(ProductActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                Log.e("API Error", errorMessage);
            }
        });
        productView.setLayoutManager(new GridLayoutManager(this,2));

    }
}
