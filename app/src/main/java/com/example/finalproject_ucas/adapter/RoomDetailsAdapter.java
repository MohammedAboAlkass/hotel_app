package com.example.finalproject_ucas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_ucas.R;
import com.example.finalproject_ucas.RoomDetails2;
import com.example.finalproject_ucas.model.RoomDetails;

import java.util.ArrayList;

public class RoomDetailsAdapter extends RecyclerView.Adapter<RoomDetailsAdapter.MyHolder> {


    Context context ;
    ArrayList<RoomDetails> arrayList ;

    public RoomDetailsAdapter(Context context, ArrayList<RoomDetails> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.room_details_item,null);
        MyHolder mh = new MyHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        int pos = position ;
        RoomDetails roomDetails = arrayList.get(position);
        holder.img.setImageURI(roomDetails.getImg());
        holder.type.setText(roomDetails.getType());
        holder.price.setText(String.valueOf(roomDetails.getPrice()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , RoomDetails2.class);
                i.putExtra("type",roomDetails.getType());
                i.putExtra("price",roomDetails.getPrice());
                i.putExtra("desc",roomDetails.getDesc());
                i.putExtra("img",roomDetails.getImg());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView img ;
        TextView type , price;
        CardView cardView ;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
            type = itemView.findViewById(R.id.typeID);
            price = itemView.findViewById(R.id.priceID);
            cardView = itemView.findViewById(R.id.cardID);

        }
    }
}
