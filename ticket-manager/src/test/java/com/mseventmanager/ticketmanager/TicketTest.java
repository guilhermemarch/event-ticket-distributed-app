package com.mseventmanager.ticketmanager;

import com.mseventmanager.ticketmanager.clients.EventManagerClient;
import com.mseventmanager.ticketmanager.controllers.TicketControler;
import com.mseventmanager.ticketmanager.dto.*;
import com.mseventmanager.ticketmanager.entity.Ticket;
import com.mseventmanager.ticketmanager.exceptions.EventNotFoundException;
import com.mseventmanager.ticketmanager.exceptions.TicketNotFoundException;
import com.mseventmanager.ticketmanager.mapper.TicketMapper;
import com.mseventmanager.ticketmanager.repositories.TicketRepository;
import com.mseventmanager.ticketmanager.services.TicketService;
import feign.FeignException;
import feign.Request;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import com.mseventmanager.ticketmanager.dto.CheckTicketsResponseDTO;
import com.mseventmanager.ticketmanager.dto.TicketRequestDTO;
import com.mseventmanager.ticketmanager.dto.TicketResponseDTO;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringJUnitConfig
class TicketTest {

    @InjectMocks
    private TicketService ticketService;

    @InjectMocks
    private TicketControler ticketControler;

    @Mock
    private TicketMapper ticketMapper;


    @Mock
    private TicketService ticketServiceTest;

    @Mock
    private EventManagerClient eventManagerClient;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private TicketRequestDTO ticketRequestDTO;
    private Ticket ticket;
    private EventResponseDTO eventResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ticketRequestDTO = new TicketRequestDTO("pedro da silva", "12345678901", "pedro@gmail.com", "jogo de futebol", "eventId");
        eventResponseDTO = new EventResponseDTO("eventId", "jogo de futebol", "2023-10-10T20:00:00", "Street 1", "rua joao", "City", "State");
        ticket = new Ticket();
        ticket.setId("ticketId");
        ticket.setCustomerName("pedro da silva");
        ticket.setCpf("12345678901");
        ticket.setCustomerMail("pedro@gmail.com");
        ticket.setEvent(eventResponseDTO);
        ticket.setStatus(Ticket.TicketStatus.ACTIVE);
    }

    @Test
    void createTicket_ShouldCreateTicket_WhenEventExists() {
        when(eventManagerClient.validateEvent("eventId")).thenReturn(eventResponseDTO);
        when(ticketMapper.toTicket(ticketRequestDTO)).thenReturn(ticket);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(ticketMapper.toDTO(ticket)).thenReturn(new TicketResponseDTO("ticketId", "pedro da silva", "12345678901", "john@example.com", eventResponseDTO, Ticket.TicketStatus.ACTIVE));

        TicketResponseDTO response = ticketService.createTicket(ticketRequestDTO);

        assertNotNull(response);
        assertEquals("ticketId", response.getId());
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), any(EmailMessage.class));
    }

    @Test
    void createTicket_ShouldThrowEventNotFoundException_WhenEventDoesNotExist() {
        when(eventManagerClient.validateEvent("eventId")).thenReturn(null);

        Exception exception = assertThrows(EventNotFoundException.class, () -> {
            ticketService.createTicket(ticketRequestDTO);
        });

        assertEquals("Evento com ID eventId não encontrado.", exception.getMessage());
    }

    @Test
    void getTicketById_ShouldReturnTicket_WhenTicketExists() {
        when(ticketRepository.findById("ticketId")).thenReturn(Optional.of(ticket));
        when(ticketMapper.toDTO(ticket)).thenReturn(new TicketResponseDTO("ticketId", "pedro da silva", "12345678901", "john@example.com", eventResponseDTO, Ticket.TicketStatus.ACTIVE));

        TicketResponseDTO response = ticketService.getTicketById("ticketId");

        assertNotNull(response);
        assertEquals("ticketId", response.getId());
    }

    @Test
    void getTicketById_ShouldThrowTicketNotFoundException_WhenTicketDoesNotExist() {
        when(ticketRepository.findById("ticketId")).thenReturn(Optional.empty());

        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getTicketById("ticketId");
        });

        assertEquals("Ticket com ID ticketId não encontrado.", exception.getMessage());
    }


    @Test
    void cancelTicket_ShouldCancelTicket_WhenTicketExists() {
        when(ticketRepository.findById("ticketId")).thenReturn(Optional.of(ticket));

        ticketService.cancelTicket("ticketId");

        assertEquals(Ticket.TicketStatus.CANCELLED, ticket.getStatus());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void cancelTicket_ShouldThrowTicketNotFoundException_WhenTicketDoesNotExist() {
        when(ticketRepository.findById("ticketId")).thenReturn(Optional.empty());

        Exception exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.cancelTicket("ticketId");
        });

        assertEquals("Ticket com ID ticketId não encontrado.", exception.getMessage());
    }

    @Test
    void getTicketsByCpf_ShouldReturnTickets_WhenTicketsExist() {
        when(ticketRepository.findByCpf("12345678901")).thenReturn(Collections.singletonList(ticket));
        when(ticketMapper.toDTO(ticket)).thenReturn(new TicketResponseDTO("ticketId", "pedro da silva", "12345678901", "john@example.com", eventResponseDTO, Ticket.TicketStatus.ACTIVE));

        List<TicketResponseDTO> response = ticketService.getTicketsByCpf("12345678901");

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("ticketId", response.get(0).getId());
    }

    @Test
    void getTicketsByEventId_ShouldReturnTickets_WhenTicketsExist() {
        when(ticketRepository.findByEventId("eventId")).thenReturn(Collections.singletonList(ticket));
        when(ticketMapper.toDTO(ticket)).thenReturn(new TicketResponseDTO("ticketId", "pedro da silva", "12345678901", "john@example.com", eventResponseDTO, Ticket.TicketStatus.ACTIVE));

        List<TicketResponseDTO> response = ticketService.getTicketsByEventId("eventId");

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("ticketId", response.get(0).getId());
    }

    @Test
    void checkTicketsByEvent_ShouldReturnTrue_WhenTicketsExist() {
        ObjectId objectId = new ObjectId("507f1f77bcf86cd799439011");
        when(ticketRepository.existsByEvent_IdAndStatus(objectId, Ticket.TicketStatus.ACTIVE)).thenReturn(true);

        CheckTicketsResponseDTO response = ticketService.checkTicketsByEvent("507f1f77bcf86cd799439011");

        assertNotNull(response);
        assertTrue(response.isHasTickets());
        assertEquals("507f1f77bcf86cd799439011", response.getEventId());
    }

    @Test
    void checkTicketsByEvent_ShouldReturnFalse_WhenNoTicketsExist() {
        ObjectId objectId = new ObjectId("507f1f77bcf86cd799439011");
        when(ticketRepository.existsByEvent_IdAndStatus(objectId, Ticket.TicketStatus.ACTIVE)).thenReturn(false);

        CheckTicketsResponseDTO response = ticketService.checkTicketsByEvent("507f1f77bcf86cd799439011");

        assertNotNull(response);
        assertFalse(response.isHasTickets());
        assertEquals("507f1f77bcf86cd799439011", response.getEventId());
    }


    @Test
    void updateTicket_ShouldThrowTicketNotFoundException_WhenTicketDoesNotExist() {
        when(ticketRepository.findById("ticketId")).thenReturn(Optional.empty());

        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.updateTicket("ticketId", ticketRequestDTO);
        });

        assertEquals("Ticket com ID ticketId não encontrado.", exception.getMessage());
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void createTicket_ShouldHandleFeignException_WhenValidateEventThrowsNotFound() {
        when(eventManagerClient.validateEvent("eventId")).thenThrow(
                new FeignException.NotFound(
                        "Event not found",
                        Request.create(Request.HttpMethod.GET, "/eventId", Collections.emptyMap(), null, StandardCharsets.UTF_8, null),
                        null,
                        Collections.emptyMap()
                )
        );

        Exception exception = assertThrows(EventNotFoundException.class, () -> {
            ticketService.createTicket(ticketRequestDTO);
        });

        assertEquals("Evento com ID eventId não encontrado.", exception.getMessage());
    }

    @Test
    void getTicketsByCpf_ShouldReturnEmptyList_WhenNoTicketsExist() {
        when(ticketRepository.findByCpf("12345678901")).thenReturn(Collections.emptyList());

        List<TicketResponseDTO> response = ticketService.getTicketsByCpf("12345678901");

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void getTicketsByEventId_ShouldReturnEmptyList_WhenNoTicketsExist() {
        when(ticketRepository.findByEventId("eventId")).thenReturn(Collections.emptyList());

        List<TicketResponseDTO> response = ticketService.getTicketsByEventId("eventId");

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void checkTicketsByEvent_ShouldHandleInvalidEventId() {
        String invalidEventId = "invalidEventId";
        assertThrows(IllegalArgumentException.class, () -> {
            ticketService.checkTicketsByEvent(invalidEventId);
        });
    }

    @Test
    void updateTicket_ShouldUpdateTicket_WhenValidDataProvided() {
        when(ticketRepository.findById("ticketId")).thenReturn(Optional.of(ticket));
        when(eventManagerClient.validateEvent("eventId")).thenReturn(eventResponseDTO);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketMapper.toDTO(ticket)).thenReturn(new TicketResponseDTO("ticketId", "guilherme atualizado", "98765432100", "guilherme.updated@example.com", eventResponseDTO, Ticket.TicketStatus.ACTIVE));

        ticketRequestDTO.setCustomerName("guilherme atualizado");
        ticketRequestDTO.setCpf("98765432100");
        ticketRequestDTO.setCustomerMail("guilherme.updated@example.com");

        TicketResponseDTO response = ticketService.updateTicket("ticketId", ticketRequestDTO);

        assertNotNull(response);
        assertEquals("guilherme atualizado", response.getCustomerName());
        assertEquals("98765432100", response.getCpf());
        assertEquals("guilherme.updated@example.com", response.getCustomerMail());
        verify(ticketRepository, times(1)).save(ticket);
    }
    @Test
    void createTicket_ShouldReturnCreatedTicket() {
        TicketRequestDTO request = new TicketRequestDTO();
        TicketResponseDTO response = new TicketResponseDTO();
        when(ticketServiceTest.createTicket(request)).thenReturn(response);

        ResponseEntity<TicketResponseDTO> result = ticketControler.createTicket(request);

        assertEquals(OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(ticketServiceTest, times(1)).createTicket(request);
    }

    @Test
    void getTicketById_ShouldReturnTicketById() {
        String id = "ticket123";
        TicketResponseDTO response = new TicketResponseDTO();
        when(ticketServiceTest.getTicketById(id)).thenReturn(response);

        ResponseEntity<TicketResponseDTO> result = ticketControler.getTicketById(id);

        assertEquals(OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(ticketServiceTest, times(1)).getTicketById(id);
    }

    @Test
    void getTicketsByCpf_ShouldReturnTicketsByCpf() {
        String cpf = "12345678900";
        List<TicketResponseDTO> response = List.of(new TicketResponseDTO());
        when(ticketServiceTest.getTicketsByCpf(cpf)).thenReturn(response);

        ResponseEntity<List<TicketResponseDTO>> result = ticketControler.getTicketsByCpf(cpf);

        assertEquals(OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(ticketServiceTest, times(1)).getTicketsByCpf(cpf);
    }

    @Test
    void getTicketsByEventId_ShouldReturnTicketsByEventId() {
        String eventId = "event123";
        List<TicketResponseDTO> response = List.of(new TicketResponseDTO());
        when(ticketServiceTest.getTicketsByEventId(eventId)).thenReturn(response);

        ResponseEntity<List<TicketResponseDTO>> result = ticketControler.getTicketsByEventId(eventId);

        assertEquals(OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(ticketServiceTest, times(1)).getTicketsByEventId(eventId);
    }

    @Test
    void checkTicketsByEvent_ShouldReturnCheckTicketsResponse() {
        String eventId = "event123";
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO();
        when(ticketServiceTest.checkTicketsByEvent(eventId)).thenReturn(response);

        ResponseEntity<CheckTicketsResponseDTO> result = ticketControler.checkTicketsByEvent(eventId);

        assertEquals(OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(ticketServiceTest, times(1)).checkTicketsByEvent(eventId);
    }

    @Test
    void updateTicket_ShouldReturnUpdatedTicket() {
        String id = "ticket123";
        TicketRequestDTO request = new TicketRequestDTO();
        TicketResponseDTO response = new TicketResponseDTO();
        when(ticketServiceTest.updateTicket(id, request)).thenReturn(response);

        ResponseEntity<TicketResponseDTO> result = ticketControler.updateTicket(id, request);

        assertEquals(OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(ticketServiceTest, times(1)).updateTicket(id, request);
    }

    @Test
    void cancelTicket_ShouldReturnNoContent() {
        String id = "ticket123";

        ResponseEntity<Void> result = ticketControler.cancelTicket(id);

        assertEquals(NO_CONTENT, result.getStatusCode());
        verify(ticketServiceTest, times(1)).cancelTicket(id);
    }
}


