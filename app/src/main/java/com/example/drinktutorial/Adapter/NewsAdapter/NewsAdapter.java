package com.example.drinktutorial.Adapter.NewsAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterDoUongs;
import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {


    private ArrayList<BaiViet> baiViets;
    OnItemClickListener listener;

    public NewsAdapter(ArrayList<BaiViet> baiViets) {
        this.baiViets = baiViets;
    }
    public interface OnItemClickListener{
        void onItemClick(BaiViet baiViet);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item, parent, false);
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
                    .into(holder.imgBV);
        }
        holder.tvTieuDeBV.setText(baiViet.getTieuDe());
        String noiDung = baiViet.getNoiDung();
        if (noiDung.length() > 100) {
            noiDung = noiDung.substring(0, 100) + "...";
        }
        holder.tvNoiDungBV.setText(noiDung);

        holder.itemView.setOnClickListener(view -> {
            if(listener != null)
                listener.onItemClick(baiViet);
        });

        holder.tvNgayDang.setText(baiViet.getDate());

    }

    @Override
    public int getItemCount() {
        return baiViets.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBV;
        TextView tvTieuDeBV, tvNoiDungBV, tvNgayDang;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            imgBV = itemView.findViewById(R.id.imgBV);
            tvTieuDeBV = itemView.findViewById(R.id.tvTieuDeBV);
            tvNgayDang = itemView.findViewById(R.id.tvNgayDang);
            tvNoiDungBV = itemView.findViewById(R.id.tvNoiDungBV);
        }
    }

//    public void updateList(ArrayList<BaiViet> newList) {
//        this.baiViets = newList;
//        notifyDataSetChanged(); // Cập nhật lại danh sách cho adapter
//    }
}

