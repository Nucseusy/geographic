package com.br.microservice.geographic.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "Localidade")
public class Locale {
    @ApiModelProperty(value = "Identificador do estado")
    private String idEstado;

    @ApiModelProperty(value = "Sigla do estado")
    private String siglaEstado;

    @ApiModelProperty(value = "Nome da região")
    private String regiaoNome;

    @ApiModelProperty(value = "Nome da cidade")
    private String nomeCidade;

    @ApiModelProperty(value = "Nome do mesorregião")
    private String nomeMesorregiao;

    @ApiModelProperty(value = "Nome formatado: Cidade/UF")
    private String nomeFormatado;
}
