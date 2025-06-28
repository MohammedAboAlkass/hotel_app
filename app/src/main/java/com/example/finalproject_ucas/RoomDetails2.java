package com.example.finalproject_ucas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject_ucas.databinding.ActivityRoomDetails2Binding;
import com.example.finalproject_ucas.databinding.ActivityUserHomeBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

public class RoomDetails2 extends AppCompatActivity {

    ActivityRoomDetails2Binding binding;

    String checkInDate = "", checkOutDate = "";
    int guests = 1;
    DBHelper db;
    int roomId;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomDetails2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        db = new DBHelper(RoomDetails2.this);


        Intent i = getIntent();
        loadImage(binding.imageView2, i.getStringExtra("img"));

        double price = getIntent().getDoubleExtra("price", 0.0);
        binding.typeTD2.setText(i.getStringExtra("type"));
        binding.priceID2.setText(String.valueOf(price));
        binding.descID2.setText(i.getStringExtra("desc"));
        roomId = getIntent().getIntExtra("roomId", -1);
        int is_vilabe = getIntent().getIntExtra("is_avilable", 0);
        Log.d("is vilable ::::: ", String.valueOf(is_vilabe));

        if (is_vilabe == 1) {
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCheckInDatePicker();
                }
            });
        } else {
            binding.button.setBackgroundColor(Color.GRAY);
        }


    }

    private void showCheckInDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    checkInDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    showCheckOutDatePicker();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showCheckOutDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    checkOutDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    showGuestsPicker();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showGuestsPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select number of guests");

        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setValue(1);

        builder.setView(numberPicker);
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            guests = numberPicker.getValue();
            confirmBooking();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void confirmBooking() {
        boolean result = db.addBooking(2, roomId, checkInDate, checkOutDate, guests, "Confirmed");
        if (result) {
            Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to book room.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImage(ImageView imageView, String imagePath) {
        try {
            if (imagePath.startsWith("/")) {
                // صورة محفوظة كـ File في النظام
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    InputStream inputStream = new FileInputStream(imageFile);
                    Drawable drawable = Drawable.createFromStream(inputStream, null);
                    imageView.setImageDrawable(drawable);
                    inputStream.close();
                } else {
                    imageView.setImageResource(R.drawable.apple);
                }
            } else {
                // صورة محفوظة كـ URI
                imageView.setImageURI(Uri.parse(imagePath));
            }
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(R.drawable.apple);
        }
    }
}