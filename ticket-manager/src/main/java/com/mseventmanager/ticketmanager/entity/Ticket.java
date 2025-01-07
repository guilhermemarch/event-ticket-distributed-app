package com.mseventmanager.ticketmanager.entity;


import com.mseventmanager.ticketmanager.dto.EventResponseDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Objects;

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


    public Ticket(String id, String customerName, String cpf, String customerMail, TicketStatus status, EventResponseDTO event) {
        this.id = id;
        this.customerName = customerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.status = status;
        this.event = event;
    }

    public Ticket() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public EventResponseDTO getEvent() {
        return event;
    }

    public void setEvent(EventResponseDTO event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(customerName, ticket.customerName) && Objects.equals(cpf, ticket.cpf) && Objects.equals(customerMail, ticket.customerMail) && status == ticket.status && Objects.equals(event, ticket.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, cpf, customerMail, status, event);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", customerName='" + customerName + '\'' +
                ", cpf='" + cpf + '\'' +
                ", customerMail='" + customerMail + '\'' +
                ", status=" + status +
                ", event=" + event +
                '}';
    }
}
