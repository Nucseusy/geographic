package com.br.microservice.geographic.adapter;

import com.br.microservice.geographic.data.Locale;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WriteFileAdapterImpl implements IWriteFileAdapter {
    WriteFile writeFile = new WriteFile();

    private Charset charset = StandardCharsets.UTF_8;

    @Override
    public OutputStream getJson(HttpServletResponse response, List<Locale> locales) throws Exception {
        OutputStream out = getHeader(response, EnumFile.JSON.getcontextType(), "download-json-localidades.json", "[{");
        File.FileBuilder file = writeFile.getFile();
        file.footer("}]")
                .separator(",")
                .initialTagDecorator(" \"%s\": \"")
                .finalTagDecorator("\"")
                .initialBlock("{")
                .finishBlock("}")
                .blockSeparator(",");

        return converter(out, locales, file.build());
    }

    @Override
    public OutputStream getCsv(HttpServletResponse response, List<Locale> locales) throws Exception {
        OutputStream out = getHeader(response, EnumFile.CSV.getcontextType(), "download-csv-localidades.csv", "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado");
        File.FileBuilder file = writeFile.getFile();
        file.separator(",");
        return converter(out, locales, file.build());
    }

    @Override
    public OutputStream getXML(HttpServletResponse response, List<Locale> locales) throws Exception {
        OutputStream out = getHeader(response, EnumFile.XML.getcontextType(), "download-xml-localidades.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n <root> \n");
        File.FileBuilder file = writeFile.getFile();
        file.footer("</root>")
                .separator("\n")
                .initialTagDecorator("<%s>")
                .finalTagDecorator("</%s>")
                .initialBlock("<element>")
                .finishBlock("</element>")
                .blockSeparator("\n");

        return converter(out, locales, file.build());
    }

    private OutputStream getHeader(HttpServletResponse response, String contentType, String fileName, String header) throws IOException {
        OutputStream out = response.getOutputStream();
        response.setContentType(contentType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        if (header != null) {
            out.write(header.getBytes(charset));
            out.write("\n".getBytes(charset));
        }
        return out;
    }

    private String getTagDecorator(String value, String initialTag, String finalTag) {
        if (initialTag != null)
            value = initialTag.concat(value);
        if (finalTag != null)
            value += finalTag;
        return value;
    }

    public OutputStream converter(OutputStream out, List<Locale> locales, File file) throws Exception {
        byte[] separator = file.getSeparator().getBytes(charset);
        int size = locales.size();
        int i = 1;
        for (Locale locale : locales) {
            if (file.getInitialBlock() != null)
                out.write(file.getInitialBlock().getBytes(charset));

            getTag(out, String.format(getTagDecorator(locale.getIdEstado(), file.getInitialTagDecorator(), file.getFinalTagDecorator()), "idEstado"), separator);
            getTag(out, String.format(getTagDecorator(locale.getSiglaEstado(), file.getInitialTagDecorator(), file.getFinalTagDecorator()), "siglaEstado"), separator);
            getTag(out, String.format(getTagDecorator(locale.getRegiaoNome(), file.getInitialTagDecorator(), file.getFinalTagDecorator()), "regiaoNome"), separator);
            getTag(out, String.format(getTagDecorator(locale.getNomeCidade(), file.getInitialTagDecorator(), file.getFinalTagDecorator()), "nomeCidade"), separator);
            getTag(out, String.format(getTagDecorator(locale.getNomeMesorregiao(), file.getInitialTagDecorator(), file.getFinalTagDecorator()), "nomeMesorregiao"), separator);
            getTag(out, String.format(getTagDecorator(locale.getNomeFormatado(), file.getInitialTagDecorator(), file.getFinalTagDecorator()), "nomeFormatado"), null);

            if (file.getFinishBlock() != null) {
                out.write(file.getFinishBlock().getBytes(charset));
            }

            if (size > 1 && i < size && file.getBlockSeparator() != null) {
                out.write(file.getBlockSeparator().getBytes(charset));
            }

            out.write("\n".getBytes(charset));
            i++;
        }
        if (file.getFooter() != null)
            out.write(file.getFooter().getBytes(charset));

        out.flush();
        return null;
    }

    private OutputStream getTag(OutputStream outputStream, String value, byte[] separator) throws IOException {
        outputStream.write(value.getBytes(charset));
        if (separator != null)
            outputStream.write(separator);
        return outputStream;
    }
}
