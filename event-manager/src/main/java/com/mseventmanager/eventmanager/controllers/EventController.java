package com.mseventmanager.eventmanager.controllers;


import com.mseventmanager.eventmanager.dto.EventRequestDTO;
import com.mseventmanager.eventmanager.dto.EventResponseDTO;
import com.mseventmanager.eventmanager.entity.Event;
import com.mseventmanager.eventmanager.mapper.EventMapper;
import com.mseventmanager.eventmanager.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;


    @Autowired
    private EventMapper eventMapper;

    @PostMapping("/create-event")
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO request) {
        EventResponseDTO response = eventService.createEvent(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable String id) {
        Optional<Event> eventId = eventService.getEventById(id);

        if (eventId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EventResponseDTO response = eventMapper.toDTO(eventId.get());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-event/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(@PathVariable String id, @RequestBody EventRequestDTO request) {
        EventResponseDTO response = eventService.updateEvent(id, request);
        return ResponseEntity.ok(response);
    }

//
//    createEvent(EventRequest request) - POST /create-event (concluido)
//
//    getEventById(String id) - GET /get-event/{id} (CONCLUIDO)
//
//    getAllEvents() - GET /get-all-events (CONCLUIDO)
//
//    getAllEventsSorted() - GET /get-all-events/sorted
//
//    updateEvent(String id, EventRequest request)  - PUT /update-event/{id}  (CONCLUIDO)
//
//    deleteEvent(String id) - DELETE /delete-event/{id}  (CONCLUIDO)

}
