package com.example.drinktutorial.View.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterDoUongs;
import com.example.drinktutorial.Adapter.NewsAdapter.NewsAdapter;
import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;
import com.example.drinktutorial.View.News.BaiVietDetailFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_LuuBV extends Fragment {

    RecyclerView rcyNewsFavour;
    ArrayList<BaiViet> favoriteNews = new ArrayList<>();
    NewsAdapter newsAdapter;
    public Fragment_LuuBV() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_luu_bv, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", getContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);
        addControls(view);
        if (userId != null) {
            loadFavoriteNews(userId);
        }

        return view;
    }

    public void addControls(View view)
    {
        rcyNewsFavour = view.findViewById(R.id.rcyNewFavour);
    }

    private void loadFavoriteNews(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId).child("BaiVietYeuThich");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> favoriteIds = new ArrayList<>();

                for (DataSnapshot drinkSnapshot : snapshot.getChildren()) {
                    favoriteIds.add(drinkSnapshot.getKey());
                }

                fetchBaiVietDetails(favoriteIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void fetchBaiVietDetails(ArrayList<String> favoriteBVIds) {
        DatabaseReference newsRef = FirebaseDatabase.getInstance().getReference("BaiViet");
        newsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteNews.clear();

                for (DataSnapshot drinkSnapshot : snapshot.getChildren()) {
                    BaiViet baiViet = drinkSnapshot.getValue(BaiViet.class);
                    if (baiViet != null) {

                        baiViet.setKeyID(drinkSnapshot.getKey());

                        if (favoriteBVIds.contains(baiViet.getKeyID())) {
                            favoriteNews.add(baiViet);
                        }

                    }
                }
                rcyNewsFavour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                newsAdapter = new NewsAdapter(favoriteNews);
                rcyNewsFavour.setAdapter(newsAdapter);

                addCarouselScrollListener();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
    private void addCarouselScrollListener() {
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaiViet baiViet) {
                Bundle bundle = new Bundle();
                bundle.putString("BaiVietID", baiViet.getKeyID());
                BaiVietDetailFragment baiVietDetailFragment = new BaiVietDetailFragment();
                baiVietDetailFragment.setArguments(bundle);

                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loadFragment(baiVietDetailFragment);

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
    }
}