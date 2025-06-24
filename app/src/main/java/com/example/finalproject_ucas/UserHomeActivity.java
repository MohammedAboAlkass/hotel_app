package com.example.finalproject_ucas;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_ucas.adapter.RoomDetailsAdapter;
import com.example.finalproject_ucas.adapter.SpacesItemDecoration;
import com.example.finalproject_ucas.databinding.ActivityLoginScreenBinding;
import com.example.finalproject_ucas.databinding.ActivityUserHomeBinding;
import com.example.finalproject_ucas.model.RoomDetails;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {
     ActivityUserHomeBinding binding;

     ArrayList<RoomDetails> roomArrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);

        setSupportActionBar(binding.toolbar);


        roomArrayList = new ArrayList<>();

        RoomDetailsAdapter adapter = new RoomDetailsAdapter(this,roomArrayList);
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
            
        }
        return super.onOptionsItemSelected(item);
    }
}