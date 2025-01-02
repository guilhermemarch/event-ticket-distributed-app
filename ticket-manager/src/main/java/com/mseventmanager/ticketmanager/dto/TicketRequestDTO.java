package com.mseventmanager.ticketmanager.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketRequestDTO {

    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventName;
    private String eventId;


//    private BigDecimal BRLAmount;
//    private BigDecimal  USDAmount;

}
