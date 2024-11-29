package com.example.drinktutorial.View.News;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterCarousel;
import com.example.drinktutorial.Adapter.NewsAdapter.NewsAdapter;
import com.example.drinktutorial.Controller.BaiVietController;
import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private RecyclerView rycNews;
    private NewsAdapter newsAdapter;

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

        addControls(view);
        loadBaiViet();
        return view;
    }

    public void loadBaiViet()
    {
        BaiVietController baiVietController = new BaiVietController();
        baiVietController.getListBV(new BaiVietController.DataStatus() {
            @Override
            public void getAllBaiViet(ArrayList<BaiViet> Baiviet) {
                rycNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                newsAdapter = new NewsAdapter(Baiviet);
                rycNews.setAdapter(newsAdapter);
                progressBar.setVisibility(View.GONE);
                addCarouselScrollListener();
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
    public void addControls(View view)
    {
        rycNews = view.findViewById(R.id.rycNews);
        progressBar = view.findViewById(R.id.progress_bar);
    }
}