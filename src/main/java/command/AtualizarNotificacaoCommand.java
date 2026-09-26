package command;

import models.ResultadoOperacao;
import services.NotificacaoService;

public class AtualizarNotificacaoCommand implements Command<Void> {

    private final int notificacaoId;
    private final int usuarioId;
    private final NotificacaoService notificacaoService;
    private ResultadoOperacao<Void> resultado;

    public AtualizarNotificacaoCommand(
            int notificacaoId,
            int usuarioId,
            NotificacaoService notificacaoService
    ) {
        this.notificacaoId = notificacaoId;
        this.usuarioId = usuarioId;
        this.notificacaoService = notificacaoService;
    }

    @Override
    public void executar() {
        resultado = notificacaoService.marcarComoLida(notificacaoId, usuarioId);
    }

    @Override
    public ResultadoOperacao<Void> getResultado() {
        return resultado;
    }
}
