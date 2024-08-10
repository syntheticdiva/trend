package com.example.trend.model;

import java.util.List;

public class TrendData {
    private List<Double> trend1;
    private List<Double> trend2;

    public TrendData(List<Double> trend1, List<Double> trend2) {
        this.trend1 = trend1;
        this.trend2 = trend2;
    }

    public List<Double> getTrend1() {
        return trend1;
    }

    public List<Double> getTrend2() {
        return trend2;
    }
}