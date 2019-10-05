package com.br.microservice.geographic.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FederativeUnity {
    private Integer id;
    private String sigla;
    private String nome;
    private Region regiao;
}
