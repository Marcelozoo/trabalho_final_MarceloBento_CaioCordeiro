package services;

import dao.NotificacaoDAO;
import dao.UsuariosDAO;
import excecoes.enums.MensagensNotificacao;
import models.Notificacao;
import models.ResultadoOperacao;
import models.Usuario;

public class EnviarNotificacaoService {

    private final UsuariosDAO usuariosDAO;
    private final NotificacaoDAO notificacaoDAO;

    public EnviarNotificacaoService(UsuariosDAO usuariosDAO, NotificacaoDAO notificacaoDAO) {
        this.usuariosDAO = usuariosDAO;
        this.notificacaoDAO = notificacaoDAO;
    }

    public void enviar(
            String destino,
            String remetente,
            String msg,
            ResultadoOperacao<Void> resultado
    ) {
        if (destino == null || remetente == null || msg == null
                || destino.isEmpty() || remetente.isEmpty() || msg.isEmpty()) {
            resultado.adicionarErro(MensagensNotificacao.CAMPOS_INVALIDOS.getMensagem());
            return;
        }

        Usuario destinatarioUsuario = usuariosDAO.buscarPorEmail(destino);
        Usuario remetenteUsuario = usuariosDAO.buscarPorEmail(remetente);
        if (destinatarioUsuario == null || remetenteUsuario == null) {
            resultado.adicionarErro(MensagensNotificacao.USUARIOS_NAO_ENCONTRADOS.getMensagem());
            return;
        }

        notificacaoDAO.inserirNotificacao(new Notificacao(
                msg,
                remetenteUsuario.getId(),
                destinatarioUsuario.getId()
        ));
        resultado.adicionarResultado(null);
    }
}
