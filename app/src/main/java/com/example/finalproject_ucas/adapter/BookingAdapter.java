package com.example.finalproject_ucas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_ucas.DBHelper;
import com.example.finalproject_ucas.R;
import com.example.finalproject_ucas.model.Booking;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context context;
    private ArrayList<Booking> bookingList;
    private DBHelper db;
    private String username;

    public BookingAdapter(Context context, ArrayList<Booking> bookingList, DBHelper db, String username) {
        this.context = context;
        this.bookingList = bookingList;
        this.db = db;
        this.username = username;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvRoomType.setText(booking.getRoomType());
        holder.tvDates.setText("From " + booking.getCheckIn() + " to " + booking.getCheckOut());
        holder.tvGuests.setText("Guests: " + booking.getGuests());
        holder.tvStatus.setText("Status: " + booking.getStatus());

        holder.btnCancel.setOnClickListener(v -> {
            db.cancelBooking(booking.getBookingId());
            bookingList.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Booking cancelled!", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomType, tvDates, tvGuests, tvStatus;
        Button btnCancel;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomType = itemView.findViewById(R.id.tv_room_type);
            tvDates = itemView.findViewById(R.id.tv_dates);
            tvGuests = itemView.findViewById(R.id.tv_guests);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
        }
    }
}
