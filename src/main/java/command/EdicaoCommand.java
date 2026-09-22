package command;

import models.ResultadoOperacao;
import services.UsuarioService;

public class EdicaoCommand implements Command{


    private final String nome;
    private final String senha;
    private final String senhaNovamente;
    private final UsuarioService usuarioService;
    private ResultadoOperacao<Void> resultado;

    public EdicaoCommand(String nome, String senha, String senhaNovamente, UsuarioService usuarioService) {
        this.nome = nome;
        this.senha = senha;
        this.senhaNovamente = senhaNovamente;
        this.usuarioService = usuarioService;

    }

    @Override
    public ResultadoOperacao<Void> executar() {
        resultado = new ResultadoOperacao<>();
        return resultado;
    }

    @Override
    public ResultadoOperacao<Void> getResultado() {
        return resultado;
    }
}
