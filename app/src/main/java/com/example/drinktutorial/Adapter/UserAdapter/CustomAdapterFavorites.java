package com.example.drinktutorial.Adapter.UserAdapter;

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

import java.util.List;

public class CustomAdapterFavorites extends RecyclerView.Adapter<CustomAdapterFavorites.ViewHolder> {

    private List<DoUong> favoriteDrinks;

    public CustomAdapterFavorites(List<DoUong> favoriteDrinks) {
        this.favoriteDrinks = favoriteDrinks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_drink, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoUong doUong = favoriteDrinks.get(position);

        holder.tvTenDU.setText(doUong.getName());
        Glide.with(holder.imgDU.getContext()).load(doUong.getHinhAnh()).into(holder.imgDU);
    }

    @Override
    public int getItemCount() {
        return favoriteDrinks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDU;
        TextView tvTenDU;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDU = itemView.findViewById(R.id.imgDU);
            tvTenDU = itemView.findViewById(R.id.tvTenDU);
        }
    }
}