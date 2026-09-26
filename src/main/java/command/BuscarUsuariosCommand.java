package command;

import models.ResultadoOperacao;
import models.Usuario;
import services.UsuarioService;

import java.util.List;

public class BuscarUsuariosCommand implements Command<List<Usuario>> {

    private final String nome;
    private final UsuarioService usuarioService;
    private ResultadoOperacao<List<Usuario>> resultado;

    public BuscarUsuariosCommand(String nome, UsuarioService usuarioService) {
        this.nome = nome;
        this.usuarioService = usuarioService;
    }

    @Override
    public void executar() {
        resultado = usuarioService.buscar(nome);

    }

    @Override
    public ResultadoOperacao<List<Usuario>> getResultado() {
        return resultado;
    }
}
