package factory;

import presenters.TelaCadastroPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaCadastro implements FabricaDeTela {
    private boolean eAdmin;

    public FabricaTelaCadastro(boolean eAdmin){
        this.eAdmin = eAdmin;
    }

    @Override
    public TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new TelaCadastroPresenter(eAdmin, provedor.obterUsuarioService(), gerenciadorTelas);
    }
}