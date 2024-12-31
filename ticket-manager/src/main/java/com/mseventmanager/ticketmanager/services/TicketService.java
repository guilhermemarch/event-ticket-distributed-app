package com.mseventmanager.ticketmanager.services;

import com.mseventmanager.ticketmanager.dto.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.mseventmanager.ticketmanager.clients.EventManagerClient;
import com.mseventmanager.ticketmanager.config.RabbitMQConfig;
import com.mseventmanager.ticketmanager.entity.Ticket;
import com.mseventmanager.ticketmanager.exceptions.EventNotFoundException;
import com.mseventmanager.ticketmanager.exceptions.TicketNotFoundException;
import com.mseventmanager.ticketmanager.mapper.TicketMapper;
import com.mseventmanager.ticketmanager.repositories.TicketRepository;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private EventManagerClient eventManagerClient;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public TicketResponseDTO createTicket(TicketRequestDTO request) {
        EventResponseDTO eventResponse = eventManagerClient.validateEvent(request.getEventId());
        if (eventResponse == null || eventResponse.getId() == null) {
            throw new EventNotFoundException("Evento com ID " + request.getEventId() + " não encontrado.");
        }

        Ticket ticket = ticketMapper.toTicket(request);
        ticket.setEvent(eventResponse);
        ticket.setStatus(Ticket.TicketStatus.ACTIVE);
        ticket = ticketRepository.save(ticket);

        String customerEmail = request.getCustomerMail();
        String customerName = request.getCustomerName();
        String eventDate = eventResponse.getDateTime();
        String eventCity = eventResponse.getCidade();
        String subject = "Seu ticket foi criado com sucesso!";
        String body = String.format("Olá, %s! Seu ticket para o evento '%s' na cidade de %s, na data %s, foi criado com sucesso.\n\n"
                        + "Detalhes do Ticket:\n"
                        + "Evento: %s\n"
                        + "Cidade: %s\n"
                        + "Data: %s\n"
                        + "ID do Ticket: %s\n"
                        + "Status: %s\n\n"
                        + "Agradecemos por escolher nossa plataforma!",
                customerName, eventResponse.getEventName(), eventCity, eventDate,
                eventResponse.getEventName(), eventCity, eventDate, ticket.getId(), ticket.getStatus());

        EmailMessage emailMessage = new EmailMessage(customerEmail, subject, body);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, emailMessage);

        logger.info("Ticket criado e e-mail enviado para: {}", customerEmail);
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

    public CheckTicketsResponseDTO checkTicketsByEvent(String eventId) {
        ObjectId eventObjectId = new ObjectId(eventId);

        boolean hasTickets = ticketRepository.existsByEvent_IdAndStatus(eventObjectId, Ticket.TicketStatus.ACTIVE);

        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO();
        response.setEventId(eventId);
        response.setHasTickets(hasTickets);

        return response;
    }

    public TicketResponseDTO updateTicket(String id, TicketRequestDTO request) {
        return null;
    }

    public void cancelTicket(String id) {
       Ticket ticket = ticketRepository.findById(id).orElse(null);
         if (ticket == null) {
                throw new TicketNotFoundException("Ticket com ID " + id + " não encontrado.");
          }
        ticket.setStatus(Ticket.TicketStatus.CANCELLED);
        ticketRepository.save(ticket);
    }
}





