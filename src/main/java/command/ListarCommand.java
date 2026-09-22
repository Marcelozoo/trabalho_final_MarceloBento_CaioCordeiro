package command;

import models.ResultadoOperacao;
import models.Usuario;
import services.UsuarioService;

import java.util.List;

public class ListarCommand implements Command<List<Usuario>> {

    private ResultadoOperacao<List<Usuario>> resultado;
    private final UsuarioService usuarioService;

    public ListarCommand(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public ResultadoOperacao<List<Usuario>> executar() {
        resultado = new ResultadoOperacao<>();
        usuarioService.listar(resultado);
        return resultado;
    }

    public ResultadoOperacao<List<Usuario>> getResultado() {
        return resultado;
    }

}
