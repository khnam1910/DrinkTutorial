package com.example.drinktutorial.Model;

import java.util.ArrayList;

public class CustomItem {
    private String imageUrl;
    private String text;

    public CustomItem(String imageUrl, String text) {
        this.imageUrl = imageUrl;
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return text;
    }

    public ArrayList ItemList()
    {
        ArrayList<CustomItem> itemList = new ArrayList<>();
        itemList.add(new CustomItem("https://images2.thanhnien.vn/zoom/448_280/528068263637045248/2024/10/30/p1-online-173030062963390729935-97-0-737-1024-crop-17303006661241912607037.jpg", "Mẫu Tiêu Đề 1"));
        itemList.add(new CustomItem("https://images2.thanhnien.vn/zoom/448_280/528068263637045248/2024/10/30/p1-online-173030062963390729935-97-0-737-1024-crop-17303006661241912607037.jpg", "Mẫu Tiêu Đề 1"));
        itemList.add(new CustomItem("https://images2.thanhnien.vn/zoom/448_280/528068263637045248/2024/10/30/p1-online-173030062963390729935-97-0-737-1024-crop-17303006661241912607037.jpg", "Mẫu Tiêu Đề 1"));
        itemList.add(new CustomItem("https://images2.thanhnien.vn/zoom/448_280/528068263637045248/2024/10/30/p1-online-173030062963390729935-97-0-737-1024-crop-17303006661241912607037.jpg", "Mẫu Tiêu Đề 1"));
        return itemList;
    }
}


