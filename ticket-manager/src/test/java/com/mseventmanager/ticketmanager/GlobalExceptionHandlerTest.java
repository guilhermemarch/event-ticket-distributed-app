package com.mseventmanager.ticketmanager;

import com.mseventmanager.ticketmanager.exceptions.EventNotFoundException;
import com.mseventmanager.ticketmanager.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;



class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleEventNotFoundException_ShouldReturnNotFoundResponse() {
        String errorMessage = "Event not found";
        EventNotFoundException exception = new EventNotFoundException(errorMessage);

        ResponseEntity<Object> response = handler.handleEventNotFoundException(exception);

        assertEquals(404, response.getStatusCodeValue());
        GlobalExceptionHandler.ErrorResponse errorResponse = (GlobalExceptionHandler.ErrorResponse) response.getBody();
        assertEquals(404, errorResponse.getStatus());
        assertEquals(errorMessage, errorResponse.getMessage());
    }

    @Test
    void handleTicketNotFoundException_ShouldReturnNotFoundResponse() {
        String errorMessage = "Ticket not found";
        GlobalExceptionHandler.TicketNotFoundException exception = handler.new TicketNotFoundException(errorMessage);

        ResponseEntity<Object> response = handler.handleTicketNotFoundException(exception);

        assertEquals(404, response.getStatusCodeValue());
        GlobalExceptionHandler.ErrorResponse errorResponse = (GlobalExceptionHandler.ErrorResponse) response.getBody();
        assertEquals(404, errorResponse.getStatus());
        assertEquals(errorMessage, errorResponse.getMessage());
    }
}