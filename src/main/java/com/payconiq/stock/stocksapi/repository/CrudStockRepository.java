package com.payconiq.stock.stocksapi.repository;

import com.payconiq.stock.stocksapi.model.Stock;
import org.springframework.data.repository.CrudRepository;

/**
 * Stock repository which supports CRUD operations.
 */
public interface CrudStockRepository  extends CrudRepository<Stock, Integer> {
}
