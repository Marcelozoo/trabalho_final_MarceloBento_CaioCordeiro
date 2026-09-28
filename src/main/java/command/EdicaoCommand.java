package command;

import models.ResultadoOperacao;
import models.Usuario;
import services.UsuarioService;

public class EdicaoCommand implements Command<Void> {

    private final Usuario usuario;
    private final String nome;
    private final String email;
    private final String senha;
    private final String senhaNovamente;
    private final UsuarioService usuarioService;
    private ResultadoOperacao<Void> resultado;

    public EdicaoCommand(
            Usuario usuario,
            String nome,
            String email,
            String senha,
            String senhaNovamente,
            UsuarioService usuarioService
    ) {
        this.usuario = usuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.senhaNovamente = senhaNovamente;
        this.usuarioService = usuarioService;
    }

    @Override
    public void executar() {
        resultado = usuarioService.editarUsuario(usuario, nome, email, senha, senhaNovamente);
    }

    @Override
    public ResultadoOperacao<Void> getResultado() {
        return resultado;
    }
}
