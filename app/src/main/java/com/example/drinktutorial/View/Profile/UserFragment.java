package com.example.drinktutorial.View.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.drinktutorial.R;
import com.example.drinktutorial.View.Login;
import com.example.drinktutorial.View.MainActivity;

public class UserFragment extends Fragment {

    Button btnLogout;
    RelativeLayout information, luu_CongThuc, luu_BaiViet, setting, lichsuxem, cskh;
    TextView username;

    public UserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);


        addControls(view);
        addEvents();

        displayUserName();

        return view;
    }

    public void addControls(View view) {
        btnLogout = view.findViewById(R.id.btn_Logout);
        information = view.findViewById(R.id.information);
        luu_CongThuc = view.findViewById(R.id.luu_Congthuc);
        luu_BaiViet = view.findViewById(R.id.luu_BaiViet);
        cskh = view.findViewById(R.id.trungtam_hotro);
        setting = view.findViewById(R.id.caidat);
        username = view.findViewById(R.id.username);
        lichsuxem = view.findViewById(R.id.daxem_ganday);
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

        luu_CongThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_LuuDU fragmentSave = new Fragment_LuuDU();
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loadFragment(fragmentSave);
                    mainActivity.updateTitle("Danh sách yêu thích");
                }

                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                    Toolbar toolbar = activity.findViewById(R.id.toolbar);
                    activity.setSupportActionBar(toolbar);
                    activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
                    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });

        luu_BaiViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_LuuBV fragmentLuuBV = new Fragment_LuuBV();
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loadFragment(fragmentLuuBV);
                    mainActivity.updateTitle("Danh sách bài viết yêu thích");
                }

                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                    Toolbar toolbar = activity.findViewById(R.id.toolbar);
                    activity.setSupportActionBar(toolbar);
                    activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
                    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });

        lichsuxem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_History fragmentHistory = new Fragment_History();
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loadFragment(fragmentHistory);
                    mainActivity.updateTitle("Lịch sử xem");
                }

                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                    Toolbar toolbar = activity.findViewById(R.id.toolbar);
                    activity.setSupportActionBar(toolbar);
                    activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
                    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void displayUserName() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("hoTen", "User");

        // Cập nhật tên người dùng lên TextView
        if (username != null) {
            username.setText(userName);
        }
    }
}
