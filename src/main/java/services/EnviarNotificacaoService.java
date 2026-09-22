package services;

import dao.NotificacaoDAO;
import dao.UsuariosDAO;
import models.Notificacao;
import models.ResultadoOperacao;

public class EnviarNotificacaoService {

    UsuariosDAO usuariosDAO;
    NotificacaoDAO notificacaoDAO;

    public EnviarNotificacaoService(UsuariosDAO usuariosDAO, NotificacaoDAO notificacaoDAO) {
        this.usuariosDAO = usuariosDAO;
        this.notificacaoDAO = notificacaoDAO;
    }

    public void enviar(String destino, String remetente, String msg, ResultadoOperacao resultado){
            if(destino == null || remetente == null || msg == null || destino.isEmpty() || remetente.isEmpty() || msg.isEmpty()){
                resultado.adicionarErro("Campos inválidos!");
                return;
            }
            int idDestinatario = usuariosDAO.buscarPorNome(destino).getId();
            int idRemetente = usuariosDAO.buscarPorNome(remetente).getId();

            notificacaoDAO.inserirNotificacao(new Notificacao(msg, idRemetente, idDestinatario));
            resultado.adicionarResultado(null);


    }
}
