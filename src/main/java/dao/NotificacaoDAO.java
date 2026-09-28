package dao;

import models.Notificacao;
import java.util.List;

public interface NotificacaoDAO {

    void inserirNotificacao(Notificacao notificacao);
    List<Notificacao> listarNotificacoes();
    List<Notificacao> listarNotificacoesPorDestinatario(int destinatarioId);
    void marcarComoLida(int notificacaoId);
    Notificacao buscarNotificacao(int id);
    int buscarQtdNotificacoesNLidas(int idUsuario);
    int buscarQtdNotificacoesEnviadas(int idUsuario);

    int buscarQtdNotificacoesLidas(int id);
}
