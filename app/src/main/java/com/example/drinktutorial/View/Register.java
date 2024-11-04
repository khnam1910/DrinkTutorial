package com.example.drinktutorial.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drinktutorial.Model.NguoiDung;
import com.example.drinktutorial.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText txt_name, txt_email, txt_Username, txt_Password;
    TextView loginRedirectText;
    Button btn_Signup;
    FirebaseDatabase database;
    DatabaseReference reference;

    // Regex pattern to validate email with @gmail.com domain
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@gmail\\.com$"
    );

    // Regex pattern to validate password (at least 8 characters, one uppercase, one lowercase, one number, and one special character)
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addControls();
        addEvents();
    }

    private void addControls() {
        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_Username = findViewById(R.id.txt_username);
        txt_Password = findViewById(R.id.txt_password);
        btn_Signup = findViewById(R.id.btn_Signup);
        loginRedirectText = findViewById(R.id.loginRedirectText);
    }

    private void addEvents() {
        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("NguoiDung");

                String name = txt_name.getText().toString();
                String email = txt_email.getText().toString();
                String username = txt_Username.getText().toString();
                String password = txt_Password.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    txt_name.setError("Họ tên không được bỏ trống");
                    txt_name.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    txt_email.setError("Email không được bỏ trống");
                    txt_email.requestFocus();
                } else if (!EMAIL_PATTERN.matcher(email).matches()) {
                    txt_email.setError("Email phải có đuôi @gmail.com");
                    txt_email.requestFocus();
                } else if (TextUtils.isEmpty(username)) {
                    txt_Username.setError("Tài khoản không được bỏ trống");
                    txt_Username.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    txt_Password.setError("Mật khẩu không được bỏ trống");
                    txt_Password.requestFocus();
                } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    txt_Password.setError("Mật khẩu phải có ít nhất 8 ký tự, bao gồm ký tự in hoa, ký tự thường, số và ký tự đặc biệt");
                    txt_Password.requestFocus();
                } else {
                    registerUser(name, email, username, password);
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser(String name, String email, String username, String password) {
        reference.orderByChild("TaiKhoan").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    txt_Username.setError("Tài khoản đã tồn tại");
                    txt_Username.requestFocus();
                } else {
                    NguoiDung newUser = new NguoiDung(name, email, username, password);
                    reference.child(username).setValue(newUser).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Register.this, "Đăng ký thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Register.this, "Đã xảy ra lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
