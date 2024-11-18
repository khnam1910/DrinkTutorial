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

import java.util.List;

public class DoUongAdapter extends RecyclerView.Adapter<DoUongAdapter.DoUongViewHolder> {

    private Context context;
    private List<DoUong> doUongList;

    public DoUongAdapter(Context context, List<DoUong> doUongList) {
        this.context = context;
        this.doUongList = doUongList;
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
        holder.tvTenDU.setText(doUong.getName());
        holder.tvMoTaDoUong.setText(doUong.getMoTa());
        Glide.with(context).load(doUong.getHinhAnh()).into(holder.imgDoUong);
    }

    @Override
    public int getItemCount() {
        return doUongList.size();
    }

    public static class DoUongViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDoUong;
        TextView tvTenDU, tvMoTaDoUong;

        public DoUongViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDoUong = itemView.findViewById(R.id.imgDoUong);
            tvTenDU = itemView.findViewById(R.id.tvTenDU);
            tvMoTaDoUong = itemView.findViewById(R.id.tvMoTaDoUong);
        }
    }

    public void updateList(List<DoUong> newDoUongList) {
        doUongList = newDoUongList;
        notifyDataSetChanged();
    }
}