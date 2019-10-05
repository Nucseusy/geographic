package com.br.microservice.geographic.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Zone {
    private Integer id;
    private String nome;
    private MicroRegion microrregiao;
}
