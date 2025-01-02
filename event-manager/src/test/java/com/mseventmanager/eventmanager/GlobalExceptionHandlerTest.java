package com.mseventmanager.eventmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mseventmanager.eventmanager.exceptions.EventDeletionException;
import com.mseventmanager.eventmanager.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleEventDeletionException_ShouldReturnConflictResponse() {
        String errorMessage = "Evento não pode ser deletado, existem ingressos vendidos.";
        EventDeletionException exception = new EventDeletionException(errorMessage);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = exceptionHandler.handleEventDeletionException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
    }
}
