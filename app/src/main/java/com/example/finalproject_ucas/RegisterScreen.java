package com.example.finalproject_ucas;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject_ucas.databinding.ActivityRegisterScreenBinding;
import com.example.finalproject_ucas.model.Users;

public class RegisterScreen extends AppCompatActivity {

    ActivityRegisterScreenBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DBHelper dbHelper = new DBHelper(RegisterScreen.this);

        binding.btnRegisterId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.etUserId.getText().toString();
                String email = binding.etEmailId.getText().toString();
                String password = binding.etPassId.getText().toString();
                Users users = new Users(username,email,password,0);

                if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    boolean inserted = dbHelper.register(users);
                    if (inserted) {
                        Toast.makeText(RegisterScreen.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterScreen.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterScreen.this, "enter all field", Toast.LENGTH_SHORT).show();
                }


            }
        });




        binding.signInId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}