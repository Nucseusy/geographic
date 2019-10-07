package com.br.microservice.geographic.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Zone {
    @ApiModelProperty(value = "Identificador do município")
    private Integer id;

    @ApiModelProperty(value = "Nome do município")
    @JsonProperty(value = "nome")
    private String name;

    @JsonProperty(value = "microrregiao")
    private MicroRegion microRegion;
}
