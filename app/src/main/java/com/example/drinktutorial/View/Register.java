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

import java.sql.Struct;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

import com.example.drinktutorial.Controller.PasswordBcrypt;

public class Register extends AppCompatActivity {

    EditText txt_name, txt_email, txt_Password, txt_againPass;
    TextView loginRedirectText;
    Button btn_Signup;
    FirebaseDatabase database;
    DatabaseReference reference;
    String otpCode;

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
        txt_Password = findViewById(R.id.txt_password);
        txt_againPass = findViewById(R.id.txt_againPass);
        btn_Signup = findViewById(R.id.btn_Signup);
        loginRedirectText = findViewById(R.id.loginRedirectText);
    }

    private void addEvents() {
        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
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

    private void registerUser() {
        String name = txt_name.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String password = txt_Password.getText().toString().trim();
        String againPass = txt_againPass.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            txt_name.setError("Họ và tên không được bỏ trống");
            txt_name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            txt_email.setError("Email không được bỏ trống");
            txt_email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            txt_Password.setError("Mật khẩu không được bỏ trống");
            txt_Password.requestFocus();
            return;
        }

        //pattern kiểm tra mkhau thỏa mãn các yêu cầu
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            txt_Password.setError("Mật khẩu không hợp lệ. Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt");
            txt_Password.requestFocus();
            return;
        }

        if (!password.equals(againPass)) {
            txt_againPass.setError("Mật khẩu không khớp");
            txt_againPass.requestFocus();
            return;
        }

        String encryptedPassword = PasswordBcrypt.encryptPassword(password);

        // Save user details to Firebase Database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("NguoiDung");

        String username = email.substring(0, email.indexOf("@"));
        NguoiDung nguoiDung = new NguoiDung(name, email, password, null, null, null, new HashMap<String, Boolean>());
        reference.child(username).setValue(nguoiDung).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Generate OTP
                otpCode = generateOTP();
                // Send OTP to email
                sendOTPEmail(email, otpCode);

                Toast.makeText(Register.this, "Đăng ký thành công. Mã OTP đã được gửi đến email của bạn.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, VerifyAccount.class);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                intent.putExtra("otpCode", otpCode);
                startActivity(intent);
            } else {
                Toast.makeText(Register.this, "Đăng ký không thành công. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

    private void sendOTPEmail(String email, String otpCode) {
        String subject = "Mã OTP của bạn";
        String message = "Mã OTP của bạn là: " + otpCode;
        MailAPI javaMailAPI = new MailAPI(email, subject, message);
        javaMailAPI.execute();
    }

}
