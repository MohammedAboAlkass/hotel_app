package com.example.finalproject_ucas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_ucas.adapter.RoomDetailsAdapter;
import com.example.finalproject_ucas.adapter.SpacesItemDecoration;
import com.example.finalproject_ucas.databinding.ActivityUserHomeBinding;
import com.example.finalproject_ucas.model.RoomDetailsAdmin;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {
     ActivityUserHomeBinding binding;

    DBHelper dbHelper;
    ArrayList<RoomDetailsAdmin> roomList;
    RoomDetailsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);

        setSupportActionBar(binding.toolbar);
        dbHelper = new DBHelper(this);


        roomList = dbHelper.getAllRooms();  // جلب بيانات الغرف

         adapter = new RoomDetailsAdapter(this,roomList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);


        binding.recyclerViewID.setLayoutManager(lm);
        binding.recyclerViewID.setAdapter(adapter);
        binding.recyclerViewID.addItemDecoration(new SpacesItemDecoration(50));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settingID) {
            Toast.makeText(this, "تم اختيار: إضافة", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.reserveID) {
            Intent i = new Intent(this, MyBooking.class);
            startActivity(i);
            
        }else if (id == R.id.logoutID) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserHomeActivity.this);
            builder.setTitle("هل تريد فعلا الخروج من التطبيق");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(UserHomeActivity.this, LoginScreen.class);
                    startActivity(i);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        }
        return super.onOptionsItemSelected(item);
    }
}