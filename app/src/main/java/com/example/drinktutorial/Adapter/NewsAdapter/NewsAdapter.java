package com.example.drinktutorial.Adapter.NewsAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.News.Fragment_Detail_News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<BaiViet> newsList;

    public NewsAdapter(Context context, List<BaiViet> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.news_recycle_row, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        BaiViet currentNews = newsList.get(position);
        holder.titleNews.setText(currentNews.getTitle());
        holder.contentNews.setText(currentNews.getContent());
        holder.dateNews.setText(currentNews.getDate());

        Glide.with(context).load(currentNews.getImgUrl()).into(holder.imgNews);

        holder.itemView.setOnClickListener(v -> {
            // Truyền dữ liệu sang Fragment_Detail_News
            Bundle bundle = new Bundle();
            bundle.putString("title", currentNews.getTitle());
            bundle.putString("content", currentNews.getContent());
            bundle.putString("imageUrl", currentNews.getImgUrl());
            bundle.putString("date", currentNews.getDate());

            Fragment fragment = new Fragment_Detail_News();
            fragment.setArguments(bundle);

            // Chuyển đến Fragment_Detail_News
            if (context instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLoad, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleNews, contentNews ,dateNews;
        ImageView imgNews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleNews = itemView.findViewById(R.id.titleNews);
            contentNews = itemView.findViewById(R.id.contentNews);
            dateNews = itemView.findViewById(R.id.dateNews);
            imgNews = itemView.findViewById(R.id.imgNews);
        }
    }
}

