package com.example.drinktutorial.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.drinktutorial.R;
import com.example.drinktutorial.View.Home.HomeFragment;
import com.example.drinktutorial.View.News.NewsFragment;
import com.example.drinktutorial.View.Profile.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    TextView tvTitle;
    Toolbar toolbar;

    String titleDefault="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addControls();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

        if (tvTitle != null) {
            tvTitle.setText("Trang chủ");
        }
        loadFragment(new HomeFragment());
        addEvents();
    }

    public void addControls()
    {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        frameLayout = (FrameLayout)findViewById(R.id.fragmentLoad);
        tvTitle =(TextView) findViewById(R.id.tvTitle);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
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
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    return true;
                }
                if(R.id.btnSearch == item.getItemId())
                {
                    loadFragment(new SearchFragment());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    tvTitle.setText("Tìm kiếm");


                    return true;
                }
                if(R.id.btnNews == item.getItemId())
                {
                    loadFragment(new NewsFragment());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    tvTitle.setText("Tin tức");

                    return true;
                }
                if(R.id.btnUser == item.getItemId())
                {
                    loadFragment(new UserFragment());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    tvTitle.setText("Người dùng");
                    return true;
                }
                return false;
            }
        });
    }
    @Override public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleOnBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();

        if (backStackEntryCount > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }

        fragmentManager.addOnBackStackChangedListener(() -> {
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentLoad);


            if (currentFragment instanceof HomeFragment && tvTitle != null) {
                tvTitle.setText("Trang chủ");
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
            else if (currentFragment instanceof SearchFragment && tvTitle != null) {
                tvTitle.setText("Tìm kiếm");
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
            else if (currentFragment instanceof NewsFragment && tvTitle != null) {
                tvTitle.setText("Tin tức");
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
            else if (currentFragment instanceof UserFragment && tvTitle != null) {
                tvTitle.setText("Người dùng");
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
            else {
                if (tvTitle != null && getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });
    }


    @SuppressLint("MissingSuperCall")
    @Override public void onBackPressed()
    {
        handleOnBackPressed();
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentLoad, fragment);

        if (!(fragment instanceof HomeFragment || fragment instanceof SearchFragment
                || fragment instanceof NewsFragment || fragment instanceof UserFragment)) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }

        ft.commit();
    }
    public void updateTitle(String title) {
        tvTitle.setText(title);
        titleDefault = title;  // Lưu lại tiêu đề hiện tại
    }
    public void restoreTitle() {
        tvTitle.setText(titleDefault);
    }
}


