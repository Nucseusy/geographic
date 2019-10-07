package com.br.microservice.geographic.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicroRegion {
    private Integer id;

    @JsonProperty(value = "nome")
    private String name;

    @JsonProperty(value = "mesorregiao")
    private MesoRegion mesoRegion;
}
