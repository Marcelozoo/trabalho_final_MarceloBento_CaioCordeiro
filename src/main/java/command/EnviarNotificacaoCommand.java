package command;

import models.ResultadoOperacao;
import services.EnviarNotificacaoService;

public class EnviarNotificacaoCommand implements  Command<Void>{

    private final String destinatario;
    private final String remetente;
    private final String msg;
    private final EnviarNotificacaoService notificacao;
    private ResultadoOperacao<Void> resultado;

    public EnviarNotificacaoCommand(String destinatario , String remetente, String msg, EnviarNotificacaoService notificacao) {
        this.destinatario = destinatario;
        this.remetente = remetente;
        this.msg = msg;
        this.notificacao = notificacao;
    }

    @Override
    public void executar() {
        resultado = new ResultadoOperacao<>();
        notificacao.enviar(destinatario, remetente, msg, resultado);
    }

    @Override
    public ResultadoOperacao<Void> getResultado() {
        return resultado;
    }
}
