package com.example.yandexmobileapplication.Response;

import java.util.LinkedList;

public class BestMatchingList {
    public int count;
    public LinkedList<Result> responsedStockDTO;

    public BestMatchingList(LinkedList<Result> responsedStockDTO) {
        this.responsedStockDTO = responsedStockDTO;
        this.count = responsedStockDTO.size();
    }
}
