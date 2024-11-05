package com.example.drinktutorial.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.Profile.Profile_Setting;

public class UserFragment extends Fragment {

    Button btnLogout;
    RelativeLayout information, luu_baiviet, thich_baiviet, setting, baomat, cskh;

    public UserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Gọi các phương thức để khởi tạo các control và sự kiện
        addControls(view);
        addEvents();

        return view;
    }

    public void addControls(View view) {
        btnLogout = view.findViewById(R.id.btn_Logout);
        information = view.findViewById(R.id.information);
        luu_baiviet = view.findViewById(R.id.luu_baiviet);
        thich_baiviet = view.findViewById(R.id.thich_baiviet);
        setting = view.findViewById(R.id.setting);
        baomat = view.findViewById(R.id.baomat);
        cskh = view.findViewById(R.id.cskh);
    }

    public void addEvents() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Profile_Setting.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    private void logout() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Chuyển hướng đến màn hình đăng nhập
        Intent intent = new Intent(getActivity(), Login.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
