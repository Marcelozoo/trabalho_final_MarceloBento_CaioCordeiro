package services;

import dao.NotificacaoDAO;
import dao.UsuariosDAO;
import excecoes.BancoDeDadosException;
import excecoes.enums.MensagensNotificacao;
import models.Notificacao;
import models.ResultadoOperacao;
import models.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class NotificacaoService {

    private final NotificacaoDAO notificacaoDAO;
    private final UsuariosDAO usuariosDAO;

    public NotificacaoService(NotificacaoDAO notificacaoDAO, UsuariosDAO usuariosDAO) {
        this.notificacaoDAO = notificacaoDAO;
        this.usuariosDAO = usuariosDAO;
    }


    public ResultadoOperacao<List<Notificacao>> listarNotificacoesNLidas(int idUsuario){
        ResultadoOperacao<List<Notificacao>> resultado = new ResultadoOperacao<>();
        try{
            if (idUsuario <= 0) {
                resultado.adicionarErro(MensagensNotificacao.USUARIO_INVALIDO.getMensagem());
                resultado.adicionarResultado(null);
                return resultado;
            }

            List<Notificacao> notificacoes = notificacaoDAO.listarNotificacoesPorDestinatario(idUsuario);
            List<Notificacao> notificacoesNLidas =  notificacoes.stream().filter(e -> !e.getFoiLida() ).collect(Collectors.toList());

            for (Notificacao notificacao : notificacoesNLidas){
                notificacao.setRemetenteNome(usuariosDAO.buscarPorId(notificacao.getRemetenteId()).getNome());
            }

            resultado.adicionarResultado(notificacoesNLidas);
        }catch (BancoDeDadosException e){
            resultado.adicionarErro(e.getMessage());
        }
        return resultado;
    }


    public ResultadoOperacao<List<Notificacao>> listarPorUsuario(int usuarioId) {
        ResultadoOperacao<List<Notificacao>> resultado = new ResultadoOperacao<>();
        try {
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
        }catch (BancoDeDadosException e){
            resultado.adicionarErro(e.getMessage());
        }
        return resultado;
    }

    public ResultadoOperacao<Void> marcarComoLida(int notificacaoId, int usuarioId) {
        ResultadoOperacao<Void> resultado = new ResultadoOperacao<>();
        try {
            if (notificacaoId <= 0) {
                resultado.adicionarErro(MensagensNotificacao.NOTIFICACAO_OU_USUARIO_INVALIDO.getMensagem());
                return resultado;
            }

            notificacaoDAO.marcarComoLida(notificacaoId);
            resultado.adicionarResultado(null);
        }catch (BancoDeDadosException e){
            resultado.adicionarErro(e.getMessage());
        }
        return resultado;
    }

    public ResultadoOperacao<List<Notificacao>> listarNotificacoesLidas(int usuarioId) {

        ResultadoOperacao<List<Notificacao>> resultado = new ResultadoOperacao<>();
        try{
            if (usuarioId <= 0) {
                resultado.adicionarErro(MensagensNotificacao.USUARIO_INVALIDO.getMensagem());
                resultado.adicionarResultado(null);
                return resultado;
            }

            List<Notificacao> notificacoes = notificacaoDAO.listarNotificacoesPorDestinatario(usuarioId);
            List<Notificacao> notificacoesLidas =  notificacoes.stream().filter(e -> e.getFoiLida() ).collect(Collectors.toList());

            for (Notificacao notificacao : notificacoesLidas){
                notificacao.setRemetenteNome(usuariosDAO.buscarPorId(notificacao.getRemetenteId()).getNome());

            }

            resultado.adicionarResultado(notificacoesLidas);
        }catch (BancoDeDadosException e){
            resultado.adicionarErro(e.getMessage());
        }
        return resultado;
    }

    public ResultadoOperacao<Integer> buscarQtdNotificacoesNLidas(int id){
        ResultadoOperacao<Integer> resultado = new ResultadoOperacao<>();

        try{
            if (id <= 0) {
                resultado.adicionarErro(MensagensNotificacao.USUARIO_INVALIDO.getMensagem());
                return resultado;
            }

            int qtd = notificacaoDAO.buscarQtdNotificacoesNLidas(id);

            resultado.adicionarResultado(qtd);
        }catch (BancoDeDadosException e){
            resultado.adicionarErro(e.getMessage());
        }
        return resultado;
    }
}
