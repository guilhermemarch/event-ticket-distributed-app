package com.mseventmanager.ticketmanager.dto;



import java.util.Objects;

public class TicketRequestDTO {

    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventName;
    private String eventId;




//    private BigDecimal BRLAmount;
//    private BigDecimal  USDAmount;


    public TicketRequestDTO(String customerName, String cpf, String customerMail, String eventName, String eventId) {
        this.customerName = customerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.eventName = eventName;
        this.eventId = eventId;
    }

    public TicketRequestDTO() {
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TicketRequestDTO that = (TicketRequestDTO) o;
        return Objects.equals(customerName, that.customerName) && Objects.equals(cpf, that.cpf) && Objects.equals(customerMail, that.customerMail) && Objects.equals(eventName, that.eventName) && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, cpf, customerMail, eventName, eventId);
    }

    @Override
    public String toString() {
        return "TicketRequestDTO{" +
                "customerName='" + customerName + '\'' +
                ", cpf='" + cpf + '\'' +
                ", customerMail='" + customerMail + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
