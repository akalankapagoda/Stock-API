package com.payconiq.stock.stocksapi.model;

import java.math.BigInteger;

public class CreateStockRequest {

    private String name;

    private BigInteger currentPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigInteger currentPrice) {
        this.currentPrice = currentPrice;
    }
}
