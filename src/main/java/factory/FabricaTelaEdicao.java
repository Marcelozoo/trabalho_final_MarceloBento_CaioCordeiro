package factory;

import models.Usuario;
import presenters.TelaEditacaoPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaEdicao implements FabricaDeTela {
    private Usuario usuario;
    public FabricaTelaEdicao(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public TelaPresenter criar(ProvedorService provedorService, GerenciadorTelasService gerenciadorTelas) {
        return new TelaEditacaoPresenter(provedorService.obterUsuarioService(), gerenciadorTelas,  this.usuario);
    }
}
