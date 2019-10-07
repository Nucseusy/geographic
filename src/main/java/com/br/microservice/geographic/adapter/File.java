package com.br.microservice.geographic.adapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {

//    private String filesName;

//    private String header;

    private String footer;

    private String separator;

    private String initialTagDecorator;

    private String finalTagDecorator;

    private String initialBlock;

    private String finishBlock;
//
//    private String contentType;

    private String blockSeparator;
}
