package com.example.drinktutorial.View.News;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.R;

public class Fragment_Detail_News extends Fragment {

    private TextView txtTitle, txtContent, txtDate;
    private ImageView imgNews;

    public Fragment_Detail_News() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__detail__news, container, false);

        txtTitle = view.findViewById(R.id.txtTitle);
        txtContent = view.findViewById(R.id.txtContent);
        txtDate = view.findViewById(R.id.txtDate);
        imgNews = view.findViewById(R.id.imgNews);

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String content = getArguments().getString("content");
            String imageUrl = getArguments().getString("imageUrl");
            String date = getArguments().getString("date");

            // Hiển thị dữ liệu lên giao diện
            txtTitle.setText(title);
            txtContent.setText(content);
            txtDate.setText(date);

            Glide.with(getContext())
                    .load(imageUrl)
                    .into(imgNews);
        }


        return view;
    }
}