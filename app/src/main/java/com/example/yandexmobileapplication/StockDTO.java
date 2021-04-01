package com.example.yandexmobileapplication;

public class StockDTO {
    String companyCode;
    String companyName;
    String stockPrice;
    String priceChange;
    ApplicationStatus favouriteStatus;

    public StockDTO(String companyCode, String companyName, String stockPrice, String priceChange, ApplicationStatus favouriteStatus) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.stockPrice = stockPrice;
        this.priceChange = priceChange;
        this.favouriteStatus = favouriteStatus;
    }

    public String getCompanyCode() {
        return companyCode;
    }

}
