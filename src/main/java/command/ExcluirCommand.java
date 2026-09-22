package command;

import models.ResultadoOperacao;
import services.UsuarioService;

public class ExcluirCommand implements Command<Void>{

    private final String nome;
    private final UsuarioService usuarioService;
    private ResultadoOperacao<Void> resultado;

    public ExcluirCommand(String nome, UsuarioService usuarioService) {
        this.nome = nome;
        this.usuarioService = usuarioService;
    }

    @Override
    public ResultadoOperacao<Void> executar() {
        resultado = new ResultadoOperacao<>();
        usuarioService.excluirUsuario(nome, resultado);
        return resultado;
    }

    @Override
    public ResultadoOperacao<Void> getResultado() {
        return resultado;
    }
}
