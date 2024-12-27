package com.mseventmanager.ticketmanager.entity;


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
    private String eventId;
    private String eventName;
    private int tickets;
    private TicketStatus status;


    public enum TicketStatus {
        ACTIVE, CANCELLED
    }


}
