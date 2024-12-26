package com.mseventmanager.ticketmanager.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ticket")
public class Ticket {


}
