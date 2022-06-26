package com.payconiq.stock.stocksapi.controller;

import com.payconiq.stock.stocksapi.model.CreateStockRequest;
import com.payconiq.stock.stocksapi.model.ListStocksResponse;
import com.payconiq.stock.stocksapi.model.Stock;
import com.payconiq.stock.stocksapi.model.UpdateStockRequest;
import com.payconiq.stock.stocksapi.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test StocksController.
 */
@SpringBootTest
public class StockControllerTest {

    @MockBean
    private static StockRepository mockStockRepository;

    @Autowired
    private StocksController stocksController;

    private static Stock firstStock;

    @BeforeAll
    private static void init() {

        firstStock = new Stock();
        firstStock.setId(1);
        firstStock.setName("FirstCompany");
        firstStock.setCurrentPrice(new BigInteger("150"));
        firstStock.setLastUpdated(new Date());
    }

    /**
     * Test paginated listing of stocks.
     */
    @Test
    public void testListStocks() {

        Pageable paging10 = PageRequest.of(9, 10, Sort.by("id"));

        List<Stock> stockList = new ArrayList<>();
        stockList.add(firstStock);

        Page<Stock> page = new PageImpl<>(stockList, paging10, 999L);

        when(mockStockRepository.findAll(paging10)).thenReturn(page);

        ListStocksResponse listStocksResponse = stocksController.listStocks(9, 10);

        assertThat(listStocksResponse.getStocks()).isNotNull();
        assertThat(listStocksResponse.getStocks().size()).isEqualTo(1);
        assertThat(listStocksResponse.getStocks().get(0)).isEqualTo(firstStock);
        assertThat(listStocksResponse.getTotalElements()).isEqualTo(999L);
        assertThat(listStocksResponse.getTotalPages()).isEqualTo(100);

    }

    @Test
    public void testGetStock() {
        when(mockStockRepository.findById(1)).thenReturn(Optional.ofNullable(firstStock));

        Stock stock = stocksController.getStock(firstStock.getId());

        assertThat(stock).isNotNull();

        assertThat(stock.getName()).isEqualTo(firstStock.getName());
    }

    @Test
    public void testCreateStock() {

        String name = "newStockName";
        BigInteger newPrice = new BigInteger("1000");

        when(mockStockRepository.create(name, newPrice)).thenReturn(firstStock);

        CreateStockRequest newStock = new CreateStockRequest();
        newStock.setName(name);
        newStock.setCurrentPrice(newPrice);

        Stock createdStock = stocksController.createStock(newStock);

        assertThat(createdStock).isNotNull();

        assertThat(createdStock.getName()).isEqualTo(firstStock.getName());
    }

    @Test
    public void testUpdateStock() {

        when(mockStockRepository.findById(2)).thenReturn(Optional.ofNullable(firstStock));
        when(mockStockRepository.save(firstStock)).thenReturn(firstStock);

        UpdateStockRequest updateStockRequest = new UpdateStockRequest();
        updateStockRequest.setCurrentPrice(new BigInteger("100"));

        Stock updatedStock = stocksController.updateStockPrice(2, updateStockRequest);

        assertThat(updatedStock).isNotNull();

        assertThat(updatedStock.getName()).isEqualTo(firstStock.getName());
        assertThat(updatedStock.getCurrentPrice()).isEqualTo(firstStock.getCurrentPrice());
    }

    /**
     * Test updating a non-existing stock leads to failure.
     */
    @Test
    public void testNonExistingUpdateStock() {

        when(mockStockRepository.findById(2)).thenReturn(Optional.empty());

        UpdateStockRequest updateStockRequest = new UpdateStockRequest();
        updateStockRequest.setCurrentPrice(new BigInteger("100"));

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
                stocksController.updateStockPrice(2, updateStockRequest));



        assertThat(exception.getReason()).isEqualTo("Stock not available");
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteStock() {

        when(mockStockRepository.findById(firstStock.getId())).thenReturn(Optional.ofNullable(firstStock));

        stocksController.deleteStock(firstStock.getId());

        verify(mockStockRepository).deleteById(firstStock.getId());
    }

    /**
     * Test deleting non-existing stock.
     */
    @Test
    public void testDeleteNonExistingStock() {

        when(mockStockRepository.findById(firstStock.getId())).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
                stocksController.deleteStock(firstStock.getId()));

        assertThat(exception.getReason()).isEqualTo("Stock not available");
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
