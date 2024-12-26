package com.mseventmanager.ticketmanager.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String customerMail;

    @Column(nullable = false)
    private String eventId;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private int tickets;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;


}
