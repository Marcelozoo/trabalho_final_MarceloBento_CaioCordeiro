package services.arquivoslog;

import java.time.LocalDate;
import java.time.LocalTime;


public interface IArquivoLog {
    
    public void salvarLog(String msg, String operacao, String nome, LocalDate data, LocalTime hora,boolean usuarioAutenticado);
    public boolean verificaTipoArquivo(String tipoArquivo);
    
}
