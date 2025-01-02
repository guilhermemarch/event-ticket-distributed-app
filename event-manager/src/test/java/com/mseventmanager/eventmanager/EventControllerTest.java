package com.mseventmanager.eventmanager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import com.mseventmanager.eventmanager.controllers.EventController;
import com.mseventmanager.eventmanager.dto.EventRequestDTO;
import com.mseventmanager.eventmanager.dto.EventResponseDTO;
import com.mseventmanager.eventmanager.entity.Event;
import com.mseventmanager.eventmanager.mapper.EventMapper;
import com.mseventmanager.eventmanager.services.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class EventControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventController eventController;

    @Test
    void createEvent_ShouldReturnCreatedEvent() {
        EventRequestDTO request = new EventRequestDTO();
        EventResponseDTO response = new EventResponseDTO();
        when(eventService.createEvent(request)).thenReturn(response);

        ResponseEntity<EventResponseDTO> result = eventController.createEvent(request);

        assertEquals(OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(eventService, times(1)).createEvent(request);
    }

    @Test
    void getAllEvents_ShouldReturnListOfEvents() {
        List<Event> events = List.of(new Event());
        when(eventService.getAllEvents()).thenReturn(events);

        ResponseEntity<List<Event>> result = eventController.getAllEvents();

        assertEquals(OK, result.getStatusCode());
        assertEquals(events, result.getBody());
        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    void getEventById_ShouldReturnEventById() {
        String id = "evento123";
        Event event = new Event();
        EventResponseDTO response = new EventResponseDTO();
        when(eventService.getEventById(id)).thenReturn(Optional.of(event));
        when(eventMapper.toDTO(event)).thenReturn(response);

        ResponseEntity<EventResponseDTO> result = eventController.getEventById(id);

        assertEquals(OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(eventService, times(1)).getEventById(id);
        verify(eventMapper, times(1)).toDTO(event);
    }

    @Test
    void getEventById_ShouldReturnNotFoundIfEventDoesNotExist() {
        String id = "naoexiste";
        when(eventService.getEventById(id)).thenReturn(Optional.empty());

        ResponseEntity<EventResponseDTO> result = eventController.getEventById(id);

        assertEquals(NOT_FOUND, result.getStatusCode());
        verify(eventService, times(1)).getEventById(id);
        verifyNoInteractions(eventMapper);
    }

    @Test
    void deleteEvent_ShouldReturnNoContent() {
        String id = "evento123";

        ResponseEntity<Void> result = eventController.deleteEvent(id);

        assertEquals(NO_CONTENT, result.getStatusCode());
        verify(eventService, times(1)).deleteEvent(id);
    }

    @Test
    void updateEvent_ShouldReturnUpdatedEvent() {
        String id = "evento123";
        EventRequestDTO request = new EventRequestDTO();
        EventResponseDTO response = new EventResponseDTO();
        when(eventService.updateEvent(id, request)).thenReturn(response);

        ResponseEntity<EventResponseDTO> result = eventController.updateEvent(id, request);

        assertEquals(OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(eventService, times(1)).updateEvent(id, request);
    }

    @Test
    void getAllEventsSorted_ShouldReturnSortedListOfEvents() {
        List<Event> events = List.of(new Event());
        when(eventService.getAllEventsSorted()).thenReturn(events);

        ResponseEntity<List<Event>> result = eventController.getAllEventsSorted();

        assertEquals(OK, result.getStatusCode());
        assertEquals(events, result.getBody());
        verify(eventService, times(1)).getAllEventsSorted();
    }
}
