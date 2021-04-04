package com.example.yandexmobileapplication;

import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;

public class ApiDataLists {
    public TextView companyStock;

    public String getCompanyCode() {
        return companyCode;
    }

    TextView stockChange;
    String companyName;
    String companyCode;
    ApplicationStatus favouriteStatus;

    public static final String key = "CACHED_STOCKS";

    public ApiDataLists(String companyName, String companyCode, ApplicationStatus favouriteStatus) {
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.favouriteStatus = favouriteStatus;
    }

    public ApiDataLists() {
        // empty slot
    }
}
