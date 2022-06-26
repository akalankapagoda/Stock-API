package com.payconiq.stock.stocksapi;

import com.payconiq.stock.stocksapi.controller.StocksController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Make sure the application properly starts up.
 */
@SpringBootTest
class StocksApiApplicationTests {

	@Autowired
	StocksController stocksController;

	@Test
	void contextLoads() {
		assertThat(stocksController).isNotNull();
	}

}
