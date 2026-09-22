package factory;

import presenters.TelaLoginPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;
import services.UsuarioService;

public class FabricaTelaLogin implements FabricaDeTela {

    @Override
    public TelaPresenter criar(ProvedorService provedorService, GerenciadorTelasService gerenciadorTelas) {
        return new TelaLoginPresenter(provedorService.obterUsuarioService(), gerenciadorTelas);
    }
}