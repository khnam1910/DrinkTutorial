package com.example.drinktutorial.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.drinktutorial.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    public void addControls()
    {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        frameLayout = (FrameLayout)findViewById(R.id.fragmentLoad);
        tvTitle =(TextView) findViewById(R.id.tvTitle);

    }

    public void addEvents()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(R.id.btnHome == item.getItemId())
                {
                    loadFragment(new HomeFragment());
                    Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                    tvTitle.setText("Trang chủ");
                    return true;
                }
                if(R.id.btnSearch == item.getItemId())
                {
                    loadFragment(new SearchFragment());
//                    actionBar.setTitle("Home");
                    tvTitle.setText("Tìm kiếm");


                    return true;
                }
                if(R.id.btnNews == item.getItemId())
                {
                    loadFragment(new NewsFragment());
//                    actionBar.setTitle("Home");
                    tvTitle.setText("Tin tức");

                    return true;
                }
                if(R.id.btnUser == item.getItemId())
                {
                    loadFragment(new UserFragment());
//                    actionBar.setTitle("Home");
                    tvTitle.setText("Người dùng");
                    return true;
                }
                return false;
            }
        });
    }

    public void loadFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        ft.replace(R.id.fragmentLoad,fragment);
        ft.commit();
    }
}