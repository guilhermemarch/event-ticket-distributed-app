package com.mseventmanager.eventmanager.mapper;


import com.mseventmanager.eventmanager.dto.EventRequestDTO;
import com.mseventmanager.eventmanager.dto.EventResponseDTO;
import com.mseventmanager.eventmanager.dto.ViaCepResponseDTO;
import com.mseventmanager.eventmanager.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {

        EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);


        Event toEvent(EventRequestDTO eventRequestDTO, ViaCepResponseDTO viaCepResponse);

        EventResponseDTO toDTO(Event event);


}