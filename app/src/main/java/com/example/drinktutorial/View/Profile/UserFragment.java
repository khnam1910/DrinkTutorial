package com.example.drinktutorial.View.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.Login;

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
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLoad, new Fragment_Setting());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        luu_baiviet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLoad, new Fragment_Save());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        thich_baiviet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLoad, new Fragment_Like());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        baomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLoad, new Fragment_Security());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        cskh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLoad, new Fragment_TakeCareCustomer());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void logout() {
        // Xóa thông tin đăng nhập
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Chuyển hướng đến màn hình đăng nhập và đóng các Activity trước đó
        Intent intent = new Intent(getActivity(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Đảm bảo không quay lại các màn hình trước
        startActivity(intent);
        getActivity().finish(); // Đảm bảo đóng Fragment hiện tại (UserFragment)
    }

}
