package services;

public class ProvedorService {


    EnviarNotificacaoService enviarNotificacaoService;
    UsuarioService usuarioService;

    public ProvedorService(UsuarioService usuarioService,
                           EnviarNotificacaoService enviarNotificacaoService ) {

        this.enviarNotificacaoService = enviarNotificacaoService;
        this.usuarioService = usuarioService;
    }


    public EnviarNotificacaoService obterEnviarNotificacaoService(){
        return enviarNotificacaoService;
    }

    public UsuarioService obterUsuarioService() {
        return usuarioService;
    }
}
