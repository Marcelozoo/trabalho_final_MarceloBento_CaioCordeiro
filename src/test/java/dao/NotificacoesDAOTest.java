package dao;

import factory.ConexaoFactory;
import models.Notificacao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class NotificacoesDAOTest {

    private static final String PROPRIEDADE_URL = "database.url";
    private static final String URL_BANCO_DE_TESTES = "jdbc:sqlite:banco/meuBancoTest.db";

    private NotificacaoDAO notificacoesDAO;
    private String urlBancoAnterior;

    @Before
    public void prepararBancoDeTestes() throws SQLException {
        urlBancoAnterior = System.getProperty(PROPRIEDADE_URL);
        System.setProperty(PROPRIEDADE_URL, URL_BANCO_DE_TESTES);
        notificacoesDAO = new NotificacaoDAOSQLite();
        limparBanco();
    }

    @After
    public void restaurarConfiguracaoDoBanco() throws SQLException {
        try {
            System.setProperty(PROPRIEDADE_URL, URL_BANCO_DE_TESTES);
            limparBanco();
        } finally {
            if (urlBancoAnterior == null) {
                System.clearProperty(PROPRIEDADE_URL);
            } else {
                System.setProperty(PROPRIEDADE_URL, urlBancoAnterior);
            }
        }
    }


    @Test
    public void inserirNotificacoesNoBancoDevePersistir(){
        Notificacao nova = new Notificacao("conteudo da msg", 1, 2, "algum");

        notificacoesDAO.inserirNotificacao(nova);


        System.out.println(notificacoesDAO.listarNotificacoes().get(0));

        Notificacao persistido = notificacoesDAO.buscarNotificacao(1);

        assertNotNull(persistido);
        assertTrue(persistido.getId() > 0);
        assertEquals("conteudo da msg", persistido.getConteudo());
        assertEquals(1, persistido.getRemetenteId());
        assertEquals(2, persistido.getDestinatarioId());
        assertNotNull(persistido.getCriadaEm());

     }

     @Test
     public void listarDeveLsitarTodasAsNotificacoesDoBanco(){
         Notificacao nova = new Notificacao("conteudo da msg", 1, 2, "Alguum");
         Notificacao nova2 = new Notificacao("conteudo da msg 2", 2, 3, "Algum2");
         Notificacao nova3 = new Notificacao("conteudo da msg 3", 5, 4, "algum3");

         notificacoesDAO.inserirNotificacao(nova);
         notificacoesDAO.inserirNotificacao(nova2);
         notificacoesDAO.inserirNotificacao(nova3);


         int qtd = notificacoesDAO.listarNotificacoes().size();

         assertEquals(3, qtd);

     }

     @Test
     public void listarPorDestinatarioDeveRetornaraTodasAsNotificacoesDoDestinatario(){
         Notificacao nova = new Notificacao("conteudo da msg", 1, 3, "Alguum");
         Notificacao nova2 = new Notificacao("conteudo da msg 2", 2, 3, "Algum2");
         Notificacao nova3 = new Notificacao("conteudo da msg 3", 5, 4, "algum3");

         notificacoesDAO.inserirNotificacao(nova);
         notificacoesDAO.inserirNotificacao(nova2);
         notificacoesDAO.inserirNotificacao(nova3);

         List<Notificacao> notificacoes = notificacoesDAO.listarNotificacoesPorDestinatario(3);

         System.out.println(notificacoesDAO.listarNotificacoes().get(0));

        assertEquals(2, notificacoes.size());
        assertEquals(2, notificacoes.get(0).getId());
        assertEquals(1, notificacoes.get(1).getId());

     }
     @Test public void marcarComoLidaDevePersistirAlteracaoNoBanco(){
         Notificacao nova = new Notificacao("conteudo da msg", 1, 3, "Algo");
         notificacoesDAO.inserirNotificacao(nova);
         assertFalse(notificacoesDAO.buscarNotificacao(1).getFoiLida());

         notificacoesDAO.marcarComoLida(1);
         assertTrue(notificacoesDAO.buscarNotificacao(1).getFoiLida());


     }



    private void limparBanco() throws SQLException {
        try (Connection conexao = ConexaoFactory.criarConexao();
             Statement statement = conexao.createStatement()) {
            statement.execute("PRAGMA foreign_keys = OFF");
            statement.executeUpdate("DELETE FROM notificacoes");
            statement.executeUpdate("DELETE FROM usuarios");
            statement.executeUpdate("DELETE FROM sqlite_sequence WHERE name IN ('usuarios','notificacoes') ");
        }
    }

}
