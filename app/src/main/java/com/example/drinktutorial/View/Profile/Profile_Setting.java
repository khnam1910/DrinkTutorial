package com.example.drinktutorial.View.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.example.drinktutorial.View.UserFragment;

import com.example.drinktutorial.R;
import com.example.drinktutorial.View.Login;

public class Profile_Setting extends AppCompatActivity {

    ImageView backSetting;
    TextView Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        addControls();
        addEvents();
    }

    public void addControls()
    {
        Logout = findViewById(R.id.logout);
        backSetting = findViewById(R.id.backSetting);
    }

    public void addEvents()
    {
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                // Chuyển hướng đến màn hình đăng nhập
                Intent intent = new Intent(Profile_Setting.this, Login.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Profile_Setting.this.finish();
            }
        });

        backSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment userFragment = new UserFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLoad, userFragment) // Đảm bảo ID này khớp với ViewGroup chứa các Fragment
                        .addToBackStack(null) // Cho phép người dùng quay lại nếu cần
                        .commit();
            }
        });
    }
}