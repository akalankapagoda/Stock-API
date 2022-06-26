package com.payconiq.stock.stocksapi.exception;

import com.payconiq.stock.stocksapi.model.ResponseMessage;
import com.payconiq.stock.stocksapi.model.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception handler for handling controller level errors and returning error responses.
 */
@ControllerAdvice
public class StockEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = Logger.getLogger(StockEntityExceptionHandler.class.getName());

    /**
     * Handle an error with a response status and a reason.
     *
     * @param e The thrown exception
     * @param request The web request
     *
     * @return A response message constructed with provided reason details and status
     */
    @ExceptionHandler(value = { ResponseStatusException.class })
    protected ResponseEntity<Object> handleResponseStatus(ResponseStatusException e, WebRequest request) {

        logger.log(Level.SEVERE, "Error handling request.", e);

        ResponseMessage responseMessage = new ResponseMessage(e.getReason(), ResponseStatus.ERROR);

        return handleExceptionInternal(e, responseMessage, new HttpHeaders(), e.getStatus(), request);

    }

    /**
     * Handle SQLExceptions.
     *
     * This considers any SQLException that occur in this application is caused by invalid user input.
     *
     * @param e The exception occurred
     * @param request The web request received
     *
     * @return An error message with SQL error details extracted
     */
    @ExceptionHandler(value = { SQLException.class })
    protected ResponseEntity<Object> handleSQLIntegrityViolation(SQLException e, WebRequest request) {

        logger.log(Level.SEVERE, "SQL Error while handling request.", e);

        ResponseMessage responseMessage = new ResponseMessage(e.getMessage(), ResponseStatus.ERROR);

        return handleExceptionInternal(e, responseMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

}
