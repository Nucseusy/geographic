package com.br.microservice.geographic.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Estado")
public class State {
    @ApiModelProperty(value = "Identificador da Unidade da Federação")
    private Integer id;

    @ApiModelProperty(value = "Nome da Unidade da Federação")
    @JsonProperty(value = "sigla")
    private String initial;

    @ApiModelProperty(value = "Sigla da Unidade da Federação")
    @JsonProperty(value = "nome")
    private String name;

    @JsonProperty(value = "regiao")
    Region region;
}
