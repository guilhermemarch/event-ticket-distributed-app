package com.mseventmanager.ticketmanager.entity;


import com.mseventmanager.ticketmanager.dto.EventResponseDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;
    private String customerName;
    private String cpf;
    private String customerMail;
    private int tickets;
    private TicketStatus status;

    private EventResponseDTO event;

    public enum TicketStatus {
        ACTIVE, CANCELLED
    }


}
