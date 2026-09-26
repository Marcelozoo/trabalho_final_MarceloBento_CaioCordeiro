package command;

import models.ResultadoOperacao;
import services.UsuarioService;

public class ExcluirCommand implements Command<Void>{

    private final int id;
    private final UsuarioService usuarioService;
    private ResultadoOperacao<Void> resultado;

    public ExcluirCommand(int id, UsuarioService usuarioService) {
        this.id = id;
        this.usuarioService = usuarioService;
    }

    @Override
    public void executar() {
        this.resultado = usuarioService.excluirUsuario(id);
    }

    @Override
    public ResultadoOperacao<Void> getResultado() {
        return resultado;
    }
}
