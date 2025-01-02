package com.mseventmanager.ticketmanager.controllers;


import com.mseventmanager.ticketmanager.dto.CheckTicketsResponseDTO;
import com.mseventmanager.ticketmanager.dto.TicketRequestDTO;
import com.mseventmanager.ticketmanager.dto.TicketResponseDTO;
import com.mseventmanager.ticketmanager.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("api/ticket")
public class TicketControler {


    @Autowired
    private TicketService ticketService;


    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketRequestDTO request) {

        TicketResponseDTO response = ticketService.createTicket(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable String id) {
        TicketResponseDTO response = ticketService.getTicketById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByCpf(@PathVariable String cpf) {
        List<TicketResponseDTO> response = ticketService.getTicketsByCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-ticket-by-event/{eventId}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByEventId(@PathVariable String eventId) {
        List<TicketResponseDTO> response = ticketService.getTicketsByEventId(eventId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-tickets-by-event/{eventId}")
    public ResponseEntity<CheckTicketsResponseDTO> checkTicketsByEvent(@PathVariable String eventId) {
        CheckTicketsResponseDTO response = ticketService.checkTicketsByEvent(eventId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable String id, @RequestBody TicketRequestDTO request) {
        TicketResponseDTO response = ticketService.updateTicket(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> cancelTicket(@PathVariable String id) {
        ticketService.cancelTicket(id);

        return ResponseEntity.noContent().build();
    }
}
