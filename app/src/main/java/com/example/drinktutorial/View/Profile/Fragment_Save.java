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

import com.example.drinktutorial.Adapter.UserAdapter.CustomAdapterFavorites;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Save extends Fragment {

    private ImageView backSetting;
    RecyclerView recyclerViewFavorites;
    CustomAdapterFavorites adapterFavorites;
    List<DoUong> favoriteDrinks = new ArrayList<>();

    public Fragment_Save() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__save, container, false);

        recyclerViewFavorites = view.findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", getContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId != null) {
            loadFavoriteDrinks(userId);
        }

        return view;
    }

    private void loadFavoriteDrinks(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId).child("Yeuthich");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> favoriteIds = new ArrayList<>();

                for (DataSnapshot drinkSnapshot : snapshot.getChildren()) {
                    favoriteIds.add(drinkSnapshot.getKey()); // Lấy ID của đồ uống
                }

                fetchDrinkDetails(favoriteIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void fetchDrinkDetails(List<String> favoriteIds) {
        DatabaseReference drinkRef = FirebaseDatabase.getInstance().getReference("DoUong");
        drinkRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteDrinks.clear();

                for (DataSnapshot drinkSnapshot : snapshot.getChildren()) {
                    DoUong drink = drinkSnapshot.getValue(DoUong.class);
                    if (drink != null) {
                        // Gán key của mỗi đồ uống vào đối tượng
                        drink.setKeyID(drinkSnapshot.getKey());

                        // Kiểm tra nếu ID nằm trong danh sách yêu thích
                        if (favoriteIds.contains(drink.getKeyID())) {
                            favoriteDrinks.add(drink);
                        }
                    }
                }

                // Cập nhật Adapter
                adapterFavorites = new CustomAdapterFavorites(favoriteDrinks);
                recyclerViewFavorites.setAdapter(adapterFavorites);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
    private void addEvents() {
        backSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment userFragment = new UserFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLoad, userFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}