package dao;

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
            System.out.println("Erro ao inserir notificacao");
        }


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
            System.out.println("Erro ao listar notificacoes");
        }


        return notificacoes;
    }

    @Override
    public void lerNotificacao(Notificacao notificacao) {
        String sql = "UPDATE notificacoes SET foi_lida = ? WHERE id = ?";


        try (Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setBoolean(1, notificacao.getFoiLida());
            stmt.setInt(2, notificacao.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao ler notificacao");
        }
    }
}
