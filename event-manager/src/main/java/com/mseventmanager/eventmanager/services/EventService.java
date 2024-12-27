package com.mseventmanager.eventmanager.services;

import com.mseventmanager.eventmanager.clients.TicketServiceClient;
import com.mseventmanager.eventmanager.clients.ViaCepClient;
import com.mseventmanager.eventmanager.dto.CheckTicketsResponseDTO;
import com.mseventmanager.eventmanager.dto.EventRequestDTO;
import com.mseventmanager.eventmanager.dto.EventResponseDTO;
import com.mseventmanager.eventmanager.dto.ViaCepResponseDTO;
import com.mseventmanager.eventmanager.exceptions.EventDeletionException;
import com.mseventmanager.eventmanager.mapper.EventMapper;
import com.mseventmanager.eventmanager.entity.Event;
import com.mseventmanager.eventmanager.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private ViaCepClient viaCepClient;

    @Autowired
    private TicketServiceClient ticketServiceClient;

    public EventResponseDTO createEvent(EventRequestDTO request) {

        ViaCepResponseDTO viaCepData = fetchAddressFromCep(request.getCep());

        Event event = eventMapper.toEvent(request, viaCepData);

        event = eventRepository.save(event);

        return eventMapper.toDTO(event);
    }

    private ViaCepResponseDTO fetchAddressFromCep(String cep) {
        return viaCepClient.getAddressByCep(cep);
    }

    public List<Event> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    public Optional<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }


    public void deleteEvent(String eventId) {
        CheckTicketsResponseDTO response = ticketServiceClient.checkTicketsByEvent(eventId);

        if (response != null && response.isHasTickets()) {
            throw new EventDeletionException("Evento não pode ser deletado, existem ingressos vendidos.");
        }

        eventRepository.deleteById(eventId);
    }

    public EventResponseDTO updateEvent(String id, EventRequestDTO request) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty()) {
            return null;
        }

        ViaCepResponseDTO viaCepData = fetchAddressFromCep(request.getCep());

        Event updatedEvent = eventMapper.toEvent(request, viaCepData);
        updatedEvent.setId(id);

        updatedEvent = eventRepository.save(updatedEvent);

        return eventMapper.toDTO(updatedEvent);
    }

    public List<Event> getAllEventsSorted() {
        List<Event> events = eventRepository.findAllByOrderByDateTimeAsc();
        return events;
    }

}
