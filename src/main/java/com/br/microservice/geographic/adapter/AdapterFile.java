package com.br.microservice.geographic.adapter;

import com.br.microservice.geographic.data.Locale;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class AdapterFile implements File {

    private IGenericFile genericFile;
    private Charset charset;

    public AdapterFile(IGenericFile genericFile) {
        this.genericFile = genericFile;
        this.charset = Charset.forName("UTF-8");
    }

    @Override
    public OutputStream getOutputStream(HttpServletResponse response, List<Locale> locales) throws Exception {
        OutputStream out = response.getOutputStream();

        response.setContentType(genericFile.getContentType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + genericFile.getFileName() + "\"");

        if (genericFile.getHeader() != null) {
            out.write(genericFile.getHeader().getBytes(charset));
            out.write("\n".getBytes(charset));
        }

        String tagInitial = getDecorator(genericFile.getInitialParameterName(), true);
        String tagFinish = getDecorator(genericFile.getFinishParameterName(), false);
        byte[] separator = genericFile.getSeparator().getBytes(charset);
        int size = locales.size();
        int i = 1;
        for (Locale locale : locales) {
            if (genericFile.getInitialBlock() != null)
                out.write(genericFile.getInitialBlock().getBytes(charset));

            getTag(out, locale.getIdEstado(), String.format(tagInitial, "idEstado").getBytes(), String.format(tagFinish, "idEstado").getBytes(), separator);
            getTag(out, locale.getSiglaEstado(), String.format(tagInitial, "siglaEstado").getBytes(), String.format(tagFinish, "siglaEstado").getBytes(), separator);
            getTag(out, locale.getRegiaoNome(), String.format(tagInitial, "regiaoNome").getBytes(), String.format(tagFinish, "regiaoNome").getBytes(), separator);
            getTag(out, locale.getNomeCidade(), String.format(tagInitial, "nomeCidade").getBytes(), String.format(tagFinish, "nomeCidade").getBytes(), separator);
            getTag(out, locale.getNomeMesorregiao(), String.format(tagInitial, "nomeMesorregiao").getBytes(), String.format(tagFinish, "nomeMesorregiao").getBytes(), separator);
            getTag(out, locale.getNomeFormatado(), String.format(tagInitial, "nomeFormatado").getBytes(), String.format(tagFinish, "nomeFormatado").getBytes(), null);

            if (genericFile.getFinishBlock() != null) {
                out.write(genericFile.getFinishBlock().getBytes(charset));
            }

            if (size > 1 && i < size) {
                out.write(genericFile.getBlockSeparator().getBytes(charset));
            }

            out.write("\n".getBytes(charset));
            i++;
        }
        if (genericFile.getFooter() != null)
            out.write(genericFile.getFooter().getBytes(charset));

        out.flush();
        return null;
    }

    private String getDecorator(String tag, boolean initFlag) {
        String format = null;
        if (genericFile.isUseParameterName() && tag != null) {
            if (initFlag) {
                format = genericFile.getInitialParameterName().concat(genericFile.getInitialParameterValue());
            } else {
                format = genericFile.getFinishParameterValue().concat(genericFile.getFinishParameterName());
            }
        } else {
            format = "";
        }
        return format;
    }

    private OutputStream getTag(OutputStream outputStream, String value, byte[] tagInitial, byte[] tagFinish, byte[] separator) throws IOException {
        outputStream.write(tagInitial);
        outputStream.write(value.getBytes(charset));
        outputStream.write(tagFinish);
        if (separator != null)
            outputStream.write(separator);
        return outputStream;
    }
}
