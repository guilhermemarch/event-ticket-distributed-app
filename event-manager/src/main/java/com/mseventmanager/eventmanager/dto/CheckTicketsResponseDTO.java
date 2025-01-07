package com.mseventmanager.eventmanager.dto;

public class CheckTicketsResponseDTO {

    private String eventId;
    private boolean hasTickets;

    public CheckTicketsResponseDTO() {
    }

    public CheckTicketsResponseDTO(String eventId, boolean hasTickets) {
        this.eventId = eventId;
        this.hasTickets = hasTickets;
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
    public String toString() {
        return "CheckTicketsResponseDTO{" +
                "eventId='" + eventId + '\'' +
                ", hasTickets=" + hasTickets +
                '}';
    }
}
