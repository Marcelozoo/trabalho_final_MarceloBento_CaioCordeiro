package services;

import dao.UsuariosDAO;
import excecoes.OperacaoUsuarioDAOException;
import excecoes.enums.MensagensUsuario;
import models.ResultadoOperacao;
import models.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest {

    private static final String EMAIL = "maria@exemplo.com";
    private static final String SENHA = "Vx7#mQ2!pL";
    private static final String SENHA_DIFERENTE = "Ot8#Sx3!Km";
    private static final String FALHA_BANCO = "Falha simulada no banco.";

    private UsuariosDAO usuarioDAO;
    private UsuarioService usuarioService;

    @Before
    public void setUp() {
        usuarioDAO = mock(UsuariosDAO.class);
        usuarioService = new UsuarioService(usuarioDAO);
    }

    // autenticarLogin

    @Test
    public void autenticarLoginDeveRetornarUsuarioQuandoAsCredenciaisSaoValidas() {
        Usuario usuario = novoUsuario(1, "Maria Souza", EMAIL, true, true);
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(usuario);

        ResultadoOperacao<Usuario> resultado = usuarioService.autenticarLogin(EMAIL, SENHA);

        assertTrue(resultado.eValido());
        assertTrue(resultado.getErros().isEmpty());
        assertSame(usuario, resultado.getResultado());
        verify(usuarioDAO).buscarPorEmail(EMAIL);
    }

    @Test
    public void autenticarLoginDeveRejeitarEmailNuloSemAcessarODao() {
        ResultadoOperacao<Usuario> resultado = usuarioService.autenticarLogin(null, SENHA);

        assertFalha(resultado, MensagensUsuario.CAMPOS_OBRIGATORIOS.getMensagem());
        verifyNoInteractions(usuarioDAO);
    }

    @Test
    public void autenticarLoginDeveRejeitarSenhaEmBrancoSemAcessarODao() {
        ResultadoOperacao<Usuario> resultado = usuarioService.autenticarLogin(EMAIL, "   ");

        assertFalha(resultado, MensagensUsuario.CAMPOS_OBRIGATORIOS.getMensagem());
        verifyNoInteractions(usuarioDAO);
    }

    @Test
    public void autenticarLoginDeveRejeitarEmailNaoEncontrado() {
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(null);

        ResultadoOperacao<Usuario> resultado = usuarioService.autenticarLogin(EMAIL, SENHA);

        assertFalha(resultado, MensagensUsuario.CREDENCIAIS_INVALIDAS.getMensagem());
        assertNull(resultado.getResultado());
    }

    @Test
    public void autenticarLoginDeveRejeitarSenhaIncorreta() {
        Usuario usuario = novoUsuario(1, "Maria Souza", EMAIL, false, true);
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(usuario);

        ResultadoOperacao<Usuario> resultado = usuarioService.autenticarLogin(EMAIL, SENHA_DIFERENTE);

        assertFalha(resultado, MensagensUsuario.CREDENCIAIS_INVALIDAS.getMensagem());
        assertNull(resultado.getResultado());
    }

    @Test
    public void autenticarLoginDeveRejeitarUsuarioNaoAutorizado() {
        Usuario usuario = novoUsuario(1, "Maria Souza", EMAIL, false, false);
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(usuario);

        ResultadoOperacao<Usuario> resultado = usuarioService.autenticarLogin(EMAIL, SENHA);

        assertFalha(resultado, MensagensUsuario.USUARIO_NAO_AUTORIZADO.getMensagem());
        assertNull(resultado.getResultado());
    }

    @Test
    public void autenticarLoginDeveConverterFalhaDoDaoEmResultadoComErro() {
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenThrow(erroBanco());

        ResultadoOperacao<Usuario> resultado = usuarioService.autenticarLogin(EMAIL, SENHA);

        assertFalha(resultado, FALHA_BANCO);
        assertNull(resultado.getResultado());
    }

    // autenticarUsuario

    @Test
    public void autenticarUsuarioDeveAutorizarEPersistirOUsuario() {
        Usuario usuario = novoUsuario(7, "Maria Souza", EMAIL, false, false);

        ResultadoOperacao<Void> resultado = usuarioService.autenticarUsuario(usuario);

        assertTrue(resultado.eValido());
        assertTrue(usuario.getIsAutenticado());
        verify(usuarioDAO).atualizar(usuario);
    }

    @Test
    public void autenticarUsuarioDeveRejeitarUsuarioNuloSemAcessarODao() {
        ResultadoOperacao<Void> resultado = usuarioService.autenticarUsuario(null);

        assertFalha(resultado, MensagensUsuario.USUARIO_NULO.getMensagem());
        verifyNoInteractions(usuarioDAO);
    }

    @Test
    public void autenticarUsuarioDeveRejeitarUsuarioJaAutorizadoSemAtualizarODao() {
        Usuario usuario = novoUsuario(7, "Maria Souza", EMAIL, true, true);

        ResultadoOperacao<Void> resultado = usuarioService.autenticarUsuario(usuario);

        assertFalha(resultado, MensagensUsuario.USUARIO_JA_AUTORIZADO.getMensagem());
        assertTrue(usuario.getIsAutenticado());
        verify(usuarioDAO, never()).atualizar(usuario);
    }

    @Test
    public void autenticarUsuarioDeveConverterFalhaDeAtualizacaoEmResultadoComErro() {
        Usuario usuario = novoUsuario(7, "Maria Souza", EMAIL, false, false);
        doThrow(erroBanco()).when(usuarioDAO).atualizar(usuario);

        ResultadoOperacao<Void> resultado = usuarioService.autenticarUsuario(usuario);

        assertFalha(resultado, FALHA_BANCO);
        assertFalse(usuario.getIsAutenticado());
        verify(usuarioDAO).atualizar(usuario);
    }

    // buscar

    @Test
    public void buscarDeveDelegarEObterUsuariosEncontrados() {
        Usuario primeiro = novoUsuario(1, "Maria Souza", EMAIL, false, true);
        Usuario segundo = novoUsuario(2, "Marcos Lima", "marcos@exemplo.com", true, true);
        List<Usuario> usuarios = Arrays.asList(primeiro, segundo);
        when(usuarioDAO.buscarPorNome("Mar")).thenReturn(usuarios);

        ResultadoOperacao<List<Usuario>> resultado = usuarioService.buscar("Mar");

        assertTrue(resultado.eValido());
        assertSame(usuarios, resultado.getResultado());
        verify(usuarioDAO).buscarPorNome("Mar");
    }

    @Test
    public void buscarDeveRetornarListaVaziaQuandoNaoHouverCorrespondencia() {
        when(usuarioDAO.buscarPorNome("Z")).thenReturn(Collections.emptyList());

        ResultadoOperacao<List<Usuario>> resultado = usuarioService.buscar("Z");

        assertTrue(resultado.eValido());
        assertNotNull(resultado.getResultado());
        assertTrue(resultado.getResultado().isEmpty());
    }

    @Test
    public void buscarDeveRejeitarNomeNuloSemAcessarODao() {
        ResultadoOperacao<List<Usuario>> resultado = usuarioService.buscar(null);

        assertFalha(resultado, MensagensUsuario.CAMPOS_OBRIGATORIOS.getMensagem());
        assertNull(resultado.getResultado());
        verifyNoInteractions(usuarioDAO);
    }


    @Test
    public void buscarDeveConverterFalhaDoDaoEmResultadoComErro() {
        when(usuarioDAO.buscarPorNome("Maria")).thenThrow(erroBanco());

        ResultadoOperacao<List<Usuario>> resultado = usuarioService.buscar("Maria");

        assertFalha(resultado, FALHA_BANCO);
        assertNull(resultado.getResultado());
    }

    // cadastrarUsuario

    @Test
    public void cadastrarUsuarioDeveTornarOPrimeiroUsuarioAdminEAutorizado() {
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(null);
        when(usuarioDAO.listarTodos()).thenReturn(Collections.emptyList());

        ResultadoOperacao<Usuario> resultado = usuarioService.cadastrarUsuario(
                "Maria Souza", EMAIL, SENHA, SENHA, false, false
        );

        assertTrue(resultado.eValido());
        Usuario cadastrado = resultado.getResultado();
        assertNotNull(cadastrado);
        assertEquals("Maria Souza", cadastrado.getNome());
        assertEquals(EMAIL, cadastrado.getEmail());
        assertTrue(cadastrado.getIsAdmin());
        assertTrue(cadastrado.getIsAutenticado());
        assertNotEquals(SENHA, cadastrado.getSenha());
        assertTrue(BCrypt.checkpw(SENHA, cadastrado.getSenha()));
        verify(usuarioDAO).inserir(cadastrado);
    }

    @Test
    public void cadastrarUsuarioDeveRespeitarPermissoesDosUsuariosSubsequentes() {
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(null);
        when(usuarioDAO.listarTodos()).thenReturn(Collections.singletonList(
                novoUsuario(1, "Administrador", "admin@exemplo.com", true, true)
        ));

        ResultadoOperacao<Usuario> resultado = usuarioService.cadastrarUsuario(
                "Maria Souza", EMAIL, SENHA, SENHA, false, false
        );

        assertTrue(resultado.eValido());
        assertFalse(resultado.getResultado().getIsAdmin());
        assertFalse(resultado.getResultado().getIsAutenticado());
        verify(usuarioDAO).inserir(resultado.getResultado());
    }

    @Test
    public void cadastrarUsuarioDeveRejeitarCamposObrigatoriosNulosSemAcessarODao() {
        ResultadoOperacao<Usuario> resultado = usuarioService.cadastrarUsuario(
                null, EMAIL, SENHA, SENHA, false, false
        );

        assertFalha(resultado, MensagensUsuario.CAMPOS_OBRIGATORIOS.getMensagem());
        assertNull(resultado.getResultado());
        verifyNoInteractions(usuarioDAO);
    }

    @Test
    public void cadastrarUsuarioDeveRejeitarConfirmacaoDeSenhaNula() {
        ResultadoOperacao<Usuario> resultado = usuarioService.cadastrarUsuario(
                "Maria Souza", EMAIL, SENHA, null, false, false
        );

        assertFalha(resultado, MensagensUsuario.CAMPOS_OBRIGATORIOS.getMensagem());
        assertNull(resultado.getResultado());
        verifyNoInteractions(usuarioDAO);
    }

    @Test
    public void cadastrarUsuarioDeveRejeitarEmailJaCadastrado() {
        Usuario usuarioExistente = novoUsuario(1, "Outra pessoa", EMAIL, false, true);
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(usuarioExistente);

        ResultadoOperacao<Usuario> resultado = usuarioService.cadastrarUsuario(
                "Maria Souza", EMAIL, SENHA, SENHA, false, false
        );

        assertFalha(resultado, MensagensUsuario.EMAIL_JA_CADASTRADO.getMensagem());
        assertNull(resultado.getResultado());
        verify(usuarioDAO).buscarPorEmail(EMAIL);
        verify(usuarioDAO, never()).inserir(any(Usuario.class));
        verify(usuarioDAO, never()).listarTodos();
    }

    @Test
    public void cadastrarUsuarioDeveRejeitarSenhaInvalida() {
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(null);

        ResultadoOperacao<Usuario> resultado = usuarioService.cadastrarUsuario(
                "Maria Souza", EMAIL, "123", "123", false, false
        );

        assertFalse(resultado.eValido());
        assertFalse(resultado.getErros().isEmpty());
        assertNull(resultado.getResultado());
        verify(usuarioDAO, never()).inserir(any(Usuario.class));
    }

    @Test
    public void cadastrarUsuarioDeveRejeitarSenhasDiferentes() {
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(null);

        ResultadoOperacao<Usuario> resultado = usuarioService.cadastrarUsuario(
                "Maria Souza", EMAIL, SENHA, SENHA_DIFERENTE, false, false
        );

        assertFalha(resultado, MensagensUsuario.SENHAS_NAO_COINCIDEM.getMensagem());
        assertNull(resultado.getResultado());
        verify(usuarioDAO, never()).inserir(any(Usuario.class));
    }

    @Test
    public void cadastrarUsuarioDeveConverterFalhaDoDaoEmResultadoComErro() {
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenThrow(erroBanco());

        ResultadoOperacao<Usuario> resultado = usuarioService.cadastrarUsuario(
                "Maria Souza", EMAIL, SENHA, SENHA, false, false
        );

        assertFalha(resultado, FALHA_BANCO);
        assertNull(resultado.getResultado());
        verify(usuarioDAO, never()).inserir(any(Usuario.class));
    }

    // excluirUsuario

    @Test
    public void excluirUsuarioDeveDelegarAExclusaoPeloId() {
        ResultadoOperacao<Void> resultado = usuarioService.excluirUsuario(15);

        assertTrue(resultado.eValido());
        assertTrue(resultado.getErros().isEmpty());
        verify(usuarioDAO).excluir(15);
    }

    @Test
    public void excluirUsuarioDeveConverterFalhaDoDaoEmResultadoComErro() {
        doThrow(erroBanco()).when(usuarioDAO).excluir(15);

        ResultadoOperacao<Void> resultado = usuarioService.excluirUsuario(15);

        assertFalha(resultado, FALHA_BANCO);
        verify(usuarioDAO).excluir(15);
    }

    // listar

    @Test
    public void listarDeveAdicionarTodosOsUsuariosNoResultado() {
        List<Usuario> usuarios = Collections.singletonList(
                novoUsuario(1, "Maria Souza", EMAIL, false, true)
        );
        when(usuarioDAO.listarTodos()).thenReturn(usuarios);
        ResultadoOperacao<List<Usuario>> resultado = new ResultadoOperacao<>();

        usuarioService.listar(resultado);

        assertTrue(resultado.eValido());
        assertSame(usuarios, resultado.getResultado());
        verify(usuarioDAO).listarTodos();
    }

    @Test
    public void listarDeveAdicionarErroQuandoODaoFalhar() {
        when(usuarioDAO.listarTodos()).thenThrow(erroBanco());
        ResultadoOperacao<List<Usuario>> resultado = new ResultadoOperacao<>();

        usuarioService.listar(resultado);

        assertFalha(resultado, FALHA_BANCO);
        assertNull(resultado.getResultado());
        verify(usuarioDAO).listarTodos();
    }

    // editarUsuario

    @Test
    public void editarUsuarioDeveAtualizarOsDadosEHashDaSenha() {
        Usuario usuario = novoUsuario(7, "Nome antigo", EMAIL, true, true);
        usuario.setPreferenciaLog("CSV");
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(usuario);
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);

        ResultadoOperacao<Void> resultado = usuarioService.editarUsuario(
                usuario, "Maria Souza", EMAIL, SENHA, SENHA
        );

        assertTrue(resultado.eValido());
        assertEquals(7, usuario.getId());
        assertEquals("Maria Souza", usuario.getNome());
        assertEquals(EMAIL, usuario.getEmail());
        assertEquals("CSV", usuario.getPreferenciaLog());
        assertTrue(usuario.getIsAdmin());
        assertTrue(usuario.getIsAutenticado());
        assertNotEquals(SENHA, usuario.getSenha());
        assertTrue(BCrypt.checkpw(SENHA, usuario.getSenha()));
        verify(usuarioDAO).buscarPorEmail(EMAIL);
        verify(usuarioDAO).atualizar(captor.capture());
        assertSame(usuario, captor.getValue());
    }

    @Test
    public void editarUsuarioDeveRejeitarUsuarioNuloSemAcessarODao() {
        ResultadoOperacao<Void> resultado = usuarioService.editarUsuario(
                null, "Maria Souza", EMAIL, SENHA, SENHA
        );

        assertFalha(resultado, MensagensUsuario.USUARIO_NULO.getMensagem());
        verifyNoInteractions(usuarioDAO);
    }

    @Test
    public void editarUsuarioDeveRejeitarCamposObrigatoriosEmBrancoSemAlterarOUsuario() {
        Usuario usuario = novoUsuario(7, "Nome antigo", EMAIL, true, true);
        String senhaOriginal = usuario.getSenha();

        ResultadoOperacao<Void> resultado = usuarioService.editarUsuario(
                usuario, " ", EMAIL, SENHA, SENHA
        );

        assertFalha(resultado, MensagensUsuario.CAMPOS_OBRIGATORIOS.getMensagem());
        assertEquals("Nome antigo", usuario.getNome());
        assertEquals(senhaOriginal, usuario.getSenha());
        verifyNoInteractions(usuarioDAO);
    }

    @Test
    public void editarUsuarioDeveRejeitarEmailPertencenteAOutroUsuario() {
        Usuario usuario = novoUsuario(7, "Maria Souza", EMAIL, true, true);
        Usuario outraUsuario = novoUsuario(8, "Outra pessoa", "outra@exemplo.com", false, true);
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(outraUsuario);

        ResultadoOperacao<Void> resultado = usuarioService.editarUsuario(
                usuario, "Maria Souza Alterada", EMAIL, SENHA, SENHA
        );

        assertFalha(resultado, MensagensUsuario.EMAIL_JA_CADASTRADO.getMensagem());
        assertEquals("Maria Souza", usuario.getNome());
        verify(usuarioDAO, never()).atualizar(usuario);
    }

    @Test
    public void editarUsuarioDeveRejeitarSenhaInvalidaSemAtualizar() {
        Usuario usuario = novoUsuario(7, "Maria Souza", EMAIL, true, true);
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(usuario);

        ResultadoOperacao<Void> resultado = usuarioService.editarUsuario(
                usuario, "Maria Souza", EMAIL, "123", "123"
        );

        assertFalse(resultado.eValido());
        assertFalse(resultado.getErros().isEmpty());
        verify(usuarioDAO, never()).atualizar(usuario);
    }

    @Test
    public void editarUsuarioDeveRejeitarSenhasDiferentesSemAtualizar() {
        Usuario usuario = novoUsuario(7, "Maria Souza", EMAIL, true, true);
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(usuario);

        ResultadoOperacao<Void> resultado = usuarioService.editarUsuario(
                usuario, "Maria Souza", EMAIL, SENHA, SENHA_DIFERENTE
        );

        assertFalha(resultado, MensagensUsuario.SENHAS_NAO_COINCIDEM.getMensagem());
        verify(usuarioDAO, never()).atualizar(usuario);
    }

    @Test
    public void editarUsuarioDeveConverterFalhaDoDaoEmResultadoComErro() {
        Usuario usuario = novoUsuario(7, "Maria Souza", EMAIL, true, true);
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenThrow(erroBanco());

        ResultadoOperacao<Void> resultado = usuarioService.editarUsuario(
                usuario, "Maria Souza Alterada", EMAIL, SENHA, SENHA
        );

        assertFalha(resultado, FALHA_BANCO);
        verify(usuarioDAO, never()).atualizar(usuario);
    }

    @Test
    public void editarUsuarioDevePreservarOsDadosQuandoATualizacaoFalhar() {
        Usuario usuario = novoUsuario(7, "Nome anterior", EMAIL, true, true);
        String senhaAnterior = usuario.getSenha();
        when(usuarioDAO.buscarPorEmail(EMAIL)).thenReturn(usuario);
        doThrow(erroBanco()).when(usuarioDAO).atualizar(usuario);

        ResultadoOperacao<Void> resultado = usuarioService.editarUsuario(
                usuario, "Nome alterado", EMAIL, SENHA, SENHA
        );

        assertFalha(resultado, FALHA_BANCO);
        assertEquals("Nome anterior", usuario.getNome());
        assertEquals(EMAIL, usuario.getEmail());
        assertEquals(senhaAnterior, usuario.getSenha());
        verify(usuarioDAO).atualizar(usuario);
    }

    private Usuario novoUsuario(
            int id,
            String nome,
            String email,
            boolean administrador,
            boolean autenticado
    ) {
        Usuario usuario = new Usuario(
                nome,
                BCrypt.hashpw(SENHA, BCrypt.gensalt()),
                administrador,
                autenticado
        );
        usuario.setId(id);
        usuario.setEmail(email);
        return usuario;
    }

    private OperacaoUsuarioDAOException erroBanco() {
        return new OperacaoUsuarioDAOException(FALHA_BANCO, new SQLException("Falha simulada"));
    }

    private void assertFalha(ResultadoOperacao<?> resultado, String mensagemEsperada) {
        assertFalse(resultado.eValido());
        assertEquals(Collections.singletonList(mensagemEsperada), resultado.getErros());
    }
}
