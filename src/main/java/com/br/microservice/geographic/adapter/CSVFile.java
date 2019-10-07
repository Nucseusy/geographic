package com.br.microservice.geographic.adapter;

public class CSVFile implements IGenericFile {

    private String fileName;
    private String header;

    public CSVFile(String fileName, String header) {
        setFileName(fileName);
        this.header = header;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getHeader() {
        return this.header;
    }

    public String getFooter() {
        return null;
    }

    public String getSeparator() {
        return ",";
    }

    public String getTagDecorator(Object value) {
        return (String)value;
    }

    public String getInitialBlock() {
        return null;
    }

    public String getFinishBlock() {
        return null;
    }

    public String getContentType() {
        return EnumFile.CSV.getcontextType();
    }

    public String getBlockSeparator() {
        return null;
    }
}
