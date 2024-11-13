package com.example.drinktutorial.Adapter.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Model.LoaiNguyenLieu;
import com.example.drinktutorial.R;

import java.util.ArrayList;

public class CutsomAdapterLNL extends BaseAdapter {
    public Context context;
    public ArrayList<LoaiNguyenLieu> loaiNguyenLieus;

    public CutsomAdapterLNL(Context context, ArrayList<LoaiNguyenLieu> loaiNguyenLieus) {
        this.context = context;
        this.loaiNguyenLieus = loaiNguyenLieus;
    }

    @Override
    public int getCount() {
        return loaiNguyenLieus.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiNguyenLieus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout_drink, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.imgDU);
        TextView textView = convertView.findViewById(R.id.tvTenDU);

        LoaiNguyenLieu loaiNguyenLieu = loaiNguyenLieus.get(position);
        textView.setText(loaiNguyenLieu.getTenLoai());

        Glide.with(context).load(loaiNguyenLieu.getHinhAnh()).into(imageView);
        return convertView;
    }
}
