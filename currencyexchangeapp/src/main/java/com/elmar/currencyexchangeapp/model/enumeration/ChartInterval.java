package com.elmar.currencyexchangeapp.model.enumeration;

public enum ChartInterval {
    MIN5("5min"),
    MIN60("60min"),
    DAILY("Daily");

    private final String duration;

    private ChartInterval(String s) {
        duration = s;
    }

    public String toString() {
        return this.duration;
    }
}
