package com.mseventmanager.ticketmanager.repositories;

import com.mseventmanager.ticketmanager.entity.Ticket;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findByCpf(String cpf);

    List<Ticket> findByEventId(String eventId);

    boolean existsByEvent_IdAndStatus(ObjectId eventId, Ticket.TicketStatus status);
}
