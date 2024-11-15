package com.example.drinktutorial.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.drinktutorial.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VerifyAccount extends AppCompatActivity {
    EditText txt_otp;
    Button btn_verify_otp;
    FirebaseDatabase database;
    DatabaseReference reference;
    String otpCodeReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);

        addControls();

        otpCodeReceived = getIntent().getStringExtra("otpCode");

        addEvents();
    }

    private void addControls()
    {
        txt_otp = findViewById(R.id.txt_otp);
        btn_verify_otp = findViewById(R.id.btn_verify_otp);
    }

    private void addEvents()
    {
        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });
    }

    private void verifyOtp() {
        String otp = txt_otp.getText().toString().trim();

        if (TextUtils.isEmpty(otp)) {
            txt_otp.setError("Mã OTP không được bỏ trống");
            txt_otp.requestFocus();
            return;
        }

        Toast.makeText(VerifyAccount.this, "Xác thực OTP thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(VerifyAccount.this, Login.class);
        startActivity(intent);
        finish();
    }
}