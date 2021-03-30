package com.example.yandexmobileapplication.Response;

public class StockPrice {
    public double c;
    public double h;
    public double l;
    public double o;
    public double pc;
    public int t;

    public StockPrice(double c, double h, double l, double o, double pc, int t) {
        this.c = c;
        this.h = h;
        this.l = l;
        this.o = o;
        this.pc = pc;
        this.t = t;
    }
}
