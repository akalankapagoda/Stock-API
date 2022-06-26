package com.payconiq.stock.stocksapi.service;

import com.payconiq.stock.stocksapi.model.ListStocksResponse;
import com.payconiq.stock.stocksapi.model.Stock;
import com.payconiq.stock.stocksapi.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Stock service which operates on Stock entities.
 */
@Service
public class StocksService {

    private final BigInteger ZERO = new BigInteger("0");

    @Autowired
    private StockRepository stockRepository;

    /**
     * List stocks paginated by id.
     *
     * @param pageNo The page number
     * @param pageSize Page size
     *
     * @return Stocks list for the current page along with pagination information
     */
    public ListStocksResponse listStocks(Integer pageNo, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));

        Page<Stock> pagedResult = stockRepository.findAll(paging);

        ListStocksResponse response = new ListStocksResponse();

        if (pagedResult.hasContent()) {
            response.setStocks(pagedResult.getContent());
            response.setTotalElements(pagedResult.getTotalElements());
            response.setTotalPages(pagedResult.getTotalPages());

        } else {
            response.setTotalPages(0);
            response.setTotalElements(0L);
        }

        return response;
    }

    /**
     * Find a stock by id.
     *
     * @param id The stock id
     *
     * @return The Stock if found
     */
    public Optional<Stock> findById(Integer id) {
        return stockRepository.findById(id);
    }

    /**
     * Update an existing stock price.
     *
     * @param id The stock id of the stock to update
     * @param price The new price
     *
     * @return The udpated stock
     */
    public Stock updateStock(Integer id, BigInteger price) {
        if (price.compareTo(ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock price cannot be negative");
        }

        Optional<Stock> existingStockOptional = stockRepository.findById(id);

        if (existingStockOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not available");
        }

        Stock existingStock = existingStockOptional.get();

        existingStock.setCurrentPrice(price);

        return stockRepository.save(existingStock);
    }

    /**
     * Create a new stock.
     *
     * @param name Stock name
     * @param price Stock price
     *
     * @return Created stock entity
     */
    public Stock createStock(String name, BigInteger price) {
        if (price.compareTo(ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock price cannot be negative");
        }

        return stockRepository.create(name, price);
    }

    /**
     * Delete an existing stock
     *
     * @param id The Id of the stock to delete
     *
     * @return The deleted stock
     */
    public Stock deleteStock(Integer id) {
        Optional<Stock> existingStockOptional = stockRepository.findById(id);

        if (existingStockOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not available");
        }

        stockRepository.deleteById(id);

        return existingStockOptional.get();
    }

}
