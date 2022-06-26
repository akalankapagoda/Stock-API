package com.payconiq.stock.stocksapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Verify that HTTP server is properly functioning.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void helloShouldReturnDefaultMessage() throws Exception {

        ResponseEntity<String> responseEntity  = this.restTemplate.getForEntity("http://localhost:" + port + "/api/stocks/hello",
                String.class);

        String body = mapper.convertValue(responseEntity.getBody(), String.class);

        assertThat(body).isEqualTo("Hello");
    }


}
