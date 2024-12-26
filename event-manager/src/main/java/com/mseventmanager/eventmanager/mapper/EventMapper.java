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

        @Mapping(target = "logradouro", source = "viaCepResponse.logradouro")
        @Mapping(target = "bairro", source = "viaCepResponse.bairro")
        @Mapping(target = "cidade", source = "viaCepResponse.localidade")
        @Mapping(target = "uf", source = "viaCepResponse.uf")
        @Mapping(target = "id", ignore = true)
        Event toEvent(EventRequestDTO eventRequestDTO, ViaCepResponseDTO viaCepResponse);

        EventResponseDTO toDTO(Event event);

        @Mapping(target = "id", ignore = true)
        void updateEventFromRequest(EventRequestDTO eventRequestDTO, @MappingTarget Event event);
}