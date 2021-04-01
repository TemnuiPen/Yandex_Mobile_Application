package com.example.yandexmobileapplication;

public interface StockAdapterListener {
    void onMarkedAsFavourite(StockDTO stock);

    void onRemovedFromFavourite(StockDTO stock);
}
