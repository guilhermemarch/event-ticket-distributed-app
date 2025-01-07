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
import org.bson.types.ObjectId;
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

        Event event = new Event();
        event.setLogradouro(viaCepData.getLogradouro());
        event.setBairro(viaCepData.getBairro());
        event.setCidade(viaCepData.getLocalidade());
        event.setUf(viaCepData.getUf());

        event.setEventName(request.getEventName());
        event.setDateTime(request.getDateTime());
        event.setCep(request.getCep());

        event = eventRepository.save(event);

        EventResponseDTO responseDTO = new EventResponseDTO();
        responseDTO.setId(event.getId());
        responseDTO.setEventName(event.getEventName());
        responseDTO.setDateTime(event.getDateTime());
        responseDTO.setCep(event.getCep());
        responseDTO.setLogradouro(event.getLogradouro());
        responseDTO.setBairro(event.getBairro());
        responseDTO.setCidade(event.getCidade());
        responseDTO.setUf(event.getUf());

        return responseDTO;
    }

    public EventResponseDTO updateEvent(String id, EventRequestDTO request) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return null;
        }

        Event existingEvent = optionalEvent.get();
        ViaCepResponseDTO viaCepData = fetchAddressFromCep(request.getCep());

        existingEvent.setLogradouro(viaCepData.getLogradouro());
        existingEvent.setBairro(viaCepData.getBairro());
        existingEvent.setCidade(viaCepData.getLocalidade());
        existingEvent.setUf(viaCepData.getUf());

        existingEvent.setEventName(request.getEventName());
        existingEvent.setDateTime(request.getDateTime());
        existingEvent.setCep(request.getCep());

        existingEvent = eventRepository.save(existingEvent);

        EventResponseDTO responseDTO = new EventResponseDTO();
        responseDTO.setId(existingEvent.getId());
        responseDTO.setEventName(existingEvent.getEventName());
        responseDTO.setDateTime(existingEvent.getDateTime());
        responseDTO.setCep(existingEvent.getCep());
        responseDTO.setLogradouro(existingEvent.getLogradouro());
        responseDTO.setBairro(existingEvent.getBairro());
        responseDTO.setCidade(existingEvent.getCidade());
        responseDTO.setUf(existingEvent.getUf());

        return responseDTO;
    }

    private ViaCepResponseDTO fetchAddressFromCep(String cep) {
        return viaCepClient.getAddressByCep(cep);
    }

    public List<Event> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    public Optional<Event> getEventById(String id) {
        if (ObjectId.isValid(id)) {
            return eventRepository.findById(id);
        }
        return Optional.empty();
    }


    public void deleteEvent(String eventId) {
        CheckTicketsResponseDTO response = ticketServiceClient.checkTicketsByEvent(eventId);

        if (response != null && response.isHasTickets()) {
            throw new EventDeletionException("Evento não pode ser deletado, existem ingressos vendidos.");
        }

        eventRepository.deleteById(eventId);
    }


    public List<Event> getAllEventsSorted() {
        List<Event> events = eventRepository.findAllByOrderByEventNameAsc();
        return events;
    }

}
