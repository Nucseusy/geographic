package com.br.microservice.geographic.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MicroRegion {
    private float id;
    private String nome;
    private MesoRegion mesorregiao;
}
