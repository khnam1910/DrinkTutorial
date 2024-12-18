package com.example.drinktutorial.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drinktutorial.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText txt_email, txt_Password;
    TextView signupRedirectText;
    Button btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Kiểm tra trạng thái đăng nhập
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Nếu đã đăng nhập, chuyển sang MainActivity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Nếu chưa đăng nhập, hiển thị giao diện đăng nhập
        FirebaseApp.initializeApp(this);

        // Ánh xạ view
        txt_email = findViewById(R.id.txt_email);
        txt_Password = findViewById(R.id.txt_Password);
        btn_Login = findViewById(R.id.btn_Login);
        signupRedirectText = findViewById(R.id.signupRedirectText);

        // Bắt sự kiện
        btn_Login.setOnClickListener(v -> {
            if (!validateUsername() | !validatePassword()) return;
            checkUser();
        });

        signupRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
        });
    }

    private Boolean validateUsername() {
        String val = txt_email.getText().toString();
        if (val.isEmpty()) {
            txt_email.setError("Tài khoản không được bỏ trống");
            return false;
        } else {
            txt_email.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = txt_Password.getText().toString();
        if (val.isEmpty()) {
            txt_Password.setError("Mật khẩu không được bỏ trống");
            return false;
        } else {
            txt_Password.setError(null);
            return true;
        }
    }

    private void checkUser() {
        String userEmail = txt_email.getText().toString().trim();
        String userPassword = txt_Password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NguoiDung");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String emailFromDB = userSnapshot.child("email").getValue(String.class);

                    if (emailFromDB != null && emailFromDB.equals(userEmail)) {
                        String passwordFromDB = userSnapshot.child("matKhau").getValue(String.class);

                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                            // Lấy userId và lưu vào SharedPreferences
                            String userId = userSnapshot.getKey();
                            String hoTen = userSnapshot.child("hoTen").getValue(String.class);

                            //Lưu thông tin đăng nhập
                            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("userId", userId);
                            editor.putString("email", userEmail);
                            editor.putString("hoTen", hoTen);
                            editor.apply();

                            // Chuyển sang MainActivity
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            txt_Password.setError("Mật khẩu không đúng");
                            txt_Password.requestFocus();
                            return;
                        }
                    }
                }
                txt_email.setError("Tài khoản không tồn tại");
                txt_email.requestFocus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
