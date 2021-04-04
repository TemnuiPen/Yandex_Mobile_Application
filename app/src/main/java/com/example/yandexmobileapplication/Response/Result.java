package com.example.yandexmobileapplication.Response;

import com.example.yandexmobileapplication.ApiDataLists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class Result {
    @SerializedName("description")
    @Expose
    public String description; // company name
    @SerializedName("displaySymbol")
    @Expose
    public String displaySymbol; // company ticker
    @SerializedName("symbol")
    @Expose
    public String symbol;
    @SerializedName("type")
    @Expose
    public String type;

    public Result(String description, String displaySymbol, String symbol, String type ) {
        this.description = description;
        this.displaySymbol = displaySymbol;
        this.symbol = symbol;
        this.type = type;
    }
}
