package com.mseventmanager.eventmanager;

import com.mseventmanager.eventmanager.entity.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testEventNoArgsConstructor() {
        Event event = new Event();

        assertNull(event.getId());
        assertNull(event.getEventName());
        assertNull(event.getDateTime());
        assertNull(event.getCep());
        assertNull(event.getLogradouro());
        assertNull(event.getBairro());
        assertNull(event.getCidade());
        assertNull(event.getUf());
    }

    @Test
    void testEventAllArgsConstructor() {
        Event event = new Event("1", "Music Festival", "2025-01-15T20:00", "12345-678", "Rua das Flores", "Centro", "São Paulo", "SP");

        assertEquals("1", event.getId());
        assertEquals("Music Festival", event.getEventName());
        assertEquals("2025-01-15T20:00", event.getDateTime());
        assertEquals("12345-678", event.getCep());
        assertEquals("Rua das Flores", event.getLogradouro());
        assertEquals("Centro", event.getBairro());
        assertEquals("São Paulo", event.getCidade());
        assertEquals("SP", event.getUf());
    }

    @Test
    void testEventPartialArgsConstructor() {
        Event event = new Event("Music Festival", "2025-01-15T20:00", "12345-678", "Rua das Flores", "Centro", "São Paulo", "SP");

        assertNull(event.getId());
        assertEquals("Music Festival", event.getEventName());
        assertEquals("2025-01-15T20:00", event.getDateTime());
        assertEquals("12345-678", event.getCep());
        assertEquals("Rua das Flores", event.getLogradouro());
        assertEquals("Centro", event.getBairro());
        assertEquals("São Paulo", event.getCidade());
        assertEquals("SP", event.getUf());
    }

    @Test
    void testSettersAndGetters() {
        Event event = new Event();
        event.setId("2");
        event.setEventName("Art Show");
        event.setDateTime("2025-03-01T18:00");
        event.setCep("98765-432");
        event.setLogradouro("Av. Paulista");
        event.setBairro("Bela Vista");
        event.setCidade("São Paulo");
        event.setUf("SP");

        assertEquals("2", event.getId());
        assertEquals("Art Show", event.getEventName());
        assertEquals("2025-03-01T18:00", event.getDateTime());
        assertEquals("98765-432", event.getCep());
        assertEquals("Av. Paulista", event.getLogradouro());
        assertEquals("Bela Vista", event.getBairro());
        assertEquals("São Paulo", event.getCidade());
        assertEquals("SP", event.getUf());
    }

    @Test
    void testToString() {
        Event event = new Event("1", "Music Festival", "2025-01-15T20:00", "12345-678", "Rua das Flores", "Centro", "São Paulo", "SP");
        String expected = "Event{id='1', eventName='Music Festival', dateTime='2025-01-15T20:00', cep='12345-678', logradouro='Rua das Flores', bairro='Centro', cidade='São Paulo', uf='SP'}";

        assertEquals(expected, event.toString());
    }
}