package factory;

import models.Usuario;
import presenters.TelaEnviarNotificacoesPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaEnviarNotificacoes implements FabricaDeTela {

    private final Usuario usuario;

    public FabricaTelaEnviarNotificacoes(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new TelaEnviarNotificacoesPresenter(
                usuario,
                provedor.obterUsuarioService(),
                provedor.obterEnviarNotificacaoService(),
                gerenciadorTelas);
    }
}