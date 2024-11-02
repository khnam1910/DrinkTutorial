package com.example.drinktutorial.Adapter;

import com.bumptech.glide.Glide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinktutorial.Model.CustomItem;
import com.example.drinktutorial.R;

import java.util.ArrayList;

public class CustomAdapterCarousel extends RecyclerView.Adapter<CustomAdapterCarousel.ViewHolder>
{
    ArrayList<CustomItem> ItemList;
    public CustomAdapterCarousel(ArrayList<CustomItem> ItemList)
    {
        this.ItemList = ItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_carousel, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomItem item = ItemList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .into(holder.imageView);
        holder.textView.setText(item.getText());
    }


    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgNews);
            textView=itemView.findViewById(R.id.tvTitleNews);
        }
    }
}
