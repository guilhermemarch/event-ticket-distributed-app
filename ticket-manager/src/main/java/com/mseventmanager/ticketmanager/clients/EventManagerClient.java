package com.mseventmanager.ticketmanager.clients;

import com.mseventmanager.ticketmanager.dto.EventResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-event-manager", url = "http://localhost:8080")
public interface EventManagerClient {

    @GetMapping("/get-event/{id}")
    EventResponseDTO validateEvent(@PathVariable("id") String eventId);
}