package com.mseventmanager.eventmanager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mseventmanager.eventmanager.clients.TicketServiceClient;
import com.mseventmanager.eventmanager.clients.ViaCepClient;
import com.mseventmanager.eventmanager.dto.CheckTicketsResponseDTO;
import com.mseventmanager.eventmanager.dto.EventRequestDTO;
import com.mseventmanager.eventmanager.dto.EventResponseDTO;
import com.mseventmanager.eventmanager.dto.ViaCepResponseDTO;
import com.mseventmanager.eventmanager.entity.Event;
import com.mseventmanager.eventmanager.exceptions.EventDeletionException;
import com.mseventmanager.eventmanager.mapper.EventMapper;
import com.mseventmanager.eventmanager.repositories.EventRepository;
import com.mseventmanager.eventmanager.services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private TicketServiceClient ticketServiceClient;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_ShouldReturnCreatedEvent() {
        EventRequestDTO request = new EventRequestDTO();
        ViaCepResponseDTO viaCepResponse = new ViaCepResponseDTO();
        Event event = new Event();
        EventResponseDTO response = new EventResponseDTO();

        when(viaCepClient.getAddressByCep(request.getCep())).thenReturn(viaCepResponse);
        when(eventMapper.toEvent(request, viaCepResponse)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.toDTO(event)).thenReturn(response);

        EventResponseDTO result = eventService.createEvent(request);

        assertEquals(response, result);
        verify(viaCepClient, times(1)).getAddressByCep(request.getCep());
        verify(eventMapper, times(1)).toEvent(request, viaCepResponse);
        verify(eventRepository, times(1)).save(event);
        verify(eventMapper, times(1)).toDTO(event);
    }

    @Test
    void getAllEvents_ShouldReturnAllEvents() {
        List<Event> events = List.of(new Event());
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(events, result);
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void getEventById_ShouldReturnEvent() {
        String id = "evento5252";
        Event event = new Event();
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.getEventById(id);

        assertTrue(result.isPresent());
        assertEquals(event, result.get());
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    void getEventById_ShouldReturnEmptyIfEventNotFound() {
        String id = "evento5252";
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Event> result = eventService.getEventById(id);

        assertTrue(result.isEmpty());
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    void deleteEvent_ShouldDeleteEventIfNoTicketsExist() {
        String eventId = "evento5252";
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO();
        response.setHasTickets(false);
        when(ticketServiceClient.checkTicketsByEvent(eventId)).thenReturn(response);

        eventService.deleteEvent(eventId);

        verify(ticketServiceClient, times(1)).checkTicketsByEvent(eventId);
        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    void deleteEvent_ShouldThrowExceptionIfTicketsExist() {
        String eventId = "evento5252";
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO();
        response.setHasTickets(true);
        when(ticketServiceClient.checkTicketsByEvent(eventId)).thenReturn(response);

        assertThrows(EventDeletionException.class, () -> eventService.deleteEvent(eventId));

        verify(ticketServiceClient, times(1)).checkTicketsByEvent(eventId);
        verifyNoInteractions(eventRepository);
    }

    @Test
    void updateEvent_ShouldReturnUpdatedEvent() {
        String id = "evento5252";
        EventRequestDTO request = new EventRequestDTO();
        ViaCepResponseDTO viaCepResponse = new ViaCepResponseDTO();
        Event existingEvent = new Event();
        Event updatedEvent = new Event();
        EventResponseDTO response = new EventResponseDTO();

        when(eventRepository.findById(id)).thenReturn(Optional.of(existingEvent));
        when(viaCepClient.getAddressByCep(request.getCep())).thenReturn(viaCepResponse);
        when(eventMapper.toEvent(request, viaCepResponse)).thenReturn(updatedEvent);
        when(eventRepository.save(updatedEvent)).thenReturn(updatedEvent);
        when(eventMapper.toDTO(updatedEvent)).thenReturn(response);

        EventResponseDTO result = eventService.updateEvent(id, request);

        assertEquals(response, result);
        verify(eventRepository, times(1)).findById(id);
        verify(viaCepClient, times(1)).getAddressByCep(request.getCep());
        verify(eventMapper, times(1)).toEvent(request, viaCepResponse);
        verify(eventRepository, times(1)).save(updatedEvent);
        verify(eventMapper, times(1)).toDTO(updatedEvent);
    }

    @Test
    void updateEvent_ShouldReturnNullIfEventNotFound() {
        String id = "evento5252";
        EventRequestDTO request = new EventRequestDTO();
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        EventResponseDTO result = eventService.updateEvent(id, request);

        assertNull(result);
        verify(eventRepository, times(1)).findById(id);
        verifyNoInteractions(viaCepClient, eventMapper);
    }

    @Test
    void getAllEventsSorted_ShouldReturnSortedEvents() {
        List<Event> events = List.of(new Event());
        when(eventRepository.findAllByOrderByEventNameAsc()).thenReturn(events);

        List<Event> result = eventService.getAllEventsSorted();

        assertEquals(events, result);
        verify(eventRepository, times(1)).findAllByOrderByEventNameAsc();
    }
}