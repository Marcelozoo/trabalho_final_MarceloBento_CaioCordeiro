package dao;

import excecoes.BancoDeDadosException;
import excecoes.enums.MensagensErroBanco;
import factory.ConexaoFactory;
import models.Notificacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificacaoDAOSQLite implements NotificacaoDAO{

    private Connection conexao;

    public NotificacaoDAOSQLite() {

    }

    @Override
    public void inserirNotificacao(Notificacao notificacao) {
        String sql = "INSERT INTO notificacoes (destinatario_id, remetente_id, conteudo) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, notificacao.getDestinatarioId());
            stmt.setInt(2, notificacao.getRemetenteId());
            stmt.setString(3, notificacao.getConteudo());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new BancoDeDadosException(MensagensErroBanco.FALHA_INSERIR_NOTIFICACOES.getTexto(), e);
        }


    }

    @Override
    public Notificacao buscarNotificacao(int id){
        String sql = "SELECT * FROM notificacoes WHERE id = ?";
        Notificacao notificacao = null;

        try (Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                notificacao = new Notificacao();

                notificacao.setId(rs.getInt("id"));
                notificacao.setDestinatarioId(rs.getInt("destinatario_id"));
                notificacao.setRemetenteId(rs.getInt("remetente_id"));
                notificacao.setCriadaEm(rs.getString("criada_em"));
                notificacao.setFoiLida(rs.getBoolean("foi_lida"));
                notificacao.setConteudo(rs.getString("conteudo"));


            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(MensagensErroBanco.FALHA_LISTAR_NOTIFICACOES.getTexto(), e);
        }

        return notificacao;
    }

    @Override
    public List<Notificacao> listarNotificacoes() {
        String sql = "SELECT * FROM notificacoes";
        List<Notificacao> notificacoes = new  ArrayList<>();

        try (Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notificacao notificacao = new Notificacao();

                notificacao.setId(rs.getInt("id"));
                notificacao.setDestinatarioId(rs.getInt("destinatario_id"));
                notificacao.setRemetenteId(rs.getInt("remetente_id"));
                notificacao.setCriadaEm(rs.getString("criada_em"));
                notificacao.setFoiLida(rs.getBoolean("foi_lida"));
                notificacao.setConteudo(rs.getString("conteudo"));

                notificacoes.add(notificacao);


            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(MensagensErroBanco.FALHA_LISTAR_NOTIFICACOES.getTexto(), e);
        }


        return notificacoes;
    }

    @Override
    public List<Notificacao> listarNotificacoesPorDestinatario(int destinatarioId) {
        String sql = "SELECT * FROM notificacoes WHERE destinatario_id = ? ORDER BY id DESC";
        List<Notificacao> notificacoes = new ArrayList<>();

        try (Connection conexao = ConexaoFactory.criarConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, destinatarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Notificacao notificacao = new Notificacao();
                    notificacao.setId(rs.getInt("id"));
                    notificacao.setDestinatarioId(rs.getInt("destinatario_id"));
                    notificacao.setRemetenteId(rs.getInt("remetente_id"));
                    notificacao.setCriadaEm(rs.getString("criada_em"));
                    notificacao.setFoiLida(rs.getBoolean("foi_lida"));
                    notificacao.setConteudo(rs.getString("conteudo"));
                    notificacoes.add(notificacao);
                }
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(MensagensErroBanco.FALHA_LISTAR_NOTIFICACOES_DE_UM_USUARIO.getTexto(), e);
        }

        return notificacoes;
    }

    @Override
    public void marcarComoLida(int notificacaoId) {
        String sql = "UPDATE notificacoes SET foi_lida = TRUE WHERE id = ? ";

        try (Connection conexao = ConexaoFactory.criarConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, notificacaoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BancoDeDadosException(MensagensErroBanco.FALHA_ATUALIZAR_NOTIFICACAO.getTexto(), e);
        }
    }

    @Override
    public int buscarQtdNotificacoesLidas(int id){
        int qtd = 0;

        String sql = "SELECT COUNT(*) as qtd FROM notificacoes WHERE destinatario_id = ? AND foi_lida = TRUE";
        try (Connection conexao = ConexaoFactory.criarConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    qtd = rs.getInt("qtd");
                }
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(MensagensErroBanco.FALHA_BUSCAR_QTD_NOTIFICACOES_LIDAS.getTexto(), e);
        }


        return qtd;
    }


    @Override
    public int buscarQtdNotificacoesNLidas(int idUsuario){
        int qtd = 0;

        String sql = "SELECT COUNT(*) as qtd FROM notificacoes WHERE destinatario_id = ? AND foi_lida = FALSE";
        try (Connection conexao = ConexaoFactory.criarConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    qtd = rs.getInt("qtd");
                }
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(MensagensErroBanco.FALHA_BUSCAR_QTD_NOTIFICACOES_N_LIDAS.getTexto(), e);
        }


        return qtd;
    }
    @Override
    public int buscarQtdNotificacoesEnviadas(int idUsuario){

        int qtd = 0;

        String sql = "SELECT COUNT(*) as qtd FROM notificacoes WHERE remetente_id = ?";
        try (Connection conexao = ConexaoFactory.criarConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    qtd = rs.getInt("qtd");
                }
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(MensagensErroBanco.FALHA_BUSCAR_QTD_NOTIFICACOES_ENVIDAS.getTexto(), e);
        }




        return qtd;
    }

}
