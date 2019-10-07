package com.br.microservice.geographic.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Locale {
    private String idEstado;
    private String siglaEstado;
    private String regiaoNome;
    private String nomeCidade;
    private String nomeMesorregiao;
    private String nomeFormatado;
}
