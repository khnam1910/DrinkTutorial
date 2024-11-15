package com.example.drinktutorial.View;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Model.NguoiDung;
import com.example.drinktutorial.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText txt_email, txt_Password;
    TextView signupRedirectText;
    Button btn_Login;
    ImageView loginGoogle;

    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if(o.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(o.getData());
                try{
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.apply();

                                Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Đăng nhập thất bại" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                catch (ApiException e)
                {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        FirebaseApp.initializeApp(this);
        addControls();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(Login.this, options);
        auth = FirebaseAuth.getInstance();

        addEvents();
    }

    private void addControls() {
        txt_email = findViewById(R.id.txt_email);
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
                Intent intent = mGoogleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });
    }

    public Boolean validateUsername() {
        String val = txt_email.getText().toString();
        if (val.isEmpty()) {
            txt_email.setError("Tài khoản không được bỏ trống");
            return false;
        } else {
            txt_email.setError(null);
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
        String userEmail = txt_email.getText().toString().trim();
        String userPassword = txt_Password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("NguoiDung");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userFound = false;
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String emailFromDB = userSnapshot.child("email").getValue(String.class);
                    Log.d("Login", "emailFromDB: " + emailFromDB);
                    if (emailFromDB != null && emailFromDB.equals(userEmail)) {
                        userFound = true;
                        String passwordFromDB = userSnapshot.child("matKhau").getValue(String.class);
                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                            //Lưu thông tin đăng nhập
                            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();

                            String nameFromDB = userSnapshot.child("hoTen").getValue(String.class);
                            String addressFromDB = userSnapshot.child("address").getValue(String.class);
                            String phoneFromDB = userSnapshot.child("phone").getValue(String.class);
                            String DOBFromDB = userSnapshot.child("DOB").getValue(String.class);

                            // Show success message before starting the activity
                            Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("hoTen", nameFromDB);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("address", addressFromDB);
                            intent.putExtra("phone", phoneFromDB);
                            intent.putExtra("DOB", DOBFromDB);
                            intent.putExtra("matKhau", passwordFromDB);

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
                    txt_email.setError("Tài khoản không tồn tại");
                    txt_email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Đăng nhập thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
