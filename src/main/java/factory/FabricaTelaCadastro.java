package factory;

import presenters.TelaCadastroPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaCadastro implements FabricaDeTela {

    @Override
    public TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new TelaCadastroPresenter(provedor.obterUsuarioService(), gerenciadorTelas);
    }
}