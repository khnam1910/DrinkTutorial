package com.example.drinktutorial.Adapter;

import com.bumptech.glide.Glide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;

import java.util.ArrayList;

public class CustomAdapterHotDrink extends RecyclerView.Adapter<CustomAdapterHotDrink.ViewHolder> {
    ArrayList<DoUong> doUongs;

    public CustomAdapterHotDrink(ArrayList<DoUong> doUongs) {
        this.doUongs = doUongs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_drink, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoUong doUong = doUongs.get(position);
        Glide.with(holder.itemView.getContext())
                .load(doUong.getHinhAnh())
                .into(holder.imageView);
        holder.textView.setText(doUong.getName());
    }

    @Override
    public int getItemCount() {
        return doUongs.size();
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
