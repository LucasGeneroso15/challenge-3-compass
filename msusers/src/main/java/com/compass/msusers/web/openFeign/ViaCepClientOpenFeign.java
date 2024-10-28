package com.compass.msusers.web.openFeign;

import com.compass.msusers.web.dto.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClientOpenFeign {

    @GetMapping("/{cep}/json")
    ViaCepResponse getAddressByZipCode(@PathVariable("cep") String cep);

}
