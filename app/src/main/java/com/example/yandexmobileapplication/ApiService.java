package com.example.yandexmobileapplication;

import com.example.yandexmobileapplication.Response.BestMatchingList;
import com.example.yandexmobileapplication.Response.Result;
import com.example.yandexmobileapplication.Response.StockPrice;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {
    @GET
    Call<StockPrice> getPrice(@Url String getPrice);

    @GET
    Call<BestMatchingList> getBestMatching(@Url String ticker);
}
