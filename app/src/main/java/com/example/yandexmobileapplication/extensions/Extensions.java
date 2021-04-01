package com.example.yandexmobileapplication.extensions;

import com.example.yandexmobileapplication.ApiDataLists;
import com.example.yandexmobileapplication.StockDTO;

import java.util.LinkedList;

public class Extensions {
    public Extensions() {
        // empty slot
    }
    public boolean isContainsApiDataList(ApiDataLists currentList, LinkedList<ApiDataLists> currentLinkedList) {
        for (int i = 0; i < currentLinkedList.size(); i++) {
            if (currentList.getCompanyCode().equals(currentLinkedList.get(i).getCompanyCode())) {
                return true;
            }
        }
        return false;
    }
    public boolean isContainsStockDTO(StockDTO currentList, LinkedList<StockDTO> currentLinkedList) {
        for(int i = 0; i < currentLinkedList.size(); i++) {
            if(currentList.getCompanyCode().equals(currentLinkedList.get(i).getCompanyCode())) {
                return true;
            }
        }
        return false;
    }

}
