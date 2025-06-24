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

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_ucas.adapter.AdminRoomAdapter;
import com.example.finalproject_ucas.databinding.ActivityAdminHomeBinding;
import com.example.finalproject_ucas.databinding.ActivityUserHomeBinding;
import com.example.finalproject_ucas.model.RoomDetails;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {

    ActivityAdminHomeBinding binding;
    Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ArrayList<RoomDetails> roomDetailsArrayList = new ArrayList<RoomDetails>();


        AdminRoomAdapter adapter = new AdminRoomAdapter(AdminHomeActivity.this,roomDetailsArrayList);
        binding.rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        binding.rv.setLayoutManager(lm);




        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);
                builder.setTitle("add details country");
                View v_item = LayoutInflater.from(AdminHomeActivity.this).inflate(R.layout.add_room,null);
                builder.setView(v_item);
                TextView t_price = v_item.findViewById(R.id.editTextText);
                TextView t_type =  v_item.findViewById(R.id.editTextText2);
                TextView t_desc = v_item.findViewById(R.id.editTextText3);
                Button btn = v_item.findViewById(R.id.button);

                btn.setOnClickListener(vb -> pickImageFromGallery());


                builder.setNegativeButton("Ignore",(dialog, which) -> {

                });
                builder.setPositiveButton("Done",(dialog, which) -> {
                    double price =Double.parseDouble(t_price.getText().toString()) ;
                    String type = t_type.getText().toString();
                    String desc = t_desc.getText().toString();

                    Uri imageUri;
                    if (selectedImageUri != null) {
                        imageUri = selectedImageUri;
                    } else {
                        imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.bg_button);
                    }

                    roomDetailsArrayList.add(new RoomDetails(imageUri,price,type,desc));
                    Toast.makeText(AdminHomeActivity.this, "success add", Toast.LENGTH_SHORT).show();
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