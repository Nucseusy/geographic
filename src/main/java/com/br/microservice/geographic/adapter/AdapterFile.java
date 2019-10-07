package com.br.microservice.geographic.adapter;

import com.br.microservice.geographic.data.Locale;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AdapterFile implements File {

    private IGenericFile genericFile;
    private Charset charset;

    public AdapterFile(IGenericFile genericFile) {
        this.genericFile = genericFile;
        this.charset = StandardCharsets.UTF_8;
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

        byte[] separator = genericFile.getSeparator().getBytes(charset);
        int size = locales.size();
        int i = 1;
        for (Locale locale : locales) {
            if (genericFile.getInitialBlock() != null)
                out.write(genericFile.getInitialBlock().getBytes(charset));

            getTag(out, String.format(genericFile.getTagDecorator(locale.getIdEstado()), "idEstado"), separator);
            getTag(out, String.format(genericFile.getTagDecorator(locale.getSiglaEstado()), "siglaEstado"), separator);
            getTag(out, String.format(genericFile.getTagDecorator(locale.getRegiaoNome()), "regiaoNome"), separator);
            getTag(out, String.format(genericFile.getTagDecorator(locale.getNomeCidade()), "nomeCidade"), separator);
            getTag(out, String.format(genericFile.getTagDecorator(locale.getNomeMesorregiao()), "nomeMesorregiao"), separator);
            getTag(out, String.format(genericFile.getTagDecorator(locale.getNomeFormatado()), "nomeFormatado"), null);

            if (genericFile.getFinishBlock() != null) {
                out.write(genericFile.getFinishBlock().getBytes(charset));
            }

            if (size > 1 && i < size && genericFile.getBlockSeparator() != null) {
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

    private OutputStream getTag(OutputStream outputStream, String value, byte[] separator) throws IOException {
        outputStream.write(value.getBytes(charset));
        if (separator != null)
            outputStream.write(separator);
        return outputStream;
    }
}
