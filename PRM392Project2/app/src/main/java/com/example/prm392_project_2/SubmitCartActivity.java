package com.example.prm392_project_2;



import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_project_2.Services.OrderDetailService;
import com.example.prm392_project_2.cartutil.CartDAO;
import com.example.prm392_project_2.cartutil.CartDatabase;
import com.example.prm392_project_2.cartutil.CartItem;
import com.example.prm392_project_2.dtos.DetailsFormat;
import com.example.prm392_project_2.dtos.SubmitCartFormat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubmitCartActivity extends AppCompatActivity  {
    private TextView totalPrice ;
    private  TextView shipFee ;
    private EditText adressInput;
    private CartDatabase database;
    private ArrayList<CartItem> listCart;
    private List<DetailsFormat> _cart;
    private CartDAO cartDAO;
    private Button submitCart;
    OrderDetailService orderDetailService;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_cart);
           Intent intent = getIntent();
       int total =    intent.getIntExtra("TotalPrice",200);
          totalPrice = findViewById(R.id.totalPriceId);
          totalPrice.setText("Total Price"+ String.valueOf(total));
          shipFee = findViewById(R.id.totalPriceId);
          shipFee.setText("Ship Fee" + String.valueOf(500));
          adressInput = (EditText) findViewById(R.id.adressInput);
          submitCart = (Button) findViewById(R.id.btnPayment);



          submitCart.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  int customerId = 1;
                  int shipFee  = 200;
                  int totalPrice = 200;
                  String address = "Hoc Mon";
                  boolean isPayed = true;

                  SubmitCartFormat _new = new SubmitCartFormat(customerId,shipFee,totalPrice,address,isPayed,_cart);
                  try {
                      Call<SubmitCartFormat> call = orderDetailService.createOrderDetail(_new);
                      call.enqueue(new Callback<SubmitCartFormat>() {
                          @Override
                          public void onResponse(Call<SubmitCartFormat> call, Response<SubmitCartFormat> response) {
                              if(response.body()!= null){
                                  Intent intent = new Intent(SubmitCartActivity.this, MainActivity.class);
                                  startActivity(intent);
                                  finish();
                              }
                          }

                          @Override
                          public void onFailure(Call<SubmitCartFormat> call, Throwable t) {
                              Toast.makeText(SubmitCartActivity.this,"Check you account again",Toast.LENGTH_LONG).show();
                          }
                      });

                  }catch (Exception e){
                      Log.d("Error",e.getMessage());
                  }
              }
          });
           database = Room.databaseBuilder(getApplicationContext(),
                   CartDatabase.class, "cart-db").build();
           cartDAO = database.cartDao();


           // Retrieve cart items asynchronously
           new Thread(() -> {
               listCart = new ArrayList<>(cartDAO.getAll());
               // Update UI on the main thread
               List<DetailsFormat> list = new ArrayList<>();
                   for (CartItem item : listCart) {
                       DetailsFormat format = new DetailsFormat(item.getProduct().getId(),item.getQuantity());
                       list.add(format);
                   }
                   _cart = list;
           }).start();
    }



}