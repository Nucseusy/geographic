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
@ApiModel(value = "Microregion")
public class MicroRegion {
    @ApiModelProperty(value = "Identificador da microrregião")
    private Integer id;

    @ApiModelProperty(value = "Nome do microrregião")
    @JsonProperty(value = "nome")
    private String name;

    @JsonProperty(value = "mesorregiao")
    private MesoRegion mesoRegion;
}
