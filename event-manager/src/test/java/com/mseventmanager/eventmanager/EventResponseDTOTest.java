package com.mseventmanager.eventmanager;

import com.mseventmanager.eventmanager.dto.EventResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventResponseDTOTest {

    @Test
    void testNoArgsConstructor() {
        EventResponseDTO eventResponseDTO = new EventResponseDTO();

        assertNull(eventResponseDTO.getId());
        assertNull(eventResponseDTO.getEventName());
        assertNull(eventResponseDTO.getDateTime());
        assertNull(eventResponseDTO.getCep());
        assertNull(eventResponseDTO.getLogradouro());
        assertNull(eventResponseDTO.getBairro());
        assertNull(eventResponseDTO.getCidade());
        assertNull(eventResponseDTO.getUf());
    }

    @Test
    void testAllArgsConstructor() {
        EventResponseDTO eventResponseDTO = new EventResponseDTO(
                "1",
                "Music Festival",
                "2025-01-15T20:00",
                "12345-678",
                "Rua das Flores",
                "Centro",
                "São Paulo",
                "SP"
        );

        assertEquals("1", eventResponseDTO.getId());
        assertEquals("Music Festival", eventResponseDTO.getEventName());
        assertEquals("2025-01-15T20:00", eventResponseDTO.getDateTime());
        assertEquals("12345-678", eventResponseDTO.getCep());
        assertEquals("Rua das Flores", eventResponseDTO.getLogradouro());
        assertEquals("Centro", eventResponseDTO.getBairro());
        assertEquals("São Paulo", eventResponseDTO.getCidade());
        assertEquals("SP", eventResponseDTO.getUf());
    }

    @Test
    void testSettersAndGetters() {
        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        eventResponseDTO.setId("2");
        eventResponseDTO.setEventName("Art Show");
        eventResponseDTO.setDateTime("2025-03-01T18:00");
        eventResponseDTO.setCep("98765-432");
        eventResponseDTO.setLogradouro("Av. Paulista");
        eventResponseDTO.setBairro("Bela Vista");
        eventResponseDTO.setCidade("São Paulo");
        eventResponseDTO.setUf("SP");

        assertEquals("2", eventResponseDTO.getId());
        assertEquals("Art Show", eventResponseDTO.getEventName());
        assertEquals("2025-03-01T18:00", eventResponseDTO.getDateTime());
        assertEquals("98765-432", eventResponseDTO.getCep());
        assertEquals("Av. Paulista", eventResponseDTO.getLogradouro());
        assertEquals("Bela Vista", eventResponseDTO.getBairro());
        assertEquals("São Paulo", eventResponseDTO.getCidade());
        assertEquals("SP", eventResponseDTO.getUf());
    }

    @Test
    void testToString() {
        EventResponseDTO eventResponseDTO = new EventResponseDTO(
                "1",
                "Music Festival",
                "2025-01-15T20:00",
                "12345-678",
                "Rua das Flores",
                "Centro",
                "São Paulo",
                "SP"
        );
        String expected = "EventResponseDTO{id='1', eventName='Music Festival', dateTime='2025-01-15T20:00', cep='12345-678', logradouro='Rua das Flores', bairro='Centro', cidade='São Paulo', uf='SP'}";

        assertEquals(expected, eventResponseDTO.toString());
    }
}