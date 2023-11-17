package com.example.cryptocurrency;

import android.graphics.Bitmap;

public class CryptoCurrency {

    String name;
    String id;
    String symbol;
    String logo;
    String amount;
    String twentyFourHourPercentage;
    Bitmap bitmap;

    public void setId(String id) {
        this.id = id;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setTwentyFourHourPercentage(String twentyFourHourPercentage) {
        this.twentyFourHourPercentage = twentyFourHourPercentage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getAmount() {
        return amount;
    }

    public String getTwentyFourHourPercentage() {
        return twentyFourHourPercentage;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getLogo() {
        return logo;
    }
}
