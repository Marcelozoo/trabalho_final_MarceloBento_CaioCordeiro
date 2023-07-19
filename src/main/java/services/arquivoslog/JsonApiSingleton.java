package services.arquivoslog;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import models.log.Log;

public class JsonApiSingleton {

    final private ObjectMapper objetoMapper;
    private FileWriter arquivoJson;
    private static JsonApiSingleton instancia = null;

    private JsonApiSingleton() {

        objetoMapper = new ObjectMapper();

    }

    public static JsonApiSingleton getInstancia() {
        if (instancia == null) {
            instancia = new JsonApiSingleton();
        }
        return instancia;
    }

    public void salvarLog(String msg, String operacao, String nome, LocalDate data, LocalTime hora, boolean usuarioAutenticado) {

        if (arquivoJson == null) {
            criaArquivo();
        } else {
            try {
                this.arquivoJson = new FileWriter("log.json", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!msg.isEmpty()) {
            Log log = new Log(msg, operacao, nome, data, hora, usuarioAutenticado);
            escreveNoArquivoJson(log);
        } else {
            Log log = new Log(operacao, nome, data, hora, usuarioAutenticado);
            escreveNoArquivoJson(log);
        }

    }

    private void escreveNoArquivoJson(Log log) {
        String json;
        try {

            json = objetoMapper.writeValueAsString(log);
            arquivoJson.write(json);
            arquivoJson.write(System.lineSeparator());
            arquivoJson.flush();
            arquivoJson.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void criaArquivo() {
        try {
            this.arquivoJson = new FileWriter("log.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
