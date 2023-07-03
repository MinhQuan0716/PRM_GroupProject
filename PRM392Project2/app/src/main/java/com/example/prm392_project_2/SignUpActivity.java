package com.example.prm392_project_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_project_2.Repositories.UnitOfWork;
import com.example.prm392_project_2.Services.AccountService;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String REQUIRE = "Required";
    EditText etUsername, etPassword,etConfirmPassword;
    TextView tvHaveAccount;
    Button btnSignUp;
    private AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Services
        accountService = UnitOfWork.getAccountService();
        // Reference from Layout

        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvHaveAccount = findViewById(R.id.tvHaveAccount);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Register event
        tvHaveAccount.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if(vId == R.id.btnSignUp){
            signUp();
        }
        if(vId == R.id.tvHaveAccount){
            signInForm();
        }
    }

    private void signInForm() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean checkInput() {
        String username=  etUsername.getText().toString();
        String password= etPassword.getText().toString();
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
        //Confirm Password
        if (TextUtils.isEmpty(etConfirmPassword.getText().toString())) {
            etPassword.setError(REQUIRE);
            return false;
        }
        // Check Equal
        if(!TextUtils.equals(password,etConfirmPassword.getText().toString())){
            Toast.makeText(this, "Password are not match", Toast.LENGTH_SHORT).show();
            return false;
        }



        // VaLid
        return true;
    }
    private void signUp() {
        if(checkInput() == false){
            return;
        }

        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
        finish();
    }
}