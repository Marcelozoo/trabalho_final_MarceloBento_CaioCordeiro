package services;

import dao.NotificacaoDAO;
import dao.UsuariosDAO;
import excecoes.enums.MensagensNotificacao;
import models.Notificacao;
import models.ResultadoOperacao;
import models.Usuario;

import java.util.List;

public class NotificacaoService {

    private final NotificacaoDAO notificacaoDAO;
    private final UsuariosDAO usuariosDAO;

    public NotificacaoService(NotificacaoDAO notificacaoDAO, UsuariosDAO usuariosDAO) {
        this.notificacaoDAO = notificacaoDAO;
        this.usuariosDAO = usuariosDAO;
    }



    public ResultadoOperacao<List<Notificacao>> listarPorUsuario(int usuarioId) {
        ResultadoOperacao<List<Notificacao>> resultado = new ResultadoOperacao<>();

        if (usuarioId <= 0) {
            resultado.adicionarErro(MensagensNotificacao.USUARIO_INVALIDO.getMensagem());
            resultado.adicionarResultado(null);
            return resultado;
        }

        List<Notificacao> notificacoes = notificacaoDAO.listarNotificacoesPorDestinatario(usuarioId);
        for (Notificacao notificacao : notificacoes) {
            Usuario remetente = usuariosDAO.buscarPorId(notificacao.getRemetenteId());
            notificacao.setRemetenteNome(remetente == null ? "Desconhecido" : remetente.getNome());
        }

        resultado.adicionarResultado(notificacoes);
        return resultado;
    }

    public ResultadoOperacao<Void> marcarComoLida(int notificacaoId, int usuarioId) {
        ResultadoOperacao<Void> resultado = new ResultadoOperacao<>();

        if (notificacaoId <= 0 || usuarioId <= 0) {
            resultado.adicionarErro(MensagensNotificacao.NOTIFICACAO_OU_USUARIO_INVALIDO.getMensagem());
            return resultado;
        }

        notificacaoDAO.marcarComoLida(notificacaoId, usuarioId);
        resultado.adicionarResultado(null);
        return resultado;
    }
}
