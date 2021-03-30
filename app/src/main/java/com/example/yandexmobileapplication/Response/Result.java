package com.example.yandexmobileapplication.Response;

import com.example.yandexmobileapplication.ApiDataLists;

import java.util.LinkedList;

public class Result {
    public  LinkedList<ApiDataLists> stockTickerAndDesc = null;

    public String companyTicker;
    public String companyName;
    String stockPrice;
    String priceChange;

    public Result(String companyTicker, String companyName, String stockPrice, String priceChange) {
        this.companyTicker = companyTicker;
        this.companyName = companyName;
        this.stockPrice = stockPrice;
        this.priceChange = priceChange;
    }
}
