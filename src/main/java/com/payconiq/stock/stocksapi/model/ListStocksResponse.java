package com.payconiq.stock.stocksapi.model;

import java.util.List;

/**
 * List stocks response with pagination information.
 */
public class ListStocksResponse {

    List<Stock> stocks;

    Integer totalPages;

    Long totalElements;

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
}
