package command;

import models.ResultadoOperacao;
import models.Usuario;
import services.UsuarioService;

public class CadastrarCommand implements Command<Usuario> {

    private final String nome;
    private final String email;
    private final String senha;
    private final String senhaNovamente;
    private final boolean eAdmin;
    private final UsuarioService usuarioService;
    private ResultadoOperacao<Usuario> resultado;

    public CadastrarCommand(
            String nome,
            String email,
            String senha,
            String senhaNovamente,
            boolean eAdmin,
            UsuarioService usuarioService
    ) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.senhaNovamente = senhaNovamente;
        this.eAdmin = eAdmin;
        this.usuarioService = usuarioService;

    }

    @Override
    public void executar() {
        resultado = usuarioService.cadastrarUsuario(nome, email, senha, senhaNovamente, eAdmin);

    }

    @Override
    public ResultadoOperacao<Usuario> getResultado() {
        return resultado;
    }
}
