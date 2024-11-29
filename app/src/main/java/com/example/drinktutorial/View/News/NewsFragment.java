package com.example.drinktutorial.View.News;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drinktutorial.Adapter.NewsAdapter.NewsAdapter;
import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<BaiViet> newsList;
    private LinearProgressIndicator progressBar;

    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

//        newsRecyclerView = view.findViewById(R.id.news_recycle);
//        progressBar = view.findViewById(R.id.progress_bar);
//        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        newsList = new ArrayList<>();
//        newsAdapter = new NewsAdapter(getContext(), newsList);
//        newsRecyclerView.setAdapter(newsAdapter);
//
//        // Lấy dữ liệu từ Firebase
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference newsRef = database.getReference("BaiViet");
//
//        // Hiển thị tiến trình khi đang tải dữ liệu
//        progressBar.setVisibility(View.VISIBLE);
//
//        newsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                newsList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    BaiViet news = snapshot.getValue(BaiViet.class);
//                    newsList.add(news);
//                }
//                newsAdapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle possible errors
//                progressBar.setVisibility(View.GONE);
//            }
//        });

        return view;
    }
}