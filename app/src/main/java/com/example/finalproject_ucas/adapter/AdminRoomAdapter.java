package com.example.finalproject_ucas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_ucas.R;
import com.example.finalproject_ucas.model.RoomDetails;

import java.util.ArrayList;

public class AdminRoomAdapter extends RecyclerView.Adapter<AdminRoomAdapter.MyHolder> {

    Context c ;
    ArrayList<RoomDetails> roomDetailsList ;

    public AdminRoomAdapter(Context c, ArrayList<RoomDetails> roomDetailsList) {
        this.c = c;
        this.roomDetailsList = roomDetailsList;
    }

    @NonNull
    @Override
    public AdminRoomAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.room_details_item,null);
        MyHolder myHolder = new MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRoomAdapter.MyHolder holder, int position) {

        RoomDetails roomDetails = roomDetailsList.get(position);
        holder.img.setImageURI(roomDetails.getImg());
        holder.type.setText(roomDetails.getType());
        holder.price.setText(String.valueOf(roomDetails.getPrice()));


    }

    @Override
    public int getItemCount() {
        return roomDetailsList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView type ;
        TextView price ;
        ImageView img ;
        CardView cardView ;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.typeID);
            price = itemView.findViewById(R.id.priceID);
            img = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardID);
        }
    }
}
