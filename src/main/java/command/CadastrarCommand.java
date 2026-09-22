package command;

import models.ResultadoOperacao;
import models.Usuario;
import services.UsuarioService;

public class CadastrarCommand implements Command<Usuario> {

    private final UsuarioService usuarioService;
    private String nome;
    private String senha;
    private String senhaNovamente;
    private ResultadoOperacao<Usuario> resultado;

    public CadastrarCommand(String nome, String senha, String senhaNovamente, UsuarioService usuarioService) {
        this.nome = nome;
        this.senha = senha;
        this.senhaNovamente = senhaNovamente;
        this.usuarioService = usuarioService;
    }

    @Override
    public ResultadoOperacao<Usuario> executar() {
        resultado = usuarioService.cadastrarUsuario(nome, senha, senhaNovamente);
        return resultado;
    }

    public ResultadoOperacao<Usuario> getResultado() {
        return resultado;
    }
}