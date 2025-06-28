package com.example.finalproject_ucas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_ucas.adapter.AdminRoomAdapter;
import com.example.finalproject_ucas.databinding.ActivityAdminHomeBinding;
import com.example.finalproject_ucas.model.RoomDetailsAdmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {

    ActivityAdminHomeBinding binding;
    Uri selectedImageUri = null;
    DBHelper db ;
    ArrayList<RoomDetailsAdmin> roomList;
    AdminRoomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        db = new DBHelper(this);

       roomList = db.getAllRooms();
        adapter = new AdminRoomAdapter(AdminHomeActivity.this, roomList, db);


        adapter = new AdminRoomAdapter(AdminHomeActivity.this, roomList,db);
        binding.rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        binding.rv.setLayoutManager(lm);


        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);
                builder.setTitle("add details country");
                View v_item = LayoutInflater.from(AdminHomeActivity.this).inflate(R.layout.add_room, null);
                builder.setView(v_item);
                TextView t_price = v_item.findViewById(R.id.editTextText);
                TextView t_type = v_item.findViewById(R.id.editTextText2);
                TextView t_desc = v_item.findViewById(R.id.editTextText3);
                TextView t_roomNumber = v_item.findViewById(R.id.roomNumberID);
                Button btn = v_item.findViewById(R.id.button);

                btn.setOnClickListener(vb -> pickImageFromGallery());


                builder.setNegativeButton("Ignore", (dialog, which) -> {

                });
                builder.setPositiveButton("Done", (dialog, which) -> {
                    double price = Double.parseDouble(t_price.getText().toString());
                    String type = t_type.getText().toString();
                    String desc = t_desc.getText().toString();
                    String roomNumber = t_roomNumber.getText().toString();

                    String imagePath;

                    // لو المستخدم اختار صورة
                    if (selectedImageUri != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                            File file = new File(getFilesDir(), "room_" + System.currentTimeMillis() + ".jpg");
                            OutputStream outputStream = new FileOutputStream(file);

                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = inputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }

                            outputStream.close();
                            inputStream.close();

                            imagePath = file.getAbsolutePath();

                        } catch (IOException e) {
                            e.printStackTrace();
                            // fallback لصورة افتراضية
                            imagePath = "android.resource://" + getPackageName() + "/" + R.drawable.bg_button;
                        }
                    } else {
                        // fallback لصورة افتراضية
                        imagePath = "android.resource://" + getPackageName() + "/" + R.drawable.bg_button;
                    }


                    RoomDetailsAdmin newRoom = new RoomDetailsAdmin(roomNumber, type,price,1, imagePath, desc);  // أو مع صورة حسب اختيارك
                    boolean added = db.addRoom(newRoom);

                    if (added) {
                        Toast.makeText(AdminHomeActivity.this, "Room added successfully!", Toast.LENGTH_SHORT).show();
                        roomList.clear();
                        roomList.addAll(db.getAllRooms());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AdminHomeActivity.this, "Failed to add room.", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                });

                builder.show();


            }
        });


    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    Toast.makeText(AdminHomeActivity.this, "تم اختيار صورة", Toast.LENGTH_SHORT).show();
                }
            });

}