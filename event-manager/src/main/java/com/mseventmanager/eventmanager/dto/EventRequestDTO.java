package com.mseventmanager.eventmanager.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventRequestDTO {

    private String eventName;
    private String dateTime;
    private String cep;

}
