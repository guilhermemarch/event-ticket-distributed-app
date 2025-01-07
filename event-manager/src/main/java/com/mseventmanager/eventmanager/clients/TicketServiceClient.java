package com.mseventmanager.eventmanager.clients;

import com.mseventmanager.eventmanager.dto.CheckTicketsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-ticket-manager", url = "http://ms-ticket-manager:8081")
public interface TicketServiceClient {

    @GetMapping("/check-tickets-by-event/{eventId}")
    CheckTicketsResponseDTO checkTicketsByEvent(@PathVariable String eventId);
}