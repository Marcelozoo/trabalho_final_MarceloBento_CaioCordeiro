package dao;

import models.Notificacao;
import java.util.List;

public interface NotificacaoDAO {

    void inserirNotificacao(Notificacao notificacao);
    List<Notificacao> listarNotificacoes();
    List<Notificacao> listarNotificacoesPorDestinatario(int destinatarioId);
    void lerNotificacao(Notificacao notificacao);
    void marcarComoLida(int notificacaoId, int destinatarioId);

}
