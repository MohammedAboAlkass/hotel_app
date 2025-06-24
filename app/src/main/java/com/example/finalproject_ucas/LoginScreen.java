package com.example.finalproject_ucas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.finalproject_ucas.databinding.ActivityLoginScreenBinding;

public class LoginScreen extends AppCompatActivity {

    ActivityLoginScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        binding.signUpId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginScreen.this,RegisterScreen.class);
                        startActivity(i);

            }
        });

      binding.btnLoginId.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              DBHelper dbHelper = new DBHelper(LoginScreen.this);
              String email =   binding.etEmailId.getText().toString();
              String pass =   binding.etPassId.getText().toString();
              if(!email.isEmpty() && !pass.isEmpty()){
                  if (dbHelper.loginUser(email, pass)) {
                      int userType = dbHelper.getUserType(email, pass);

                      if (userType == 1) {
                          // Admin
                          Intent intent = new Intent(LoginScreen.this, AdminHomeActivity.class);
                          startActivity(intent);
                          finish();
                      } else if (userType == 0) {
                          // User
                          Intent intent = new Intent(LoginScreen.this, UserHomeActivity.class);
                          startActivity(intent);
                          finish();
                      } else {
                          Toast.makeText(LoginScreen.this, "Unknown user type", Toast.LENGTH_SHORT).show();
                      }
                  } else {
                      Toast.makeText(LoginScreen.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                  }
              }else {
                  Toast.makeText(LoginScreen.this, "enter email and password", Toast.LENGTH_SHORT).show();
              }

          }
      });












    }
}