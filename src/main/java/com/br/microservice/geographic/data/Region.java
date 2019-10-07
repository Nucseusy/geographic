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
public class Region {
    @ApiModelProperty(value = "Identificador da região")
    private float id;

    @ApiModelProperty(value = "Nome da região")
    @JsonProperty(value = "sigla")
    private String initial;

    @ApiModelProperty(value = "Sigla da região")
    @JsonProperty(value = "nome")
    private String name;
}
