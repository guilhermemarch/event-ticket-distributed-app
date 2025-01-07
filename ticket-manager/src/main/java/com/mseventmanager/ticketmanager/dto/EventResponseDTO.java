package com.mseventmanager.ticketmanager.dto;



import java.util.Objects;


public class EventResponseDTO {

    private String id;
    private String eventName;
    private String dateTime;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;


    public EventResponseDTO() {
    }

    public EventResponseDTO(String id, String eventName, String dateTime, String logradouro, String bairro, String cidade, String uf) {
        this.id = id;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventResponseDTO that = (EventResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(eventName, that.eventName) && Objects.equals(dateTime, that.dateTime) && Objects.equals(logradouro, that.logradouro) && Objects.equals(bairro, that.bairro) && Objects.equals(cidade, that.cidade) && Objects.equals(uf, that.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, dateTime, logradouro, bairro, cidade, uf);
    }

    @Override
    public String toString() {
        return "EventResponseDTO{" +
                "id='" + id + '\'' +
                ", eventName='" + eventName + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
