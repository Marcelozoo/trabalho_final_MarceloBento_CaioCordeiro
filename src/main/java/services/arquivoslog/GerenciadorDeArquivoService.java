package services.arquivoslog;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class GerenciadorDeArquivoService {
    ArrayList<IArquivoLog> salvarArquivos;

    public GerenciadorDeArquivoService() {
        this.salvarArquivos = new ArrayList<>();
        this.salvarArquivos.add(new JsonAdapter(JsonApiSingleton.getInstancia()));
    }
    
    public void processarLog(String msg,String tipo, String operacao, String nome, LocalDate data, LocalTime hora, boolean usuarioAutenticado){
        for(IArquivoLog formaSalvar : salvarArquivos){
            if(formaSalvar.verificaTipoArquivo(tipo)){
                formaSalvar.salvarLog(msg, operacao, nome, data,hora, usuarioAutenticado);
                break;
            }
        }   
    }
}
