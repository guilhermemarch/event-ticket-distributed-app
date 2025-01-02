package com.mseventmanager.eventmanager.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class EventRequestDTO {

    private String eventName;
    private String dateTime;
    private String cep;

}
