package command;

import models.Notificacao;
import models.ResultadoOperacao;
import services.NotificacaoService;

import java.util.List;

public class BuscarNotificacoesNLidasCommand implements Command<List<Notificacao>> {

    private int usuarioId;
    private final NotificacaoService notificacaoService;
    private ResultadoOperacao<List<Notificacao>> resultado;

    public BuscarNotificacoesNLidasCommand(int usuarioId, NotificacaoService notificacaoService) {
        this.usuarioId = usuarioId;
        this.notificacaoService = notificacaoService;
    }

    @Override
    public void executar() {
        resultado = notificacaoService.listarPorUsuario(usuarioId);
    }

    @Override
    public ResultadoOperacao<List<Notificacao>> getResultado() {
        return resultado;
    }
}
