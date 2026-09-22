package command;

import models.ResultadoOperacao;
import models.Usuario;
import services.UsuarioService;

public class AutenticarLoginCommand implements Command<Usuario> {

    private final String nome;
    private final String senha;
    private final UsuarioService usuarioService;
    private ResultadoOperacao<Usuario> resultado;

    public AutenticarLoginCommand(String nome, String senha, UsuarioService usuarioService) {
        this.nome = nome;
        this.senha = senha;
        this.usuarioService = usuarioService;
    }

    @Override
    public ResultadoOperacao<Usuario> executar() {
        resultado = usuarioService.autenticarLogin(nome, senha);
        return resultado;
    }

    @Override
    public ResultadoOperacao<Usuario> getResultado() {
        return resultado;
    }
}