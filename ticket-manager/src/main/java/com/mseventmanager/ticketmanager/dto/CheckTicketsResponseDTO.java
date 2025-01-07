package com.mseventmanager.ticketmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


public class CheckTicketsResponseDTO {

    private String eventId;
    private boolean hasTickets;

    public CheckTicketsResponseDTO(String eventId, boolean hasTickets) {
        this.eventId = eventId;
        this.hasTickets = hasTickets;
    }

    public CheckTicketsResponseDTO() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isHasTickets() {
        return hasTickets;
    }

    public void setHasTickets(boolean hasTickets) {
        this.hasTickets = hasTickets;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CheckTicketsResponseDTO that = (CheckTicketsResponseDTO) o;
        return hasTickets == that.hasTickets && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, hasTickets);
    }

    @Override
    public String toString() {
        return "CheckTicketsResponseDTO{" +
                "eventId='" + eventId + '\'' +
                ", hasTickets=" + hasTickets +
                '}';
    }
}
