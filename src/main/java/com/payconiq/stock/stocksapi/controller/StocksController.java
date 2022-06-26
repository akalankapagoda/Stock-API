package com.payconiq.stock.stocksapi.controller;

import com.payconiq.stock.stocksapi.model.Stock;
import com.payconiq.stock.stocksapi.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Controller application for managing Stocks.
 */
@RestController
@RequestMapping("api/stocks")
public class StocksController {

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
     * @param id
     * @return
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
     * @param stock Stock object with name and current price
     *
     * @return Created Stock
     */
    @PostMapping
    public Stock createStock(@RequestBody Stock stock) {

        return stockRepository.create(stock.getName(), stock.getCurrentPrice());
    }


}
