package com.example.prm392_project_2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prm392_project_2.Services.OrderDetailService;
import com.example.prm392_project_2.cartutil.CartDAO;
import com.example.prm392_project_2.cartutil.CartDatabase;
import com.example.prm392_project_2.cartutil.CartItem;
import com.example.prm392_project_2.dtos.Account;
import com.example.prm392_project_2.dtos.DetailsFormat;
import com.example.prm392_project_2.dtos.SubmitCartFormat;
import com.google.gson.Gson;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class SubmitCartActivity extends AppCompatActivity {
    private TextView tvTotalPrice, tvShipFee, tvAddress;
    private CartDatabase database;
    private ArrayList<CartItem> listCart;
    private List<DetailsFormat> _cart;
    private CartDAO cartDAO;
    private Button submitCart;
    OrderDetailService orderDetailService;

    private String address;
    private int ship, total;
    String SECRET_KEY ="sk_test_51NRd8wA9veHO6La7Rhu0GAWvzlmS23YsXgXvWSAOKIaRty3RjFKFdVKflNhmnx6SuJcwJDnuWp1DxyLfjOL8O9tN00vQ3Y6zKf";
    String PUBLISH_KEY="pk_test_51NRd8wA9veHO6La7RM2p9iQQrfIGxCxPRiXPVEllvov9Hon3Kbxe8RLYaVd2eWklFdSh0MI9f4YPVaMlJwWvmovt00J3ep6fgm";

    private PaymentSheet paymentSheet;

    public String CustomerID;
    public String EphericalKey;
    public String ClientSercet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_cart);
        Intent intent = getIntent();

        //lay total
        total = intent.getIntExtra("TotalPrice", 200);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvTotalPrice.setText("Total Price: $" + String.valueOf(total));

        //lay ship
        ship = 5;
        tvShipFee = findViewById(R.id.tvShipFee);
        tvShipFee.setText("Ship fee: $" + String.valueOf(ship));

        //lay dia chi
        tvAddress = findViewById(R.id.tvAddress);
        String address = intent.getStringExtra("Address");
        tvAddress.setText("Address: " + address);

        submitCart = (Button) findViewById(R.id.btnPayment);

        database = Room.databaseBuilder(getApplicationContext(),
                CartDatabase.class, "cart-db").build();
        cartDAO = database.cartDao();


        PaymentConfiguration.init(this, PUBLISH_KEY);
        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        submitCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCustomerID();
            }
        });
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String loginAccountJson = pref.getString("loginAccount", null);
            Gson gson = new Gson();
            Account loginAccount = gson.fromJson(loginAccountJson, Account.class);
            int customerId = loginAccount.getId();
            boolean isPayed = true;

            SubmitCartFormat _new = new SubmitCartFormat(customerId, ship, total, address, isPayed, _cart);

            listCart = new ArrayList<>(cartDAO.getAll());
            // Update UI on the main thread
            List<DetailsFormat> list = new ArrayList<>();
            for (CartItem item : listCart) {
                cartDAO.delete(item);
            }
            Toast.makeText(SubmitCartActivity.this, "Payment successfully", Toast.LENGTH_LONG).show();

            Intent intent1 = new Intent(SubmitCartActivity.this, MainActivity.class);
            startActivity(intent1);
            finish();
        }
    }

    private void getCustomerID() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            CustomerID = object.getString("id");
//                            Toast.makeText(MainActivity.this, customerID, Toast.LENGTH_SHORT).show();
                            getEphericalKey(object.getString("id"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SECRET_KEY);
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SubmitCartActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getEphericalKey(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            EphericalKey = object.getString("id");
//                            Toast.makeText(MainActivity.this, EphericalKey, Toast.LENGTH_SHORT).show();
                            getClientSecret(customerID);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SECRET_KEY);
                header.put("Stripe-Version", "2022-11-15");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SubmitCartActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSercet = object.getString("client_secret");
//                            Toast.makeText(MainActivity.this, ClientSercet, Toast.LENGTH_SHORT).show();
                            paymentFlow();



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SECRET_KEY);
                header.put("Stripe-Version", "2022-11-15");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                params.put("amount", String.valueOf(total + ship) + "00");
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SubmitCartActivity.this);
        requestQueue.add(stringRequest);
    }

    private void paymentFlow() {
        paymentSheet.presentWithPaymentIntent(
                ClientSercet, new PaymentSheet.Configuration("Shopify",
                        new PaymentSheet.CustomerConfiguration(
                                CustomerID,
                                EphericalKey
                        ))
        );
    }


}