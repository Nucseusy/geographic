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
@ApiModel(value = "Mesorregion")
public class MesoRegion {
    @ApiModelProperty(value = "Identificador da mesorregião")
    private Integer id;

    @ApiModelProperty(value = "Nome do mesorregião")
    @JsonProperty(value = "nome")
    private String name;
}
