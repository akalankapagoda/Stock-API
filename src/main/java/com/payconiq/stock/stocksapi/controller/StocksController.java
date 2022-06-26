package com.payconiq.stock.stocksapi.controller;

import com.payconiq.stock.stocksapi.model.CreateStockRequest;
import com.payconiq.stock.stocksapi.model.Stock;
import com.payconiq.stock.stocksapi.model.UpdateStockRequest;
import com.payconiq.stock.stocksapi.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Controller application for managing Stocks.
 */
@RestController
@RequestMapping("api/stocks")
public class StocksController {

    private final BigInteger ZERO = new BigInteger("0");

    @Autowired
    private StockRepository stockRepository;

    /**
     * Health check endpoint.
     *
     * @return A simple hello message
     */
    @GetMapping("/hello")
    public String getHello() {
        return "Hello";
    }

    /**
     * Retrieve a specific stock.
     *
     * @param id Stock id
     *
     * @return The stock
     */
    @GetMapping("{id}")
    public Stock getStock(@PathVariable Integer id) {


        Optional<Stock> stockOptional = stockRepository.findById(id);

        if (stockOptional.isPresent()) {
            return stockOptional.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not available");
    }

    /**
     * Create a new stock.
     *
     * @param createStockRequest Stock details to update
     *
     * @return Created Stock
     */
    @PostMapping
    public Stock createStock(@RequestBody CreateStockRequest createStockRequest) {

        if (createStockRequest.getCurrentPrice().compareTo(ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock price cannot be negative");
        }

        return stockRepository.create(createStockRequest.getName(), createStockRequest.getCurrentPrice());
    }

    /**
     * Update an existing stock price.
     *
     * @param updateStockRequest New stock details to update
     *
     * @return Updated stock
     */
    @PatchMapping("{id}")
    public Stock updateStockPrice(@PathVariable Integer id, @RequestBody UpdateStockRequest updateStockRequest) {

        if (updateStockRequest.getCurrentPrice().compareTo(ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock price cannot be negative");
        }

        Optional<Stock> existingStockOptional = stockRepository.findById(id);

        if (existingStockOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not available");
        }

        Stock existingStock = existingStockOptional.get();

        existingStock.setCurrentPrice(updateStockRequest.getCurrentPrice());

        return stockRepository.save(existingStock);

    }

    /**
     * Delete an existing stock.
     *
     * @param id Stock id
     *
     * @return Deleted stock
     */
    @DeleteMapping("{id}")
    public Stock deleteStock(@PathVariable Integer id) {

        Optional<Stock> existingStockOptional = stockRepository.findById(id);

        if (existingStockOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not available");
        }

        stockRepository.deleteById(id);

        return existingStockOptional.get();
    }


}
