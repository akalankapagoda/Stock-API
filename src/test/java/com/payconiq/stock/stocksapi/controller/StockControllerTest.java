package com.payconiq.stock.stocksapi.controller;

import com.payconiq.stock.stocksapi.model.Stock;
import com.payconiq.stock.stocksapi.repository.StockRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

        Stock newStock = new Stock();
        newStock.setName(name);
        newStock.setCurrentPrice(newPrice);

        Stock createdStock = stocksController.createStock(newStock);

        assertThat(createdStock).isNotNull();

        assertThat(createdStock.getName()).isEqualTo(firstStock.getName());
    }
}
