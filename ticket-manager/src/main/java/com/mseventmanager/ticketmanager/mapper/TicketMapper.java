package com.mseventmanager.ticketmanager.mapper;


import com.mseventmanager.ticketmanager.dto.TicketRequestDTO;
import com.mseventmanager.ticketmanager.dto.TicketResponseDTO;
import com.mseventmanager.ticketmanager.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {


       @Mapping(target = "event.eventName", source = "eventName")
        Ticket toTicket(TicketRequestDTO ticketRequest);


        TicketResponseDTO toDTO(Ticket ticket);
}