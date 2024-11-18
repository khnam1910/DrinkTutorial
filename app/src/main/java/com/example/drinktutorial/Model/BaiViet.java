package com.example.drinktutorial.Model;

public class BaiViet {
<<<<<<< Updated upstream
=======
    private String keyID;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
=======
    public BaiViet(String keyId, String title, String imgUrl, String content, String date) {
        this.keyID = keyId;
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.date = date;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

>>>>>>> Stashed changes
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
