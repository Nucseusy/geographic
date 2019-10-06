package com.br.microservice.geographic.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicroRegion {
    private Integer id;
    private String nome;
    private MesoRegion mesorregiao;
}
