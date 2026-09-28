package services;

import com.pss.senha.validacao.ValidadorSenha;
import dao.NotificacaoDAO;
import dao.UsuariosDAO;
import eventosTela.EventosTela;
import excecoes.BancoDeDadosException;
import excecoes.enums.MensagensErroBanco;
import excecoes.enums.MensagensUsuario;
import models.Notificacao;
import models.ResultadoOperacao;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import utilidades.FormatarErros;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {

    private final UsuariosDAO usuarioDAO;
    private final ValidadorSenha validadorSenha;
    private final NotificacaoDAO notificacaoDAO;

    public UsuarioService(UsuariosDAO usuariosDAO, NotificacaoDAO notificacaoDAO) {
        this.usuarioDAO = usuariosDAO;
        this.notificacaoDAO = notificacaoDAO;
        this.validadorSenha = new ValidadorSenha();

    }

    public ResultadoOperacao<Usuario> autenticarLogin(String email, String senha){

        ResultadoOperacao<Usuario> resultado = new ResultadoOperacao<>();

        if (!validarCamposObrigatorios(resultado, email, senha)) {
            return resultado;
        }

        try {
            Usuario usuario = usuarioDAO.buscarPorEmail(email);

            if (usuario == null || !BCrypt.checkpw(senha, usuario.getSenha())) {
                resultado.adicionarErro(MensagensUsuario.CREDENCIAIS_INVALIDAS.getMensagem());
                return resultado;
            }

            if (!usuario.getIsAutenticado()) {
                resultado.adicionarErro(MensagensUsuario.USUARIO_NAO_AUTORIZADO.getMensagem());
                return resultado;
            }


            int id = usuario.getId();
            usuario.setQtdNotificacoesNLidas(notificacaoDAO.buscarQtdNotificacoesNLidas(id));
            usuario.setNotificacoesEnviadas(notificacaoDAO.buscarQtdNotificacoesEnviadas(id));

            resultado.adicionarResultado(usuario);

        } catch (BancoDeDadosException e) {
            resultado.adicionarErro(e.getMessage());
        }
        return resultado;

    }
    public ResultadoOperacao<Void> autenticarUsuario(Usuario usuario){

        ResultadoOperacao<Void> resultado = new ResultadoOperacao<>();
        if (usuario == null) {
            resultado.adicionarErro(MensagensUsuario.USUARIO_NULO.getMensagem());
            return resultado;
        }

        if (usuario.getIsAutenticado()) {
            resultado.adicionarErro(MensagensUsuario.USUARIO_JA_AUTORIZADO.getMensagem());
            return resultado;
        }

        try {
            usuario.setAutenticacao(true);
            usuarioDAO.atualizar(usuario);
        } catch (BancoDeDadosException e) {
            usuario.setAutenticacao(false);
            resultado.adicionarErro(e.getMessage());
        }
        return resultado;
    }
    public ResultadoOperacao<List<Usuario>> buscar(String nome) {
        ResultadoOperacao<List<Usuario>> resultado = new ResultadoOperacao<>();
        try {
            List<Usuario> usuarios = null;

            if (nome == null) {
                resultado.adicionarErro(MensagensUsuario.CAMPOS_OBRIGATORIOS.getMensagem());
                return resultado;
            }

            if (nome.isEmpty()){
                usuarios = usuarioDAO.listarTodos();
            }else{
                usuarios = usuarioDAO.buscarPorNome(nome);
            }

            for (Usuario u : usuarios){
                int id = u.getId();
                u.setNotificacoesEnviadas(notificacaoDAO.buscarQtdNotificacoesEnviadas(id));
                u.setQtdNotificacoesNLidas(notificacaoDAO.buscarQtdNotificacoesNLidas(id));
                u.setQtdNotificacoesLidas(notificacaoDAO.buscarQtdNotificacoesLidas(id));
            }

            resultado.adicionarResultado(usuarios);


        } catch (BancoDeDadosException e) {
            resultado.adicionarErro(e.getMessage());
        }

        return resultado;
    }

    public ResultadoOperacao<Usuario> buscarPorId(int id) {
        ResultadoOperacao<Usuario> resultado = new ResultadoOperacao<>();
        try {
            Usuario usuario = null;

            if (id < 0) {
                return resultado;
            }
            usuario = usuarioDAO.buscarPorId(id);

            usuario.setNotificacoesEnviadas(notificacaoDAO.buscarQtdNotificacoesEnviadas(id));
            usuario.setQtdNotificacoesNLidas(notificacaoDAO.buscarQtdNotificacoesNLidas(id));
            usuario.setQtdNotificacoesLidas(notificacaoDAO.buscarQtdNotificacoesLidas(id));

            resultado.adicionarResultado(usuario);
        } catch (BancoDeDadosException e) {
            resultado.adicionarErro(e.getMessage());
        }

        return resultado;
    }


    private boolean validarCamposObrigatorios(ResultadoOperacao<?> resultado, String... campos){
        for (String campo : campos) {
            if(campo == null || campo.trim().isEmpty()){
                resultado.adicionarErro(MensagensUsuario.CAMPOS_OBRIGATORIOS.getMensagem());
                return false;
            }
        }

        return true;

    }

    private boolean validarEmail(ResultadoOperacao<?> resultado, String email){
        if (usuarioDAO.buscarPorEmail(email) != null) {
            resultado.adicionarErro(MensagensUsuario.EMAIL_JA_CADASTRADO.getMensagem());
            return false;
        }
        return true;
    }

    private boolean validarEmailNaEdicao(ResultadoOperacao<?> resultado, String email, Usuario usuario){
        Usuario usuarioComMesmoEmail = usuarioDAO.buscarPorEmail(email);
        if (usuarioComMesmoEmail != null && usuarioComMesmoEmail.getId() != usuario.getId()) {
            resultado.adicionarErro(MensagensUsuario.EMAIL_JA_CADASTRADO.getMensagem());
            return false;
        }
        return true;
    }

    private boolean validarSenha(ResultadoOperacao<?> resultado, String senha){
        List<String> erros = validadorSenha.validar(senha);
        if (!erros.isEmpty()) {
            resultado.adicionarErro(FormatarErros.unificarErros(erros));
            return false;
        }
        return true;
    }

    private boolean validarSenhasIguais(ResultadoOperacao<?> resultado, String senha, String senhaNovamente){
        if (!senha.equals(senhaNovamente)) {
            resultado.adicionarErro(MensagensUsuario.SENHAS_NAO_COINCIDEM.getMensagem());
            return false;
        }


        return true;
    }

    public ResultadoOperacao<Usuario> cadastrarUsuario(
            String nome,
            String email,
            String senha,
            String senhaNovamente,
            boolean eAdmin
    ) {
        ResultadoOperacao<Usuario> resultado = new ResultadoOperacao<>();
        try {
            if (!validarCamposObrigatorios(resultado, nome, email, senha, senhaNovamente) ||
                    !validarEmail(resultado, email) ||
                    !validarSenha(resultado, senha) ||
                    !validarSenhasIguais(resultado, senha, senhaNovamente)) {
                return resultado;
            }

            boolean primeiroUsuario = usuarioDAO.listarTodos().isEmpty();
            Usuario usuario = new Usuario(
                    nome,
                    BCrypt.hashpw(senha, BCrypt.gensalt()),
                    primeiroUsuario || eAdmin,
                    primeiroUsuario
            );
            usuario.setEmail(email);
            usuarioDAO.inserir(usuario);
            resultado.adicionarResultado(usuario);




        } catch (BancoDeDadosException e) {
            resultado.adicionarErro(e.getMessage());
        }

        return resultado;
    }


    public ResultadoOperacao<Void> excluirUsuario(int id){
        ResultadoOperacao<Void> resultado = new ResultadoOperacao<>();

        try {
            usuarioDAO.excluir(id);
            resultado.adicionarResultado(null);
        } catch (BancoDeDadosException e) {
            resultado.adicionarErro(e.getMessage());
        }

        return resultado;

    }

    public ResultadoOperacao<List<Usuario>> listar() {
        ResultadoOperacao<List<Usuario>> resultado = new ResultadoOperacao<>();

        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            if (usuarios != null) {
                resultado.adicionarResultado(usuarios);
            }
        } catch (BancoDeDadosException e) {
            resultado.adicionarErro(e.getMessage());
        }
        return resultado;

    }

    public ResultadoOperacao<Void> editarUsuario(
            Usuario usuario,
            String nome,
            String email,
            String senha,
            String senhaNovamente
    ) {
        ResultadoOperacao<Void> resultado = new ResultadoOperacao<>();
        if (usuario == null) {
            resultado.adicionarErro(MensagensUsuario.USUARIO_NULO.getMensagem());
            return resultado;
        }

        String nomeAnterior = usuario.getNome();
        String emailAnterior = usuario.getEmail();
        String senhaAnterior = usuario.getSenha();

        try {
            if (!validarCamposObrigatorios(resultado, nome, email, senha, senhaNovamente) ||
                    !validarEmailNaEdicao(resultado, email, usuario) ||
                    !validarSenha(resultado, senha) ||
                    !validarSenhasIguais(resultado, senha, senhaNovamente)) {
                return resultado;
            }

            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));

            usuarioDAO.atualizar(usuario);
        } catch (BancoDeDadosException e) {
            usuario.setNome(nomeAnterior);
            usuario.setEmail(emailAnterior);
            usuario.setSenha(senhaAnterior);
            resultado.adicionarErro(e.getMessage());
        }

        return resultado;
    }
}
