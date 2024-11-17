package com.example.drinktutorial.Model;

public class BaiViet {
    private String title;
    private String imgUrl;
    private String content;
    private String date;

    public BaiViet() {
    }

    public BaiViet(String title, String imgUrl, String content, String date) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
