package com.mseventmanager.eventmanager.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ViaCepResponseDTO {

    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

}
