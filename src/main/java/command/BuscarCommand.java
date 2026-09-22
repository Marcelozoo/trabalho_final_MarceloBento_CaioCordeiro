package command;

import models.ResultadoOperacao;
import models.Usuario;
import services.UsuarioService;

public class BuscarCommand implements Command<Usuario> {

    private final String nome;
    private final UsuarioService usuarioService;
    private ResultadoOperacao<Usuario> resultado;

    public BuscarCommand(String nome, UsuarioService usuarioService) {
        this.nome = nome;
        this.usuarioService = usuarioService;
    }

    @Override
    public ResultadoOperacao<Usuario> executar() {
        resultado = new ResultadoOperacao<>();
        usuarioService.buscar(nome, resultado);
        return resultado;
    }

    public ResultadoOperacao<Usuario> getResultado() {
        return resultado;
    }
}