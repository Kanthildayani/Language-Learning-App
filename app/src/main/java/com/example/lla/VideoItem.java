package com.example.lla;

public class VideoItem {
    private String heading;
    private int imageResource;

    public VideoItem(String heading, int imageResource) {
        this.heading = heading;
        this.imageResource = imageResource;
    }

    public String getHeading() {
        return heading;
    }


    public int getImage() {
        return imageResource;
    }
}
