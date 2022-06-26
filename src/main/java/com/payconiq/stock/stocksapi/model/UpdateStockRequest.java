package com.payconiq.stock.stocksapi.model;

import java.math.BigInteger;

public class UpdateStockRequest {

    private BigInteger currentPrice;

    public BigInteger getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigInteger currentPrice) {
        this.currentPrice = currentPrice;
    }
}
