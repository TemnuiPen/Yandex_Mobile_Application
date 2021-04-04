package com.example.yandexmobileapplication.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class BestMatchingList {
    @SerializedName("count")
    @Expose
    public int count;

    @SerializedName("result")
    @Expose
    public LinkedList<Result> result;

    public BestMatchingList(LinkedList<Result> resultList) {
        this.result = resultList;
        this.count = resultList.size();
    }
}
