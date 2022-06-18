package com.payconiq.stock.stocksapi.repository;

import com.payconiq.stock.stocksapi.model.Stock;
import org.springframework.data.repository.CrudRepository;

/**
 * This will handle DB operations related to {@link Stock} entity for us.
 */
public interface StockRepository extends CrudRepository<Stock, Integer> {
}
