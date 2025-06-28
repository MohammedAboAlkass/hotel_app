
package com.example.finalproject_ucas.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
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
import com.example.finalproject_ucas.model.RoomDetailsAdmin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class RoomDetailsAdapter extends RecyclerView.Adapter<RoomDetailsAdapter.MyHolder> {

    Context context;
    ArrayList<RoomDetailsAdmin> roomList;

    public RoomDetailsAdapter(Context context, ArrayList<RoomDetailsAdmin> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.room_details_item, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        int pos = position ;

        RoomDetailsAdmin roomDetailsAdmin = roomList.get(pos);

        loadImage(holder.img, roomDetailsAdmin.getImageUri());
        holder.type.setText(roomDetailsAdmin.getRoomType());
        holder.price.setText(String.valueOf(roomDetailsAdmin.getPricePerNight()));
        if(roomDetailsAdmin.getAvailability() == 1){
            holder.vilable.setText("متاح");
            holder.vilable.setBackgroundColor(Color.GREEN);

        }else{
            holder.vilable.setText("غير متاح ");
            holder.vilable.setBackgroundColor(Color.RED);
        }

        holder.cardView.setOnClickListener(v -> {
            Intent i = new Intent(context, RoomDetails2.class);
            i.putExtra("type", roomDetailsAdmin.getRoomType());
            i.putExtra("price", roomDetailsAdmin.getPricePerNight());
            i.putExtra("desc", roomDetailsAdmin.getDesc());
            i.putExtra("img", roomDetailsAdmin.getImageUri());
            i.putExtra("roomId", roomDetailsAdmin.getRoomId());
            i.putExtra("is_avilable", roomDetailsAdmin.getAvailability());
            Log.d("img", "Desc: " + roomDetailsAdmin.getImageUri());

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
    private void loadImage(ImageView imageView, String imagePath) {
        try {
            if (imagePath.startsWith("/")) {
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

    static class MyHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView type, price ,vilable;
        CardView cardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
            type = itemView.findViewById(R.id.typeID);
            price = itemView.findViewById(R.id.priceID);
            vilable = itemView.findViewById(R.id.vilableID);
            cardView = itemView.findViewById(R.id.cardID);

        }
    }
}
