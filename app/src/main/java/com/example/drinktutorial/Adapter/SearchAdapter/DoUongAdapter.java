package com.example.drinktutorial.Adapter.SearchAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;

import java.util.ArrayList;
import java.util.List;

public class DoUongAdapter extends RecyclerView.Adapter<DoUongAdapter.DoUongViewHolder> {

    private Context context;
    private List<DoUong> doUongList;

    public DoUongAdapter(Context context, List<DoUong> doUongList) {
        this.context = context;
        this.doUongList = doUongList != null ? doUongList : new ArrayList<>();
    }

    @NonNull
    @Override
    public DoUongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout_douongs, parent, false);
        return new DoUongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoUongViewHolder holder, int position) {
        DoUong doUong = doUongList.get(position);

        // Thiết lập dữ liệu vào ViewHolder
        holder.tvTenDU.setText(doUong.getName());
        holder.tvMoTaDoUong.setText(doUong.getMoTa());

        // Sử dụng Glide để tải ảnh
        if (doUong.getHinhAnh() != null && !doUong.getHinhAnh().isEmpty()) {
            Glide.with(context).load(doUong.getHinhAnh()).into(holder.imgDoUong);
        }
    }

    @Override
    public int getItemCount() {
        return doUongList != null ? doUongList.size() : 0;
    }

    public static class DoUongViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDoUong;
        TextView tvTenDU, tvMoTaDoUong;

        public DoUongViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ các view từ item layout
            imgDoUong = itemView.findViewById(R.id.imgDoUong);
            tvTenDU = itemView.findViewById(R.id.tvTenDU);
            tvMoTaDoUong = itemView.findViewById(R.id.tvMoTaDoUong);
        }
    }


    public void updateList(List<DoUong> newDoUongList) {
        if (newDoUongList != null) {
            this.doUongList = newDoUongList;
        } else {
            this.doUongList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }
}