package com.example.finalproject_ucas;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finalproject_ucas.adapter.BookingAdapter;
import com.example.finalproject_ucas.databinding.ActivityMyBookingBinding;
import com.example.finalproject_ucas.databinding.ActivityRoomDetails2Binding;
import com.example.finalproject_ucas.model.Booking;

import java.util.ArrayList;

public class MyBooking extends AppCompatActivity {

    ActivityMyBookingBinding binding;
    String username;
    DBHelper db;
    ArrayList<Booking> bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        username = getIntent().getStringExtra("id");
        db = new DBHelper(this);

        bookingList = db.getBookingsByUser(2);

        BookingAdapter adapter = new BookingAdapter(this, bookingList, db, username);
        binding.rvBookingID.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBookingID.setAdapter(adapter);



    }
}