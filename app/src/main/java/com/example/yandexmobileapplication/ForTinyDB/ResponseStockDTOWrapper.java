package com.example.yandexmobileapplication.ForTinyDB;

import com.example.yandexmobileapplication.ApiDataLists;

import java.util.LinkedList;

public class ResponseStockDTOWrapper {
    LinkedList<ApiDataLists> responseStockDTO;

    public  ResponseStockDTOWrapper(LinkedList<ApiDataLists> responseStockDTO) {
        this.responseStockDTO = responseStockDTO;
    }

    public LinkedList<ApiDataLists> getResponseStockDTO() {
        return responseStockDTO;
    }
}
