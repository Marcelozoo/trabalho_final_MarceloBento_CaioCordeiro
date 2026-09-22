package dao;

import models.Notificacao;
import java.util.List;

public interface NotificacaoDAO {

    void inserirNotificacao(Notificacao notificacao);
    List<Notificacao> listarNotificacoes();
    void lerNotificacao(Notificacao notificacao);

}
