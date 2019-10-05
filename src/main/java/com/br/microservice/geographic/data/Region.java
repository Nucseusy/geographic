package com.br.microservice.geographic.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Region {
    @ApiModelProperty(value = "Identificador da região")
    private float id;

    @ApiModelProperty(value = "Nome da região")
//    @JsonProperty(value = "sigla")
//    private String initial;
    private String sigla;

    @ApiModelProperty(value = "Sigla da região")
//    @JsonProperty(value = "nome")
//    private String name;
    private String nome;
}
