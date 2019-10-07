package com.br.microservice.geographic.adapter;

public interface IGenericFile {
    void setFileName(String fileName);

    String getFileName();

    String getHeader();

    String getFooter();

    String getSeparator();

    String getTagDecorator(Object value);

    String getInitialBlock();

    String getFinishBlock();

    String getContentType();

    String getBlockSeparator();
}
