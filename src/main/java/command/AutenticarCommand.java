package command;

import models.ResultadoOperacao;
import models.Usuario;
import services.UsuarioService;

public class AutenticarCommand implements Command<Void>{

    private final Usuario usuario;
    private final UsuarioService usuarioService;
    private ResultadoOperacao<Void> resultado;

    public AutenticarCommand(Usuario usuario, UsuarioService usuarioService) {
        this.usuario = usuario;
        this.usuarioService = usuarioService;
    }

    @Override
    public void executar() {
        this.resultado = usuarioService.autenticarUsuario(usuario);

    }

    @Override
    public ResultadoOperacao<Void> getResultado() {
        return resultado;
    }
}
