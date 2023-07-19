package models.log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Log {

    private String msgFalha;
    private String operacao;
    private String nome;
    private String data;
    private String hora;
    private boolean usuarioAutenticado;

    public Log(String operacao, String nome, LocalDate data, LocalTime hora, boolean usuarioAutenticado) {
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        this.operacao = operacao;
        this.nome = nome;
        this.data = data.format(formatterData);
        this.hora = hora.format(formatterHora);
        this.usuarioAutenticado = usuarioAutenticado;
    }

    public Log(String msgFalha, String operacao, String nome, LocalDate data, LocalTime hora, boolean usuarioAutenticado) {

        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.msgFalha = msgFalha;
        this.operacao = operacao;
        this.nome = nome;
        this.data = data.format(formatterData);
        this.hora = hora.format(formatterHora);
        this.usuarioAutenticado = usuarioAutenticado;
    }
    
    public String getMsgFalha() {
        return msgFalha;
    }

    public void setMsgFalha(String msgFalha) {
        this.msgFalha = msgFalha;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void setUsuarioAutenticado(boolean usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    }

}
