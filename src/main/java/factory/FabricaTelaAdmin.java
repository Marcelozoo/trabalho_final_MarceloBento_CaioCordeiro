package factory;

import models.Usuario;
import presenters.TelaAdminPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaAdmin implements FabricaDeTela {

    private final Usuario usuario;

    public FabricaTelaAdmin(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new TelaAdminPresenter(usuario,
                gerenciadorTelas,
                provedor.obterUsuarioService(),
                provedor.obterNotificacaoService()
        );
    }
}