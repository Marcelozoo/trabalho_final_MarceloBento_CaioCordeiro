package dao;

import excecoes.FalhaConexaoException;
import excecoes.OperacaoUsuarioDAOException;
import excecoes.enums.MensagensErroBanco;
import excecoes.enums.MensagensUsuario;
import factory.ConexaoFactory;
import models.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class UsuariosDAOTest {

    private static final String PROPRIEDADE_URL = "database.url";
    private static final String URL_BANCO_DE_TESTES = "jdbc:sqlite:banco/meuBancoTest.db";

    private UsuariosDAO usuariosDAO;
    private String urlBancoAnterior;

    @Before
    public void prepararBancoDeTestes() throws SQLException {
        urlBancoAnterior = System.getProperty(PROPRIEDADE_URL);
        System.setProperty(PROPRIEDADE_URL, URL_BANCO_DE_TESTES);
        usuariosDAO = new UsuarioDAOSQLite();
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
    public void inserirDevePersistirTodosOsCamposDoUsuario() {
        Usuario usuario = novoUsuario("Maria Souza", "maria@exemplo.com", "hash-maria", true, true, "JSON");

        usuariosDAO.inserir(usuario);

        Usuario persistido = usuariosDAO.buscarPorEmail("maria@exemplo.com");
        assertNotNull(persistido);
        assertTrue(persistido.getId() > 0);
        assertEquals("Maria Souza", persistido.getNome());
        assertEquals("maria@exemplo.com", persistido.getEmail());
        assertEquals("hash-maria", persistido.getSenha());
        assertTrue(persistido.getIsAdmin());
        assertTrue(persistido.getIsAutenticado());
        assertEquals("JSON", persistido.getPreferenciaLog());
        assertNotNull(persistido.getCriadoEm());
    }

    @Test
    public void inserirDeveRejeitarEmailDuplicadoSemCriarOutroUsuario() {
        usuariosDAO.inserir(novoUsuario("Primeiro", "repetido@exemplo.com", "hash-1", false, true, "JSON"));
        Usuario duplicado = novoUsuario("Segundo", "repetido@exemplo.com", "hash-2", true, true, "CSV");

        OperacaoUsuarioDAOException erro = assertThrows(
                OperacaoUsuarioDAOException.class,
                () -> usuariosDAO.inserir(duplicado)
        );

        assertEquals(erro.getMessage(), MensagensErroBanco.FALHA_INSERIR_USUARIO.getTexto());
        assertTrue(erro.getCause() instanceof SQLException);
        assertEquals(1, usuariosDAO.listarTodos().size());
    }

    @Test
    public void inserirDevePropagarFalhaDeConexaoSemDuploEncadeamento() {
        System.setProperty(PROPRIEDADE_URL, "jdbc:sqlite:/diretorio-inexistente/banco.db");

        FalhaConexaoException erro = assertThrows(
                FalhaConexaoException.class,
                () -> usuariosDAO.inserir(novoUsuario(
                        "Usuário sem conexão",
                        "sem-conexao@exemplo.com",
                        "hash",
                        false,
                        false,
                        "JSON"
                ))
        );

        assertEquals(erro.getMessage(), MensagensErroBanco.FALHA_CONEXAO.getTexto());
        assertTrue(erro.getCause() instanceof SQLException);
        assertFalse(erro.getCause() instanceof FalhaConexaoException);
    }


    @Test
    public void atualizarDeveAlterarTodosOsCamposDoUsuarioExistente() {
        usuariosDAO.inserir(novoUsuario("Nome antigo", "antigo@exemplo.com", "hash-antigo", false, false, "JSON"));
        Usuario usuario = usuariosDAO.buscarPorEmail("antigo@exemplo.com");
        assertNotNull(usuario);
        String dataDeCriacaoOriginal = usuario.getCriadoEm();

        usuario.setNome("Nome atualizado");
        usuario.setEmail("atualizado@exemplo.com");
        usuario.setSenha("hash-atualizado");
        usuario.setPreferenciaLog("CSV");
        usuario.setIsAdmin(true);
        usuario.setAutenticacao(true);
        usuariosDAO.atualizar(usuario);

        Usuario atualizado = usuariosDAO.buscarPorId(usuario.getId());
        assertNotNull(atualizado);
        assertEquals(usuario.getId(), atualizado.getId());
        assertEquals("Nome atualizado", atualizado.getNome());
        assertEquals("atualizado@exemplo.com", atualizado.getEmail());
        assertEquals("hash-atualizado", atualizado.getSenha());
        assertEquals("CSV", atualizado.getPreferenciaLog());
        assertTrue(atualizado.getIsAdmin());
        assertTrue(atualizado.getIsAutenticado());
        assertEquals(dataDeCriacaoOriginal, atualizado.getCriadoEm());
        assertNull(usuariosDAO.buscarPorEmail("antigo@exemplo.com"));
    }


    @Test
    public void excluirDeveRemoverOUsuarioInformado() {
        usuariosDAO.inserir(novoUsuario("Usuario removido", "remover@exemplo.com", "hash", false, false, "JSON"));
        Usuario usuario = usuariosDAO.buscarPorEmail("remover@exemplo.com");
        assertNotNull(usuario);

        usuariosDAO.excluir(usuario.getId());

        assertNull(usuariosDAO.buscarPorId(usuario.getId()));
        assertNull(usuariosDAO.buscarPorEmail("remover@exemplo.com"));
        assertTrue(usuariosDAO.buscarPorNome("Usuario removido").isEmpty());
        assertTrue(usuariosDAO.listarTodos().isEmpty());
    }

    @Test
    public void buscarPorIdDeveRetornarUsuarioEncontradoOuNull() {
        usuariosDAO.inserir(novoUsuario("Usuario por id", "por-id@exemplo.com", "hash-id", true, true, "CSV"));
        Usuario usuario = usuariosDAO.buscarPorEmail("por-id@exemplo.com");
        assertNotNull(usuario);

        Usuario encontrado = usuariosDAO.buscarPorId(usuario.getId());

        assertNotNull(encontrado);
        assertEquals(usuario.getId(), encontrado.getId());
        assertEquals("Usuario por id", encontrado.getNome());
        assertEquals("por-id@exemplo.com", encontrado.getEmail());
        assertEquals("hash-id", encontrado.getSenha());
        assertTrue(encontrado.getIsAdmin());
        assertTrue(encontrado.getIsAutenticado());
        assertEquals("CSV", encontrado.getPreferenciaLog());
        assertNotNull(encontrado.getCriadoEm());
        assertNull(usuariosDAO.buscarPorId(999999));
    }

    @Test
    public void buscarPorEmailDeveRetornarUsuarioEncontradoOuNull() {
        usuariosDAO.inserir(novoUsuario("Usuario por email", "email@exemplo.com", "hash-email", false, true, "JSON"));

        Usuario encontrado = usuariosDAO.buscarPorEmail("email@exemplo.com");

        assertNotNull(encontrado);
        assertEquals("Usuario por email", encontrado.getNome());
        assertEquals("email@exemplo.com", encontrado.getEmail());
        assertEquals("hash-email", encontrado.getSenha());
        assertFalse(encontrado.getIsAdmin());
        assertTrue(encontrado.getIsAutenticado());
        assertEquals("JSON", encontrado.getPreferenciaLog());
        assertNotNull(encontrado.getCriadoEm());
        assertNull(usuariosDAO.buscarPorEmail("inexistente@exemplo.com"));
    }

    @Test public void buscarPorStringVaziaDeveRetornarTodosUsuariosDoBanco(){
        usuariosDAO.inserir(novoUsuario("Maria Souza", "maria@exemplo.com", "hash-maria", true, false, "JSON"));
        usuariosDAO.inserir(novoUsuario("Marcos Lima", "marcos@exemplo.com", "hash-marcos", false, true, "CSV"));
        usuariosDAO.inserir(novoUsuario("Marta Oliveira", "marta@exemplo.com", "hash-marta", false, false, "JSON"));
        usuariosDAO.inserir(novoUsuario("Amanda Reis", "amanda@exemplo.com", "hash-amanda", true, true, "CSV"));
        usuariosDAO.inserir(novoUsuario("Carla Marques", "carla@exemplo.com", "hash-carla", false, false, "JSON"));

        assertEquals(5, usuariosDAO.buscarPorNome("").size());
    }
    @Test
    public void buscarPorNomeDeveRetornarTodosOsUsuariosComOPrefixoInformado() {
        usuariosDAO.inserir(novoUsuario("Maria Souza", "maria@exemplo.com", "hash-maria", true, false, "JSON"));
        usuariosDAO.inserir(novoUsuario("Marcos Lima", "marcos@exemplo.com", "hash-marcos", false, true, "CSV"));
        usuariosDAO.inserir(novoUsuario("Marta Oliveira", "marta@exemplo.com", "hash-marta", false, false, "JSON"));
        usuariosDAO.inserir(novoUsuario("Amanda Reis", "amanda@exemplo.com", "hash-amanda", true, true, "CSV"));
        usuariosDAO.inserir(novoUsuario("Carla Marques", "carla@exemplo.com", "hash-carla", false, false, "JSON"));

        List<Usuario> encontrados = usuariosDAO.buscarPorNome("mar");

        assertEquals(3, encontrados.size());
        Set<String> nomes = new HashSet<>();
        for (Usuario usuario : encontrados) {
            nomes.add(usuario.getNome());
            assertNotNull(usuario.getEmail());
            assertNotNull(usuario.getSenha());
            assertNotNull(usuario.getCriadoEm());
        }
        assertTrue(nomes.contains("Maria Souza"));
        assertTrue(nomes.contains("Marcos Lima"));
        assertTrue(nomes.contains("Marta Oliveira"));
    }

    @Test
    public void buscarPorNomeDeveRetornarListaVaziaQuandoNaoHouverCorrespondencia() {
        usuariosDAO.inserir(novoUsuario("João Silva", "joao@exemplo.com", "hash-joao", false, false, "JSON"));

        List<Usuario> encontrados = usuariosDAO.buscarPorNome("Mar");

        assertTrue(encontrados.isEmpty());
    }

    @Test
    public void listarTodosDeveRetornarTodosOsUsuariosDoBanco() {
        assertTrue(usuariosDAO.listarTodos().isEmpty());

        usuariosDAO.inserir(novoUsuario("Usuario A", "a@exemplo.com", "hash-a", false, false, "JSON"));
        usuariosDAO.inserir(novoUsuario("Usuario B", "b@exemplo.com", "hash-b", true, true, "CSV"));

        List<Usuario> usuarios = usuariosDAO.listarTodos();

        assertEquals(2, usuarios.size());
        Set<String> emails = new HashSet<>();
        for (Usuario usuario : usuarios) {
            emails.add(usuario.getEmail());
            assertNotNull(usuario.getCriadoEm());
        }
        assertTrue(emails.contains("a@exemplo.com"));
        assertTrue(emails.contains("b@exemplo.com"));
    }

    private Usuario novoUsuario(
            String nome,
            String email,
            String senha,
            boolean administrador,
            boolean autenticado,
            String preferenciaLog
    ) {
        Usuario usuario = new Usuario(nome, senha, administrador, autenticado);
        usuario.setEmail(email);
        usuario.setPreferenciaLog(preferenciaLog);
        return usuario;
    }

    private void limparBanco() throws SQLException {
        try (Connection conexao = ConexaoFactory.criarConexao();
             Statement statement = conexao.createStatement()) {
            statement.execute("PRAGMA foreign_keys = OFF");
            statement.executeUpdate("DELETE FROM notificacoes");
            statement.executeUpdate("DELETE FROM usuarios");
            statement.executeUpdate("DELETE FROM sqlite_sequence WHERE name = 'usuarios'");
        }
    }
}
