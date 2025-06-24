package com.example.finalproject_ucas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.Objects;

public class RoomDetails2 extends AppCompatActivity {

    ActivityRoomDetails2Binding binding ;

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
        binding.imageView2.setImageResource(i.getIntExtra("img", 0));
        binding.typeTD2.setText(i.getStringExtra("type"));
        binding.priceID2.setText(String.valueOf(i.getStringExtra("price")));
        binding.descID2.setText(i.getStringExtra("desc"));

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckInDatePicker();
            }
        });




    }
    private void showCheckInDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    checkInDate = year + "-" + (month+1) + "-" + dayOfMonth;
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
                    checkOutDate = year + "-" + (month+1) + "-" + dayOfMonth;
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
        boolean result = db.addBooking(2, 1, checkInDate, checkOutDate, guests, "Confirmed");
        if (result) {
            Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to book room.", Toast.LENGTH_SHORT).show();
        }
    }
}