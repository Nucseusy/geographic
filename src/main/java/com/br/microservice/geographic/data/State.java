package com.br.microservice.geographic.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class State {
    @ApiModelProperty(value = "Identificador da Unidade da Federação")
    private Integer id;

    @ApiModelProperty(value = "Nome da Unidade da Federação")
   // @JsonProperty(value = "sigla")
//    private String initial;
    private String sigla;

    @ApiModelProperty(value = "Sigla da Unidade da Federação")
//    @JsonProperty(value = "nome")
//    private String name;
    private String nome;

//    @JsonProperty(value = "regiao")
//    Region region;
    Region regiao;
}
