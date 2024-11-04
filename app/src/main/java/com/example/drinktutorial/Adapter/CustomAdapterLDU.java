package com.example.drinktutorial.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.Model.LoaiDoUong;
import com.example.drinktutorial.R;

import java.util.ArrayList;

public class CustomAdapterLDU extends RecyclerView.Adapter<CustomAdapterLDU.ViewHolder>{
    ArrayList<LoaiDoUong> loaiDoUongs;

    public CustomAdapterLDU(ArrayList<LoaiDoUong> loaiDoUongs) {
        this.loaiDoUongs = loaiDoUongs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_drink, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiDoUong loaiDoUong = loaiDoUongs.get(position);
        Glide.with(holder.itemView.getContext())
                .load(loaiDoUong.getHinhAnh())
                .into(holder.imageView);
        holder.textView.setText(loaiDoUong.getTenLoai());
    }

    @Override
    public int getItemCount() {
        return loaiDoUongs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgDU);
            textView = itemView.findViewById(R.id.tvTenDU);
        }
    }
}
