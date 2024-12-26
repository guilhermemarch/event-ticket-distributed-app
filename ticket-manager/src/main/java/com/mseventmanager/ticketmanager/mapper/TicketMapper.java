package com.mseventmanager.ticketmanager.mapper;


import com.mseventmanager.ticketmanager.dto.TicketRequestDTO;
import com.mseventmanager.ticketmanager.dto.TicketResponseDTO;
import com.mseventmanager.ticketmanager.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {

        TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

        Ticket toTicket(TicketRequestDTO ticketRequest);

        TicketResponseDTO toTicketResponse(Ticket ticket);

        TicketResponseDTO toDTO(Ticket ticket);

}