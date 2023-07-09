package com.example.prm392_project_2;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_project_2.Repositories.UnitOfWork;
import com.example.prm392_project_2.Services.AccountService;
import com.example.prm392_project_2.dtos.Account;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String REQUIRE = "Required";
    EditText etUsername,etPassword;
    TextView tvNoAcount;
    Button btnSignIn;
    ArrayList<Account>  listAccount;
    AccountService accountService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        //call service
        accountService= UnitOfWork.getAccountService();
        // Reference from Layout

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvNoAcount = findViewById(R.id.tvNoAccount);
        btnSignIn = findViewById(R.id.btnLogin);

        // Register event
        tvNoAcount.setOnClickListener(this);
        //Login event
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if(vId == R.id.btnLogin){
            signIn();
        }
        if(vId == R.id.tvNoAccount){
            signUpForm();
        }
    }

    private void signUpForm() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean checkInput() {
        // Username
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError(REQUIRE);
            return false;
        }
        // Password
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(REQUIRE);
            return false;
        }
        // VaLid
        return true;
    }
    private void signIn() {
        if(checkInput()==false){
            return;
        }
        Call<Account[]> call =accountService.getAllAccounts();
        call.enqueue(new Callback<Account[]>() {
            @Override
            public void onResponse(Call<Account[]> call, Response<Account[]> response) {
                Account[] accounts = response.body();
                if (accounts == null) {
                    return;
                }
                boolean isExisted=false;
                for (Account account : accounts) {
                    if (etUsername.getText().toString().trim().equals(account.getUsername()) && etPassword.getText().toString().trim().equals(account.getPassword())) {
                        isExisted = true;
                        break;
                    }
                }
                if(isExisted){
                    Intent intent=new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignInActivity.this, "Account not exsited", Toast.LENGTH_LONG).show();
                }


            }




            @Override
            public void onFailure(Call<Account[]> call, Throwable t) {
                String errorMessage = "Load fail: " + t.getMessage();
                Toast.makeText(SignInActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                Log.e("API Error", errorMessage);
            }
        });


    }
}