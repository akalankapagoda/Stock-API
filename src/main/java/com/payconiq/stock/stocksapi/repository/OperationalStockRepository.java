package com.payconiq.stock.stocksapi.repository;

import com.payconiq.stock.stocksapi.model.Stock;

import java.math.BigInteger;

/**
 * A repository to handle special functions other than CRUD operations.
 */
public interface OperationalStockRepository {

    /**
     * Create a new stock.
     *
     * @param name The stock name
     * @param price The stock price
     *
     * @return Created stock
     */
    public Stock create(String name, BigInteger price);
}
