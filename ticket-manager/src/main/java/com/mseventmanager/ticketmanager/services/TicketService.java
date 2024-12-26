package com.mseventmanager.ticketmanager.services;

import com.mseventmanager.ticketmanager.clients.EventManagerClient;
import com.mseventmanager.ticketmanager.dto.EventResponseDTO;
import com.mseventmanager.ticketmanager.dto.TicketRequestDTO;
import com.mseventmanager.ticketmanager.dto.TicketResponseDTO;
import com.mseventmanager.ticketmanager.entity.Ticket;
import com.mseventmanager.ticketmanager.mapper.TicketMapper;
import com.mseventmanager.ticketmanager.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Ticket ticket = ticketMapper.toTicket(request);

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
}





