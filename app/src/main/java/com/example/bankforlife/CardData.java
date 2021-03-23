package com.example.bankforlife;

public class CardData {
    private String Title;
    private String Content;
    private int Image;

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public int getImage() {
        return Image;
    }

    public void setTitle(String title){
        this.Title = title;
    }

    public void setContent(String content){
        this.Content = content;
    }

    public void setImage(int image){
        this.Image = image;
    }
}
