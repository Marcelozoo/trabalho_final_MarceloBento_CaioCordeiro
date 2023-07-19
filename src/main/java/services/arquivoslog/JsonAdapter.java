package services.arquivoslog;

import java.time.LocalDate;
import java.time.LocalTime;

public class JsonAdapter implements IArquivoLog {
    final private JsonApiSingleton arquivoJson;
    
    public JsonAdapter (JsonApiSingleton arqJsonApi){
        this.arquivoJson = arqJsonApi;
        
    }
    @Override
    public void salvarLog(String msg, String operacao, String nome, LocalDate data, LocalTime hora, boolean usuarioAutenticado){
        this.arquivoJson.salvarLog(msg, operacao, nome, data,hora, usuarioAutenticado);     
    }
    
    @Override
    public boolean verificaTipoArquivo(String tipoArquivo){
        return tipoArquivo.equals("Json");
    }
}
