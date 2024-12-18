package com.example.drinktutorial.View.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterDoUongs;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_History extends Fragment {
    RecyclerView rcyHistory;
    private String idDU;
    ArrayList<DoUong> historyDrinks = new ArrayList<>();
    CustomAdapterDoUongs adapterDoUongs;

    public Fragment_History() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", getContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);
        addControls(view);
        if(getArguments() != null)
        {
            idDU = getArguments().getString("idDoUong");
            if (userId != null && idDU != null) {
                addToHistory(userId, idDU);
                loadHistory(userId);
            }
        }
        return view;
    }

    public void addControls(View view)
    {
        rcyHistory = view.findViewById(R.id.rycHistory);
    }
    private void loadHistory(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId).child("LichSuXem");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> historyIds = new ArrayList<>();

                for (DataSnapshot drinkSnapshot : snapshot.getChildren()) {
                    historyIds.add(drinkSnapshot.getKey());
                }

                fetchDrinkDetails(historyIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void fetchDrinkDetails(ArrayList<String> historyIds) {
        DatabaseReference drinkRef = FirebaseDatabase.getInstance().getReference("DoUong");
        drinkRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyDrinks.clear();

                for (DataSnapshot drinkSnapshot : snapshot.getChildren()) {
                    DoUong drink = drinkSnapshot.getValue(DoUong.class);
                    if (drink != null) {

                        drink.setKeyID(drinkSnapshot.getKey());

                        if (historyIds.contains(drink.getKeyID())) {
                            historyDrinks.add(drink);
                        }
                    }
                }


                rcyHistory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                adapterDoUongs = new CustomAdapterDoUongs(historyDrinks);
                rcyHistory.setAdapter(adapterDoUongs);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
    private void addToHistory(String userId, String idDoUong) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId).child("LichSuXem");


        userRef.child(idDoUong).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    userRef.child(idDoUong).setValue(true); // Lưu true hoặc thông tin khác bạn muốn
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
}