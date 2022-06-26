package com.payconiq.stock.stocksapi.controller;

import com.payconiq.stock.stocksapi.model.CreateStockRequest;
import com.payconiq.stock.stocksapi.model.ListStocksResponse;
import com.payconiq.stock.stocksapi.model.Stock;
import com.payconiq.stock.stocksapi.model.UpdateStockRequest;
import com.payconiq.stock.stocksapi.service.StocksService;
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
    private StocksService stocksService;

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
     * List stocks as a paginated list.
     *
     * @param pageNo Page number, defaults to 0
     * @param pageSize Page Size, defaults to 10
     *
     * @return List of stocks for the provided page and pagination information
     */
    @GetMapping
    public ListStocksResponse listStocks(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        return stocksService.listStocks(pageNo, pageSize);
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


        Optional<Stock> stockOptional = stocksService.findById(id);

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

        return stocksService.createStock(createStockRequest.getName(), createStockRequest.getCurrentPrice());
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

        return stocksService.updateStock(id, updateStockRequest.getCurrentPrice());

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

        return stocksService.deleteStock(id);
    }


}
