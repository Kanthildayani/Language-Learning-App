package com.example.lla;

public class CardDataModel {

    private int imageResource;
    private String text;

    private int color;




    public CardDataModel(int imageResource, String text,int color) {
        this.imageResource = imageResource;
        this.text = text;
        this.color = color;

    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText() {
        return text;
    }

    public int getColor(){return color;}

}