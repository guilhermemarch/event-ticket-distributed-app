package com.mseventmanager.eventmanager.dto;

public class EventRequestDTO {

    private String eventName;
    private String dateTime;
    private String cep;

    public EventRequestDTO() {
    }

    public EventRequestDTO(String eventName, String dateTime, String cep) {
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.cep = cep;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "EventRequestDTO{" +
                "eventName='" + eventName + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }
}