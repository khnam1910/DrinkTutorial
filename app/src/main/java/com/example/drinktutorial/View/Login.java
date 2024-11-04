package com.example.drinktutorial.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drinktutorial.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText txt_Username, txt_Password;
    TextView signupRedirectText;
    Button btn_Login;
    ImageView loginGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addEvents();
    }

    private void addControls() {
        txt_Username = findViewById(R.id.txt_Username);
        txt_Password = findViewById(R.id.txt_Password);
        btn_Login = findViewById(R.id.btn_Login);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginGoogle = findViewById(R.id.loginGoogle);
    }

    private void addEvents() {
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                    return;
                }
                checkUser();
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername() {
        String val = txt_Username.getText().toString();
        if (val.isEmpty()) {
            txt_Username.setError("Tài khoản không được bỏ trống");
            return false;
        } else {
            txt_Username.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = txt_Password.getText().toString();
        if (val.isEmpty()) {
            txt_Password.setError("Mật khẩu không được bỏ trống");
            return false;
        } else {
            txt_Password.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = txt_Username.getText().toString().trim();
        String userPassword = txt_Password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NguoiDung");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userFound = false;
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String usernameFromDB = userSnapshot.child("taikhoan").getValue(String.class);
                    Log.d("Login", "usernameFromDB: " + usernameFromDB);
                    if (usernameFromDB != null && usernameFromDB.equals(userUsername)) {
                        userFound = true;
                        String passwordFromDB = userSnapshot.child("matkhau").getValue(String.class);
                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                            String nameFromDB = userSnapshot.child("hoten").getValue(String.class);
                            String emailFromDB = userSnapshot.child("email").getValue(String.class);

                            // Show success message before starting the activity
                            Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("hoten", nameFromDB);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("taikhoan", usernameFromDB);
                            intent.putExtra("matkhau", passwordFromDB);

                            startActivity(intent);
                            finish();  // Close Login activity
                            return;
                        } else {
                            txt_Password.setError("Mật khẩu không đúng");
                            txt_Password.requestFocus();
                            return;
                        }
                    }
                }
                if (!userFound) {
                    txt_Username.setError("Tài khoản không tồn tại");
                    txt_Username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Đăng nhập thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
