package com.example.drinktutorial.Adapter.HomeAdapter;

import com.bumptech.glide.Glide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.R;

import java.util.ArrayList;

public class CustomAdapterCarousel extends RecyclerView.Adapter<CustomAdapterCarousel.ViewHolder> {
    ArrayList<BaiViet> baiViets;
    OnItemClickListener listener;

    public CustomAdapterCarousel(ArrayList<BaiViet> baiViets) {
        this.baiViets = baiViets;
    }

    public interface OnItemClickListener
    {
        void onItemClick(BaiViet baiViet);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_carousel, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiViet baiViet = baiViets.get(position);


        String firstImageUrl = null;

        if (baiViet.getHinhAnh() != null && !baiViet.getHinhAnh().isEmpty()) {
            firstImageUrl = baiViet.getHinhAnh().values().iterator().next();
        }


        if (firstImageUrl != null) {
            Glide.with(holder.itemView.getContext())
                    .load(firstImageUrl)
                    .into(holder.imageView);
        }


        holder.textView.setText(baiViet.getTieuDe());

        holder.itemView.setOnClickListener(view -> {
            if(listener != null)
                listener.onItemClick(baiViet);
        });
    }

    @Override
    public int getItemCount() {
        return baiViets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgNews);
            textView = itemView.findViewById(R.id.tvTitleNews);
        }
    }
}
