package com.mseventmanager.eventmanager;

import com.mseventmanager.eventmanager.clients.TicketServiceClient;
import com.mseventmanager.eventmanager.clients.ViaCepClient;
import com.mseventmanager.eventmanager.dto.CheckTicketsResponseDTO;
import com.mseventmanager.eventmanager.dto.EventRequestDTO;
import com.mseventmanager.eventmanager.dto.EventResponseDTO;
import com.mseventmanager.eventmanager.dto.ViaCepResponseDTO;
import com.mseventmanager.eventmanager.entity.Event;
import com.mseventmanager.eventmanager.exceptions.EventDeletionException;
import com.mseventmanager.eventmanager.repositories.EventRepository;
import com.mseventmanager.eventmanager.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private TicketServiceClient ticketServiceClient;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEvent() {
        EventRequestDTO request = new EventRequestDTO("Music Festival", "2025-01-15T20:00", "12345-678");
        ViaCepResponseDTO viaCepResponse = new ViaCepResponseDTO("Rua das Flores", "Centro", "São Paulo", "SP");
        Event event = new Event("Music Festival", "2025-01-15T20:00", "12345-678", "Rua das Flores", "Centro", "São Paulo", "SP");

        when(viaCepClient.getAddressByCep("12345-678")).thenReturn(viaCepResponse);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        EventResponseDTO response = eventService.createEvent(request);

        assertNotNull(response);
        assertEquals("Music Festival", response.getEventName());
        verify(viaCepClient).getAddressByCep("12345-678");
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void testUpdateEvent() {
        String id = "645a3b4f1a7f6a2e6789abcd";
        EventRequestDTO request = new EventRequestDTO("Updated Event", "2025-02-20T18:00", "98765-432");
        ViaCepResponseDTO viaCepResponse = new ViaCepResponseDTO("Av. Paulista", "Bela Vista", "São Paulo", "SP");
        Event existingEvent = new Event("Old Event", "2025-01-10T20:00", "12345-678", "Rua das Flores", "Centro", "São Paulo", "SP");

        when(eventRepository.findById(id)).thenReturn(Optional.of(existingEvent));
        when(viaCepClient.getAddressByCep("98765-432")).thenReturn(viaCepResponse);
        when(eventRepository.save(existingEvent)).thenReturn(existingEvent);

        EventResponseDTO response = eventService.updateEvent(id, request);

        assertNotNull(response);
        assertEquals("Updated Event", response.getEventName());
        verify(eventRepository).findById(id);
        verify(viaCepClient).getAddressByCep("98765-432");
        verify(eventRepository).save(existingEvent);
    }

    @Test
    void testUpdateEventNotFound() {
        String id = "invalid-id";
        EventRequestDTO request = new EventRequestDTO("Event", "2025-02-20T18:00", "98765-432");

        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        EventResponseDTO response = eventService.updateEvent(id, request);

        assertNull(response);
        verify(eventRepository).findById(id);
        verifyNoInteractions(viaCepClient);
    }

    @Test
    void testGetAllEvents() {
        Event event1 = new Event("Event 1", "2025-01-15T20:00", "12345-678", "Rua A", "Centro", "São Paulo", "SP");
        Event event2 = new Event("Event 2", "2025-01-20T20:00", "98765-432", "Rua B", "Centro", "Rio de Janeiro", "RJ");

        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        List<Event> events = eventService.getAllEvents();

        assertNotNull(events);
        assertEquals(2, events.size());
        verify(eventRepository).findAll();
    }

    @Test
    void testGetEventByIdValid() {
        String id = "645a3b4f1a7f6a2e6789abcd";
        Event event = new Event("Event", "2025-01-15T20:00", "12345-678", "Rua A", "Centro", "São Paulo", "SP");

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.getEventById(id);

        assertTrue(result.isPresent());
        assertEquals("Event", result.get().getEventName());
        verify(eventRepository).findById(id);
    }

    @Test
    void testGetEventByIdInvalid() {
        String id = "invalid-id";

        Optional<Event> result = eventService.getEventById(id);

        assertTrue(result.isEmpty());
        verify(eventRepository, never()).findById(id);
    }

    @Test
    void testDeleteEventWithTickets() {
        String eventId = "645a3b4f1a7f6a2e6789abcd";
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO(eventId, true);

        when(ticketServiceClient.checkTicketsByEvent(eventId)).thenReturn(response);

        assertThrows(EventDeletionException.class, () -> eventService.deleteEvent(eventId));

        verify(ticketServiceClient).checkTicketsByEvent(eventId);
        verify(eventRepository, never()).deleteById(eventId);
    }

    @Test
    void testDeleteEventWithoutTickets() {
        String eventId = "645a3b4f1a7f6a2e6789abcd";
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO(eventId, false);

        when(ticketServiceClient.checkTicketsByEvent(eventId)).thenReturn(response);

        eventService.deleteEvent(eventId);

        verify(ticketServiceClient).checkTicketsByEvent(eventId);
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    void testGetAllEventsSorted() {
        Event event1 = new Event("A Event", "2025-01-15T20:00", "12345-678", "Rua A", "Centro", "São Paulo", "SP");
        Event event2 = new Event("B Event", "2025-01-20T20:00", "98765-432", "Rua B", "Centro", "Rio de Janeiro", "RJ");

        when(eventRepository.findAllByOrderByEventNameAsc()).thenReturn(Arrays.asList(event1, event2));

        List<Event> events = eventService.getAllEventsSorted();

        assertNotNull(events);
        assertEquals(2, events.size());
        assertEquals("A Event", events.get(0).getEventName());
        verify(eventRepository).findAllByOrderByEventNameAsc();
    }
}
