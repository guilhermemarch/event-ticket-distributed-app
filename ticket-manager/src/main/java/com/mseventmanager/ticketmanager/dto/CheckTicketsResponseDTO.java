package com.mseventmanager.ticketmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckTicketsResponseDTO {

    private String eventId;
    private boolean hasTickets;

}
