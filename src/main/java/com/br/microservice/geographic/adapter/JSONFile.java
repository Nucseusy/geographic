package com.br.microservice.geographic.adapter;

public class JSONFile implements IGenericFile {

    private String fileName;

    public JSONFile(String fileName) {
        setFileName(fileName);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getHeader() {
        return "[{";
    }

    public String getFooter() {
        return "}]";
    }

    public String getSeparator() {
        return ",";
    }

    public String getTagDecorator(Object value) {
        if (value instanceof String) {
            return " \"%s\": \"".concat((String) value).concat("\"");
        } else {
            return " \"%s\": ".concat((String) value);
        }
    }

    public String getInitialBlock() {
        return "{";
    }

    public String getFinishBlock() {
        return "}";
    }

    public String getContentType() {
        return EnumFile.JSON.getcontextType();
    }

    public String getBlockSeparator() {
        return ",";
    }
}
