package com.mseventmanager.ticketmanager.repositories;

import com.mseventmanager.ticketmanager.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {

}
