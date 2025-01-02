package com.mseventmanager.eventmanager.clients;

import com.mseventmanager.eventmanager.dto.ViaCepResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "ViaCepClient", url = "https://viacep.com.br/ws/")
public interface ViaCepClient {

    @GetMapping(value = "{cep}/json")
    ViaCepResponseDTO getAddressByCep(@PathVariable("cep") String cep);

}
