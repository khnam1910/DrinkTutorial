package com.example.drinktutorial.Adapter.HomeAdapter;

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

public class CustomAdapterDoUongs extends RecyclerView.Adapter<CustomAdapterDoUongs.ViewHolder> {
    ArrayList<DoUong> doUongs;
    OnItemClickListener listener;
    public CustomAdapterDoUongs(ArrayList<DoUong> doUongs) {
        this.doUongs = doUongs;
    }

    public interface OnItemClickListener{
        void onItemClick(DoUong doUong);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_douongs, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoUong doUong = doUongs.get(position);
        Glide.with(holder.itemView.getContext())
                .load(doUong.getHinhAnh())
                .into(holder.imageView);
        holder.tvTenDU.setText(doUong.getName());
        String moTa = doUong.getMoTa();
        if (moTa.length() > 100) {
            moTa = moTa.substring(0, 100) + "...";
        }
        holder.tvMoTa.setText(moTa);

        holder.itemView.setOnClickListener(view -> {
            if(listener != null)
                listener.onItemClick(doUong);
        });

    }

    @Override
    public int getItemCount() {
        return doUongs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTenDU, tvMoTa;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgDoUong);
            tvTenDU = itemView.findViewById(R.id.tvTenDU);
            tvMoTa = itemView.findViewById(R.id.tvMoTaDoUong);
        }
    }

    public void updateList(ArrayList<DoUong> newList) {
        this.doUongs = newList;
        notifyDataSetChanged(); // Cập nhật lại danh sách cho adapter
    }

}
