package com.example.prm392_project_2;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String REQUIRE = "Required";
    EditText etUsername,etPassword;
    TextView tvNoAcount;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        // Reference from Layout

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvNoAcount = findViewById(R.id.tvNoAccount);
        btnSignIn = findViewById(R.id.btnLogin);

        // Register event
        tvNoAcount.setOnClickListener(this);
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
        Intent intent = new Intent(this, SIgnUpActivity.class);
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

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}