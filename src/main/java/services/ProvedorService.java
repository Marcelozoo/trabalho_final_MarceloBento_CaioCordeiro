package services;

public class ProvedorService {

    private final EnviarNotificacaoService enviarNotificacaoService;
    private final UsuarioService usuarioService;
    private final NotificacaoService notificacaoService;

    public ProvedorService(
            UsuarioService usuarioService,
            EnviarNotificacaoService enviarNotificacaoService,
            NotificacaoService notificacaoService
    ) {
        this.usuarioService = usuarioService;
        this.enviarNotificacaoService = enviarNotificacaoService;
        this.notificacaoService = notificacaoService;
    }

    public EnviarNotificacaoService obterEnviarNotificacaoService() {
        return enviarNotificacaoService;
    }

    public UsuarioService obterUsuarioService() {
        return usuarioService;
    }

    public NotificacaoService obterNotificacaoService() {
        return notificacaoService;
    }
}
