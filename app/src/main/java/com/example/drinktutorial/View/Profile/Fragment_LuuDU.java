package com.example.drinktutorial.View.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.drinktutorial.View.Home.DoUongDetailFragment;
import com.example.drinktutorial.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_LuuDU extends Fragment {


    RecyclerView recyclerViewFavorites;
    CustomAdapterDoUongs adapterDoUongs;

    ArrayList<DoUong> favoriteDrinks = new ArrayList<>();

    public Fragment_LuuDU() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luu_du, container, false);

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
                ArrayList<String> favoriteIds = new ArrayList<>();

                for (DataSnapshot drinkSnapshot : snapshot.getChildren()) {
                    favoriteIds.add(drinkSnapshot.getKey());
                }

                fetchDrinkDetails(favoriteIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void fetchDrinkDetails(ArrayList<String> favoriteIds) {
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
                adapterDoUongs = new CustomAdapterDoUongs(favoriteDrinks);
                recyclerViewFavorites.setAdapter(adapterDoUongs);

                addItemClickListenerForDoUong();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }

    public void addItemClickListenerForDoUong()
    {
        adapterDoUongs.setOnItemClickListener(new CustomAdapterDoUongs.OnItemClickListener() {
            @Override
            public void onItemClick(DoUong doUong) {
//                        Log.d("Test", "onItemClick: "+doUong.getKeyID());

                Bundle bundle = new Bundle();
                bundle.putString("idDoUong",doUong.getKeyID());
                DoUongDetailFragment doUongDetailFragment = new DoUongDetailFragment();
                doUongDetailFragment.setArguments(bundle);

                if(getActivity() instanceof MainActivity)
                {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loadFragment(doUongDetailFragment);
                }
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                    Toolbar toolbar = activity.findViewById(R.id.toolbar);
                    activity.setSupportActionBar(toolbar);
                    activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
                    if (activity.getSupportActionBar() != null) {
                        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                }
            }
        });
    }

}