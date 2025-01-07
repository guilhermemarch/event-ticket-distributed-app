package com.mseventmanager.ticketmanager.dto;


import com.mseventmanager.ticketmanager.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

public class TicketResponseDTO {


    private String id;
    private String customerName;
    private String cpf;
    private String customerMail;
    private EventResponseDTO event;

//    private  BigDecimal BRLAmount;
//    private BigDecimal  USDAmount;

    private Ticket.TicketStatus status;


    public TicketResponseDTO() {
    }

    public TicketResponseDTO(String id, String customerName, String cpf, String customerMail, EventResponseDTO event, Ticket.TicketStatus status) {
        this.id = id;
        this.customerName = customerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.event = event;
        this.status = status;
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

    public EventResponseDTO getEvent() {
        return event;
    }

    public void setEvent(EventResponseDTO event) {
        this.event = event;
    }

    public Ticket.TicketStatus getStatus() {
        return status;
    }

    public void setStatus(Ticket.TicketStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TicketResponseDTO that = (TicketResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(customerName, that.customerName) && Objects.equals(cpf, that.cpf) && Objects.equals(customerMail, that.customerMail) && Objects.equals(event, that.event) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, cpf, customerMail, event, status);
    }

    @Override
    public String toString() {
        return "TicketResponseDTO{" +
                "id='" + id + '\'' +
                ", customerName='" + customerName + '\'' +
                ", cpf='" + cpf + '\'' +
                ", customerMail='" + customerMail + '\'' +
                ", event=" + event +
                ", status=" + status +
                '}';
    }
}
