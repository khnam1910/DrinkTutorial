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
import com.example.drinktutorial.Model.NguyenLieu;
import com.example.drinktutorial.R;

import java.util.ArrayList;

public class CustomAdapterNguyenLieu extends RecyclerView.Adapter<CustomAdapterNguyenLieu.ViewHolder>{

    ArrayList<NguyenLieu> nguyenLieus;

//    OnItemClickListener listener;

    public CustomAdapterNguyenLieu(ArrayList<NguyenLieu> nguyenLieus) {
        this.nguyenLieus = nguyenLieus;
    }

//    public interface  OnItemClickListener{
//        void onItemClick(NguyenLieu nguyenLieu);
//    }
//    public void setOnItemClickListener(OnItemClickListener listener)
//    {
//        this.listener = listener;
//    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_drink, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NguyenLieu nguyenLieu = nguyenLieus.get(position);
        Glide.with(holder.itemView.getContext())
                .load(nguyenLieu.getAnh())
                .into(holder.imageView);
        holder.textView.setText(nguyenLieu.getTen());

    }

    @Override
    public int getItemCount() {
        return nguyenLieus.size();
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
