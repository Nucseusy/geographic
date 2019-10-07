package com.br.microservice.geographic.adapter;

public class XMLFile implements IGenericFile {

    private String fileName;

    public XMLFile(String fileName){
        setFileName(fileName);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getHeader() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root> \n";
    }

    public String getFooter() {
        return "</root>";
    }

    public String getSeparator() {
        return "\n";
    }

    public String getTagDecorator(Object value) {
        return "<%s>".concat((String)value).concat("</%s>");
    }

    public String getInitialBlock() {
        return "<element>";
    }

    public String getFinishBlock() {
        return "</element>";
    }

    public String getContentType() {
        return EnumFile.XML.getcontextType();
    }

    public String getBlockSeparator() {
        return "\n";
    }
}
