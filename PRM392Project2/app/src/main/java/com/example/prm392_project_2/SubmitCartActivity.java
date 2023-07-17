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

           database = Room.databaseBuilder(getApplicationContext(),
                   CartDatabase.class, "cart-db").build();
           cartDAO = database.cartDao();

          submitCart.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  int customerId = 1;
                  int shipFee  = 200;
                  int totalPrice =  intent.getIntExtra("TotalPrice",200);
                  String address = "Hoc Mon";
                  boolean isPayed = true;

                  SubmitCartFormat _new = new SubmitCartFormat(customerId,shipFee,totalPrice,address,isPayed,_cart);
                  Toast.makeText(SubmitCartActivity.this,"Add order successfully",Toast.LENGTH_LONG).show();
                    new Thread(()->{
                        listCart = new ArrayList<>(cartDAO.getAll());
                        // Update UI on the main thread
                        List<DetailsFormat> list = new ArrayList<>();
                        for (CartItem item : listCart) {
                           cartDAO.delete(item);
                        }
                    }).start();
                    Intent intent1 = new Intent(SubmitCartActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
              }
          });




    }



}