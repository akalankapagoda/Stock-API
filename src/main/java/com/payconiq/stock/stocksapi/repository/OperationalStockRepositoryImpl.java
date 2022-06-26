package com.payconiq.stock.stocksapi.repository;


import com.payconiq.stock.stocksapi.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

/**
 * Operational repository implementation to handle advanced operations.
 */
public class OperationalStockRepositoryImpl implements OperationalStockRepository {

    @Autowired
    private PagingCrudStockRepository pagingCrudStockRepository;

    @Override
    public Stock create(String name, BigInteger price) {

        Stock stock = new Stock();

        stock.setName(name);
        stock.setCurrentPrice(price);

        return pagingCrudStockRepository.save(stock);
    }
}
