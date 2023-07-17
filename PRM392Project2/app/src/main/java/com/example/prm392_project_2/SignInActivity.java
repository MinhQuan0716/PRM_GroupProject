package com.example.prm392_project_2;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.prm392_project_2.databinding.ActivitySignInBinding;
import com.example.prm392_project_2.dtos.Account;
import com.example.prm392_project_2.utilities.Constants;
import com.example.prm392_project_2.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private static final String REQUIRE = "Required";

    ArrayList<Account>  listAccount;
    AccountService accountService;

    private ActivitySignInBinding binding;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //call service
        accountService= UnitOfWork.getAccountService();

        preferenceManager = new PreferenceManager(getApplicationContext());

        binding = ActivitySignInBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setListeners();
    }


    private void setListeners() {
        binding.btnLogin.setOnClickListener(view -> {
            signIn();
            signInF();
        });
        binding.tvNoAccount.setOnClickListener(view -> {
            signUpForm();
        });
    }

    private void signUpForm() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean checkInput() {
        // Username
        if (binding.etUsername.getText().toString().trim().isEmpty()) {
            showToast("Invalid Username");
            return false;
        }
        // Password
        if (binding.etPassword.getText().toString().trim().isEmpty()) {
            showToast("Invalid Password");
            return false;
        }
        // VaLid
        return true;
    }
    private void signIn() {
        if(checkInput()==false){
            return;
        }
        Call<Account> call=accountService.login(binding.etUsername.getText().toString(),binding.etPassword.getText().toString());
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(!response.isSuccessful()){
                    return;
                } else{
                       Account loginAccount =response.body();
                       if(loginAccount!=null){
                           // Convert the loginAccount object to a string using Gson
                           Gson gson = new Gson();
                           String loginAccountJson = gson.toJson(loginAccount);

// Save the loginAccount string to SharedPreferences
                           SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                           SharedPreferences.Editor editor = preferences.edit();
                           editor.putString("loginAccount", loginAccountJson);
                           editor.apply();
                           Intent intent=new Intent(SignInActivity.this,MainActivity.class);
                           startActivity(intent);
                       }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                String errorMessage = "Load fail: " + t.getMessage();
                Toast.makeText(SignInActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                Log.e("API Error", errorMessage);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void signInF() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, binding.etUsername.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.etPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));
                    }
                });
    }
}