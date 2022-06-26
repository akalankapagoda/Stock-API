package com.payconiq.stock.stocksapi.repository;

import com.payconiq.stock.stocksapi.model.Stock;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Stock repository which supports CRUD operations.
 */
public interface PagingCrudStockRepository extends PagingAndSortingRepository<Stock, Integer> {
}
