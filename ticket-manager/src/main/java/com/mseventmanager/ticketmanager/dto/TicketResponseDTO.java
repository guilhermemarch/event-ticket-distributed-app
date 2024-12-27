package com.mseventmanager.ticketmanager.dto;


import com.mseventmanager.ticketmanager.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketResponseDTO {


    private String id;
    private String customerName;
    private String cpf;
    private String customerMail;
    private EventResponseDTO event;
    private Ticket.TicketStatus status;


}
