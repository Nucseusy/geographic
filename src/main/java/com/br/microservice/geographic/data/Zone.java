package com.br.microservice.geographic.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Zone {
    private Integer id;
    private String nome;
    private MicroRegion microrregiao;
}
