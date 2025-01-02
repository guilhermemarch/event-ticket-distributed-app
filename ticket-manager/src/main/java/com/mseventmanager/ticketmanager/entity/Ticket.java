package com.mseventmanager.ticketmanager.entity;


import com.mseventmanager.ticketmanager.dto.EventResponseDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;
    private String customerName;
    private String cpf;
    private String customerMail;


//    private BigDecimal BRLAmount;
//    private BigDecimal  USDAmount;


    private TicketStatus status;



    private EventResponseDTO event;

    public enum TicketStatus {
        ACTIVE, CANCELLED
    }



}
