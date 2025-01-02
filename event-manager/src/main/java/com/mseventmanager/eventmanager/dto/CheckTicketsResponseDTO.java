package com.mseventmanager.eventmanager.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CheckTicketsResponseDTO {

    private String eventId;
    private boolean hasTickets;
}
