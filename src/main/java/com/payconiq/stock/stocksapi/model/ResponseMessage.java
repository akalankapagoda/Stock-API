package com.payconiq.stock.stocksapi.model;

/**
 * A generic response message to relay status information back to the client.
 */
public class ResponseMessage {

    private String message;

    private ResponseStatus status;

    public ResponseMessage(String message, ResponseStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
