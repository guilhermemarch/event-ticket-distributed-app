package com.mseventmanager.ticketmanager.services;

import com.mseventmanager.ticketmanager.clients.EventManagerClient;
import com.mseventmanager.ticketmanager.dto.EventResponseDTO;
import com.mseventmanager.ticketmanager.dto.TicketRequestDTO;
import com.mseventmanager.ticketmanager.dto.TicketResponseDTO;
import com.mseventmanager.ticketmanager.entity.Ticket;
import com.mseventmanager.ticketmanager.exceptions.EventNotFoundException;
import com.mseventmanager.ticketmanager.mapper.TicketMapper;
import com.mseventmanager.ticketmanager.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.mseventmanager.ticketmanager.entity.Ticket.TicketStatus.ACTIVE;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private EventManagerClient eventManagerClient;

    @Autowired
    private TicketRepository ticketRepository;



    public TicketResponseDTO createTicket(TicketRequestDTO request) {

        EventResponseDTO eventResponse = eventManagerClient.validateEvent(request.getEventId());
        if (eventResponse == null || eventResponse.getId() == null) {
            throw new EventNotFoundException("Evento com ID " + request.getEventId() + " não encontrado.");
        }

        Ticket ticket = ticketMapper.toTicket(request);
        ticket.setEvent(eventResponse);
        ticket.setStatus(Ticket.TicketStatus.ACTIVE);

        ticket = ticketRepository.save(ticket);

        return ticketMapper.toDTO(ticket);
    }


    public TicketResponseDTO getTicketById(String id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);

        if (ticket == null) {
            return null;
        }

        return ticketMapper.toDTO(ticket);
    }

    public List<TicketResponseDTO> getTicketsByCpf(String cpf) {
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);
        return  tickets.stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketResponseDTO> getTicketsByEventId(String eventId) {
        List<Ticket> tickets = ticketRepository.findByEventId(eventId);
        return  tickets.stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
    }
}





