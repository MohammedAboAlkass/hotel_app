
package com.example.finalproject_ucas.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_ucas.DBHelper;
import com.example.finalproject_ucas.R;
import com.example.finalproject_ucas.model.RoomDetailsAdmin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AdminRoomAdapter extends RecyclerView.Adapter<AdminRoomAdapter.MyHolder> {

    Context c;
    ArrayList<RoomDetailsAdmin> roomDetailsAdminList;
    private DBHelper db;

    public AdminRoomAdapter(Context c, ArrayList<RoomDetailsAdmin> roomDetailsAdminList, DBHelper db) {
        this.c = c;
        this.roomDetailsAdminList = roomDetailsAdminList;
        this.db = db;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.room_details_item_admin, null);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        RoomDetailsAdmin roomDetailsAdmin = roomDetailsAdminList.get(position);

        // تحميل الصورة
        loadImage(holder.img, roomDetailsAdmin.getImageUri());

        // البيانات النصية
        holder.type.setText("TYPE: " + roomDetailsAdmin.getRoomType());
        holder.price.setText("PRICE: " + roomDetailsAdmin.getPricePerNight());
        holder.roomNumber.setText("ROOM NUM: " + roomDetailsAdmin.getRoomNumber());

        // حذف
        holder.delete.setOnClickListener(v -> {
            new AlertDialog.Builder(c)
                    .setTitle("Delete Room")
                    .setMessage("Are you sure you want to delete this room?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Log.d("id number", String.valueOf(roomDetailsAdmin.getRoomId()));

                        db.deleteRoom(roomDetailsAdmin.getRoomId());
                        roomDetailsAdminList.clear();
                        roomDetailsAdminList.addAll(db.getAllRooms());
                        notifyDataSetChanged();

                        Toast.makeText(c, "Room deleted.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
        holder.edit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setTitle("Edit Room Details");

            View v_item = LayoutInflater.from(c).inflate(R.layout.add_room, null);
            builder.setView(v_item);

            TextView t_price = v_item.findViewById(R.id.editTextText);
            TextView t_type = v_item.findViewById(R.id.editTextText2);
            TextView t_desc = v_item.findViewById(R.id.editTextText3);
            TextView t_roomNumber = v_item.findViewById(R.id.roomNumberID);

            // تحميل البيانات الحالية
            t_price.setText(String.valueOf(roomDetailsAdmin.getPricePerNight()));
            t_type.setText(roomDetailsAdmin.getRoomType());
            t_desc.setText(roomDetailsAdmin.getDesc());
            t_roomNumber.setText(roomDetailsAdmin.getRoomNumber());

            builder.setPositiveButton("Save", (dialog, which) -> {
                // التحقق من الإدخال
                double price = Double.parseDouble(t_price.getText().toString());
                String type = t_type.getText().toString();
                String desc = t_desc.getText().toString();
                String roomNumber = t_roomNumber.getText().toString();

                // تعديل البيانات
                roomDetailsAdmin.setPricePerNight(price);
                roomDetailsAdmin.setRoomType(type);
                roomDetailsAdmin.setDesc(desc);
                roomDetailsAdmin.setRoomNumber(roomNumber);

                // تعديل في قاعدة البيانات
                db.updateRoom(roomDetailsAdmin);

                // تحديث الواجهة
                roomDetailsAdminList.clear();
                roomDetailsAdminList.addAll(db.getAllRooms());
                notifyDataSetChanged();

                Toast.makeText(c, "Room updated.", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

    }

    @Override
    public int getItemCount() {
        return roomDetailsAdminList.size();
    }

    // تحميل الصورة بطريقة مرنة
    private void loadImage(ImageView imageView, String imagePath) {
        try {
            if (imagePath.startsWith("/")) {
                // من ملفات الجهاز
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
                // من URI
                imageView.setImageURI(Uri.parse(imagePath));
            }
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(R.drawable.apple);
        }
    }

    // ViewHolder
    class MyHolder extends RecyclerView.ViewHolder {
        TextView type, desc, roomNumber;
        TextView price;
        ImageView img, delete, edit;
        CardView cardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.typeID);
            price = itemView.findViewById(R.id.priceID);
            img = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardID);
            delete = itemView.findViewById(R.id.delete_ID);
            edit = itemView.findViewById(R.id.edit_ID);
            desc = itemView.findViewById(R.id.descID);
            roomNumber = itemView.findViewById(R.id.roomID);
        }
    }

}
