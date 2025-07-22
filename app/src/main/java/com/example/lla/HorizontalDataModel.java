package com.example.lla;

import java.util.List;

public class HorizontalDataModel {
    private String heading;
    private List<CardDataModel> cardDataList;
    public HorizontalDataModel(String heading, List<CardDataModel> cardDataList) {
        this.heading = heading;
        this.cardDataList = cardDataList;
    }
    public String getHeading() {
        return heading;
    }
    public List<CardDataModel> getCardDataList() {
        return cardDataList;
    }
}
