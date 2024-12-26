package com.mseventmanager.ticketmanager.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {

        TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);


}