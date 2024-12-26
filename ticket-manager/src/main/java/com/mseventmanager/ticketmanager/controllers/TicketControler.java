package com.mseventmanager.ticketmanager.controllers;


import com.mseventmanager.ticketmanager.dto.TicketRequestDTO;
import com.mseventmanager.ticketmanager.dto.TicketResponseDTO;
import com.mseventmanager.ticketmanager.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketControler {

    @Autowired
    private TicketService ticketService;


    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody TicketRequestDTO request) {

        TicketResponseDTO response = ticketService.createTicket(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable String id) {
        TicketResponseDTO response = ticketService.getTicketById(id);
        return ResponseEntity.ok(response);
    }

}
